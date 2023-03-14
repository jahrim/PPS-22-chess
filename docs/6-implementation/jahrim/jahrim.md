Per quanto riguarda l'implementazione del sistema, io mi sono occupato principalmente dell'infrastruttura
su cui si basa l'applicazione e della gestione dello stato di una partita all'interno dell'engine, di cui
in particolare anche la gestione della scacchiera.

Le classi a cui ho contribuito, in totalità o anche solo parzialmente, sono le seguenti, raggruppate per
categoria:
- applicazione:
  - avvio: `Main`
  - infrastruttura: 
    - componenti: `ChessApplication`, `ChessApplicationContext`, `ChessApplicationComponent`, `ChessLocalProxy`,
      `FXComponent`, `StageComponent`
    - schermate: `Page`, `PageWithController`, `ApplicablePage`, `FXMLPage`
    - controller: `Controller`, `FXMLController`
  - schermate: `MainMenuPage`
  - controller: `MainMenuController`, `GameController`
  - utility: `FXUtils`
- engine:
  - stato: `ChessPort`, `ChessGame`, `ChessGameState`,  `ChessGameStatus`, `ChessGameHistory`, `ChessBoard`, 
    `ChessBoardBuilder`, `PiecePlaceholder`
  - eventi: `Event`, `TurnChangedEvent`, `GameOverEvent`, `GameOverCause`
  - regole dei pezzi: `AvoidSelfCheckRule`, `RuleShorthands`
- utility:
  - scala: `Exceptions`, `Logger`, `Require`, `Id`, `OptionExtension`
  - vertx: `VerticleExecutor`

Inoltre, ho contribuito alla creazione o al reperimento delle seguenti risorse: _chess-pieces/\*.png_, _effects/\*.png_, 
_icons/\*.png_, _utility/\*.png_, _main-menu.fxml_.

Di seguito, descriverò nel dettaglio le parti più interessanti del mio contributo a questo progetto, esclusa
l'infrastruttura dell'applicazione, già descritta nei capitoli precedenti.

## Gestione dell'esecuzione di una partita
Il servizio relativo all'engine, implementato dalla classe `ChessGameService`, è un servizio _stateful_,
perciò mantiene uno stato, in base al quale abilita o disabilita le funzionalità esposte dal suo contratto.
Pertanto, la business logic del servizio, contenuta nella classe `ChessGame`, deve tenere traccia di uno stato,
modellato dalla classe `ChessGameState`, e deve garantire che:
- Non vi siano modifiche concorrenti sullo stato del servizio;
- Nessuna funzionalità del servizio sia eseguita quando lo stato del servizio non lo permette (es.: non è possibile 
  richiedere le mosse di un pezzo sulla scacchiera se non è stata avviata una partita...).

Per evitare modifiche concorrenti sullo stato del servizio, si è deciso di fare evolvere lo stato all'interno di un 
_event-loop_, che è stato realizzato sfruttando la libreria _Vert.x_. Per realizzare efficacemente l'event-loop, è 
necessario garantire che non vi sia concorrenza tra le attività che sono richieste al servizio. 

Per spiegare come è stato garantito ciò, è necessario introdurre brevemente il funzionamento di Vert.x.
\
In Vert.x, un `Vertx` è un sistema che gestisce un insieme di event-loop. All'interno di un `Vertx`, è possibile 
istanziare un `Verticle`, che è un attore al quale viene associato un event-loop, sul quale può registrare degli
event-handler per definire il proprio comportamento in reazione agli eventi ricevuti. Tutti gli event-handler
registrati dallo stesso `Verticle` sono sempre eseguiti attraverso un unico flusso di controllo.
\
All'interno dell'engine, il `ChessGameService` è un `Verticle` che crea un `ChessGame`, il quale configura il `Verticle`
aggiungendo alcuni event-handler all'event-loop (es.: registrando le proprie sottoscrizioni e quelle degli utenti del 
servizio...). 
Tuttavia, senza prendere degli accorgimenti, le funzionalità del `ChessGame` sarebbero eseguite dal chiamante, 
rompendo di fatto l'event-loop. Infatti, le funzioni del contratto del servizio e gli event-handler eseguirebbero
concorrentemente su flussi di controllo separati.
\
Per questo, è stato implementato un `VerticleExecutor`, che permette di specificare dei task da eseguire in futuro,
garantendo che la loro esecuzione avvenga sullo stesso event-loop del `Verticle` in cui è stato creato.
\
Come mostrato di seguito, il `VerticleExecutor` mantiene una mappa di task ancora da eseguire, associati a un certo
identificatore. Alla creazione registra un event-handler, che reagisce a eventi contenenti l'identificatore di un task,
prendendo tale task dalla mappa ed eseguendolo.
Quando si delega un task al `VerticleExecutor` tramite il metodo `runLater`, la mappa viene aggiornata aggiungendo tale
task e un nuovo evento contenente il suo identificatore viene pubblicato sull'event-loop, attivando l'event-handler 
appena possibile.

```scala
class VerticleExecutor(private val vertx: Vertx):
  private type TaskId = String
  private val address: String = Id()
  private val pendingTasks: Map[TaskId, PendingTask[_]] = TrieMap.empty
  
  this.vertx.eventBus().localConsumer(this.address, this.runPendingTask)

  def runLater[T](task: => T): Future[T] =
    val (taskId, pendingTask) = Id() -> PendingTask(task)
    this.pendingTasks.update(taskId, pendingTask)
    this.vertx.eventBus().publish(this.address, taskId)
    pendingTask.future

  private def runPendingTask(message: Message[TaskId]): Unit =
    this.pendingTasks.get(message.body()).foreach(_.execute())
    this.pendingTasks.remove(message.body())
```

Delegando tutte le funzionalità del contratto al proprio `VerticleExecutor`, il `ChessGame` garantisce che la loro
esecuzione avvenga allo stesso modo degli event-handler, ovvero che le funzionalità del servizio e gli event-handler
eseguano sullo stesso flusso di controllo.

Una volta risolto il problema della concorrenza nelle funzionalità che modificano lo stato servizio, si è anche
dovuto garantire che le funzionalità non potessero essere eseguite quando lo stato del servizio non lo permette.
\
Questo problema è stato gestito inserendo dei metodi privati all'interno del `ChessGame`, applicabili a blocchi di
codice che devono essere eseguiti sotto alcune condizioni particolari. In altre parole, questi metodi agiscono come
dei _modificatori_ per l'esecuzione di un certo blocco di codice.

Di seguito, si illustra un esempio di una combinazione delle soluzioni ai problemi descritti in questa sezione, 
applicata all'implementazione di una delle funzionalità del servizio.

```scala
class ChessGame(private val vertx: Vertx) extends ChessPort:
  private var state: ChessGameState = ChessGameState.NotConfigured()
  private val verticleExecutor: VerticleExecutor = VerticleExecutor(this.vertx)
  /* ... */

  override def findMoves(position: Position): Future[Set[Move]] =
    runOnVerticle("Find move") {
      onlyIfRunning { status =>
        status.chessBoard.pieces(status.currentTurn).get(position) match
          case Some(piece) => piece.rule.findMoves(position, status)
          case None        => Set.empty
      }
    }

  private def runOnVerticle[T](activityName: String)(activity: => T): Future[T] =
    this.verticleExecutor.runLater { logActivity(activityName) { activity } }
  
  private def onlyIfRunning[T](activity: ChessGameStatus => T): T = this.state match
    case Running(status) => activity(status)
    case _               => throw GameNotRunningException()
```

Nell'esempio, si applicano due modificatori al blocco di codice relativo all'implementazione della `findMoves`:
- `runOnVerticle`: indica che il blocco di codice dev'essere eseguito sull'event-loop. 
  Il nome indicato per il blocco di codice viene utilizzato per eseguire delle stampe e monitorare l'inizio e la fine
  della sua esecuzione (notare come il modificatore `runOnVerticle` dipenda da un ulteriore modificatore `logActivity`).
- `onlyIfRunning`: indica che il blocco di codice può essere eseguito solo quando il sistema è nello stato `Running`.

## Creazione della scacchiera
La scacchiera è uno degli elementi più importanti relativi allo stato di una partita degli scacchi.
La sua implementazione, contenuta nella classe `ChessBoard`, è molto semplice e consiste in un wrapper di una mappa che
associa ogni posizione sulla scacchiera, modellata dalla classe `Position`, a un pezzo degli scacchi, modellato dalla
classe `Piece`.
\
La `ChessBoard`, come gli altri elementi del `ChessGameStatus` di un `ChessGame`, è stata definita come classe 
immutabile. Infatti, ogni metodo di modifica istanzia un nuovo oggetto contenente i campi aggiornati dalla modifica. In 
questo modo, l'engine può inviare i propri dati all'applicazione senza rischiare che l'applicazione possa modificarli.

Uno dei problemi principali di una scacchiera è che crearla con una precisa configurazione iniziale dei pezzi può essere
molto verboso (es.: si consideri di dover descrivere la configurazione standard dei pezzi sulla scacchiera
inizializzando la mappa citata precedentemente...).
\
Pertanto, se si vuole lasciare la libertà all'utilizzatore di una `ChessBoard` di crearla con una configurazione 
personalizzata, è necessario rendere il processo di configurazione più semplice e dichiarativo.

A tale scopo, si è deciso di realizzare il `ChessBoardBuilder`, che può essere configurato per creare delle `ChessBoard`
con delle disposizioni particolari dei pezzi.
\
Durante la configurazione, il `ChessBoardBuilder` scorre le celle della scacchiera da sinistra verso destra e dall'alto
verso il basso (come una macchina da scrivere), permettendo all'utente di configurare il contenuto della prossima cella 
tramite il metodo `setNextCell`. Inoltre, siccome spesso alcuni rank della scacchiera non contengono pezzi, è permesso
all'utente di riempire la riga corrente con delle celle vuote (come andare a capo) attraverso il metodo `nextRow`, che
può essere ripetuto più volte.
\
Dopodiché, sono stati aggiunti degli operatori più sintetici per le due operazioni precedenti: l'operatore `+`, alias di
`setNextCell`, e l'operatore `-`, alias di `nextRow`. In questo modo, si è ottenuto il livello di dichiaratività
mostrato di seguito.

```scala
import io.github.chess.util.scala.option.OptionExtension.anyToOptionOfAny
import io.github.chess.engine.model.game.Team.{BLACK as B, WHITE as W}

val standardChessBoardBuilder: ChessBoardBuilder =
  ChessBoardBuilder()
    + Rook(B) + Knight(B) + Bishop(B) + Queen(B) + King(B) + Bishop(B) + Knight(B) + Rook(B)
    + Pawn(B) + Pawn(B)   + Pawn(B)   + Pawn(B)  + Pawn(B) + Pawn(B)   + Pawn(B)   + Pawn(B)
    + None    + None      + None      + None     + None    + None      + None      + None
    - 2   // skip two rows
    + None    + None      + None      + None     + None    + None      + None      + None
    + Pawn(W) + Pawn(W)   + Pawn(W)   + Pawn(W)  + Pawn(W) + Pawn(W)   + Pawn(W)   + Pawn(W)
    + Rook(W) + Knight(W) + Bishop(W) + Queen(W) + King(W) + Bishop(W) + Knight(W) + Rook(W)
val standardChessBoard: ChessBoard = standardChessBoardBuilder.build()
```

Nonostante i miglioramenti, definire una scacchiera in questo modo risulta comunque piuttosto verboso. Per questo, si è
deciso di costruire un _DSL_ apposito per la configurazione di un `ChessBoardBuilder`, contenuto nell'oggetto 
`ChessBoardBuilder.DSL`.
\
Il DSL è accessibile all'interno del _factory method_ `configure` del `ChessBoardBuilder`.
Quando chiamato, il metodo `configure` crea un nuovo `ChessBoardBuilder`, rendendolo disponibile come _given instance_
all'interno della funzione di configurazione che l'utente deve specificare.
All'interno di tale funzione, il DSL fornisce un insieme di operatori che prendono in input un `ChessBoardBuilder` come 
given instance, ne modificano la configurazione e lo restituiscono in output. Questi operatori sono:
- `X`, `R`, `N`, `B`, `Q`, `K`, `P`: impostano il contenuto della prossima cella del `ChessBoardBuilder` a un pezzo 
  della squadra bianca. Corrispondono rispettivamente ai pezzi: `PiecePlaceholder`, `Rook`, `Knight`, `Bishop`, `Queen`,
  `King` e `Pawn`.
- `x`, `r`, `n`, `b`, `q`, `k`, `p`: impostano il contenuto della prossima cella del `ChessBoardBuilder` a un pezzo 
  della squadra nera. Corrispondono rispettivamente ai pezzi: `PiecePlaceholder`, `Rook`, `Knight`, `Bishop`, `Queen`, 
  `King` e `Pawn`.
- `*`: imposta il contenuto della prossima cella del `ChessBoardBuilder` a una cella vuota.
- `**`: equivalente a `nextRow`.

In questo modo, dovrebbe essere possibile configurare la scacchiera nel modo seguente.

```scala
val standardChessBoardBuilder: ChessBoardBuilder = ChessBoardBuilder.configure {
  r n b q k b n r   // unfortunately, it does not compile
  p p p p p p p p
  * * * * * * * *
  ** { 2 }          // skip two rows
  * * * * * * * *
  P P P P P P P P
  R N B Q K B N R
}
val standardChessBoard: ChessBoard = standardChessBoardBuilder.build()
```

Purtroppo, questa sintassi non compila. Infatti, nel codice precedente, il primo operatore `r` viene interpretato come 
chiamata a metodo sul `ChessBoardBuilder.DSL`, tuttavia esso restituisce un `ChessBoardBuilder`, perciò l'operatore 
successivo `n` viene interpretato come chiamata a metodo su un `ChessBoardBuilder` e non sul `ChessBoardBuilder.DSL`, 
quindi la compilazione fallisce.

Per risolvere quest'ultimo problema, è stato aggiunto l'operatore di separazione `|`, come metodo estensione della 
classe `ChessBoardBuilder`. L'operatore `|` ha una funzionalità puramente sintattica, infatti prende in input un 
`ChessBoardBuilder` e lo restituisce inalterato. In aggiunta, l'utilizzo del separatore dilata maggiormente la larghezza
della scacchiera rendendola più leggibile.

Infine, per semplicità di utilizzo, il DSL è stato reso disponibile direttamente all'interno del metodo `apply` della 
`ChessBoard`, che dipende dalla `configure` del `ChessBoardBuilder`.

Di seguito, si riporta il risultato finale del DSL.

```scala
val standardChessBoard: ChessBoard = ChessBoard {
  r | n | b | q | k | b | n | r   // now, it compiles correctly
  p | p | p | p | p | p | p | p
  * | * | * | * | * | * | * | *
  ** { 2 }
  * | * | * | * | * | * | * | *
  P | P | P | P | P | P | P | P
  R | N | B | Q | K | B | N | R
}
```

## Prevenzione delle mosse di auto-scacco
In collaborazione con Madina Kentpayeva, ho contribuito alle funzionalità di analisi delle situazioni di scacco, scacco
matto e stallo.
Infatti, esse si basano sul fatto che un giocatore non può spostare un pezzo se la mossa lo metterebbe in scacco. Per
questo, ho implementato l'`AvoidSelfCheckRule`: un mixin che può essere applicato a qualsiasi `ChessRule`, filtrando le
mosse che metterebbero il giocatore corrente in scacco.

```scala
trait AvoidSelfCheckRule extends ChessRule with RuleShorthands:
  abstract override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    within(status) {
      super.findMoves(position, status).filter { move =>
        !isPieceOfCurrentTurn(position)
        ||
        !ChessGameAnalyzer.check(
          status.updateChessBoard {
              status.chessBoard.movePiece(move.from, move.to)
            }
        )
  }
}
```

Per evitare chiamate ricorsive cicliche, per cui la `findMoves` richiama la `check` per eliminare le mosse che
metterebbero il giocatore corrente in scacco, mentre la `check` richiama la `findMoves` per individuare le mosse
dell'avversario che potrebbero catturare il re del giocatore corrente, è stato necessario aggiungere un controllo,
per cui solo i pezzi del giocatore di turno sono soggetti a questa regola del gioco.

[Back to index](../../index.md) |
[Back to implementation](../../6-implementation/index.md)