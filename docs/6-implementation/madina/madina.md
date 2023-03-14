Per quanto riguarda l'implementazione del sistema, io mi sono occupata principalmente della 
definizione delle regole e dei pezzi della torre e della regina, dell'analisi della scacchiera
per le situazioni di scacco, scacco matto e stallo, della schermata di gioco dell'applicazione
e del controller relativo alla scacchiera.

In particolare, le classi a cui ho contribuito, in totalità o anche solo parzialmente,
sono le seguenti, raggruppate per categoria:
- regole dei pezzi: `AllDirectionsRule`, `QueenRule`, `RookRule`, `StraightRule`, `ChessRule`,
  `DirectionalRule`, `RuleShorthands`, `NPrologRule`, `SPrologRule`, `EPrologRule`, `WPrologRule`
- mosse dei pezzi: `CaptureMove`
- pezzi della scacchiera: `Queen`, `Rook`
- servizio relativo all'engine: `ChessGame`, `ChessGameAnalyzer`
- applicazione grafica: `FXUtils`, `InterfaceConfiguration`, `ApplicablePage`, `CellView`,
  `PieceView`, `ChessBoardController`, `GameController`
- utility: `StatefulSystem`, `GivenExtension`, `NumberExtension`

Inoltre, ho contribuito alla creazione delle seguenti risorse: _game-page.fxml_, _game-page.css_,
_n_move.pl_, _e_move.pl_, _s_move.pl_, _o_move.pl_.

Di seguito, descriverò nel dettaglio una selezione delle parti più interessanti del sistema a cui
ho contribuito.

## Regole dei pezzi
Per implementare le regole della regina e della torre, modellate rispettivamente dalle classi
`QueenRule` e `RookRule`, è stato necessario definire i vari concetti relativi alle direzioni
cardinali. Quindi, si è deciso di definire delle teorie Prolog che attraverso delle _regole_
logiche permettono, data una posizione di partenza sulla scacchiera, di ottenere le posizioni 
che si trovano ad una certa direzione cardinale rispetto alla posizione di partenza.

In particolare, sono state realizzate le seguenti `PrologRule`: `NPrologRule`, `SPrologRule`, 
`EPrologRule`, `WPrologRule`. Di seguito, si riporta l'implementazione della `NPrologRule` in
codice Prolog (le altre regole sono state implementate in maniera simile).

```
% n_move(+int, +int, -int, -int): 
% finds all the possible positions to the north of the specified position (X1, Y1)
n_move(X1, Y1, X1, Y2) :- Y2 is Y1 + 1; n_move(X1, Y1 + 1, X1, Y2).
```

Come si può notare, uno dei problemi di questa regola è che non è _fully-relational_, ovvero 
le _variabili_ nella _testa_ della regola sono necessariamente o solo di input o
solo di output. Ciò è dovuto principalmente alla presenza dell'operatore `is`, usato per
eseguire calcoli numerici in Prolog.
\
Se la regola, fosse stata _fully-relational_, non ci sarebbe stato bisogno di implementare
anche la `SPrologRule`, infatti si sarebbe potuto riutilizzare la `NPrologRule` cambiando
semplicemente la posizione delle variabili nel risolvente, come mostrato nel codice seguente.

```
?- n_move(0,0,X,Y).   %return all north positions with respect to (0,0)
?- n_move(X,Y,0,0).   %return all south positions with respect to (0,0). It does not work!
```

Una volta realizzate le `PrologRule` per le direzioni cardinali, si è deciso di definire il
concetto di `DirectionalRule`, ovvero una regola degli scacchi che è caratterizzata da un
insieme di direzioni verso cui un pezzo può muoversi, modellate dal type alias `Direction`.
\
Una `Direction` non è altro che una funzione che, data una posizione di partenza, restituisce
una sequenza ordinata delle posizioni successive. Dalle `Direction` è poi possibile mappare
le posizioni libere sulla scacchiera in mosse eseguibili da un certo pezzo, come implementato
dalla `findMoves` della `DirectionalRule`.

A supporto della `DirectionalRule` e di altre regole, è stato definito un mixin chiamato 
`RuleShorthands`, che, quando esteso da una classe, abilita l'utilizzo di diverse funzionalità
che possono essere utili all'interno della definizione di una regola di gioco.
\
Siccome molte delle funzionalità richiedono lo stato della partita in input, modellato dalla
classe `ChessGameStatus`, si è deciso di richiedere tale stato sotto forma di _given instance_.
\
A tal proposito, è stata realizzata una funzione di utility che permette di definire given
instance in modo più dichiarativo, chiamata `GivenExtension.within`. 

Di seguito, si riporta un esempio della sua applicazione nel metodo `limitDirection` della `DirectionalRule`,
che restituisce l'insieme delle mosse possibili data una posizione sulla scacchiera e la direzione della mossa,
a cui ho contribuito solo parzialmente. 
\
Nell'esempio, nel blocco di codice all'interno di `within`, lo `status`
del gioco è disponibile come given instance, infatti è possibile usare il metodo `occupied` delle `RuleShorthands`
senza specificarlo.

```scala
import io.github.chess.util.scala.givens.GivenExtension.within
private def limitDirection(startingPosition: Position, status: ChessGameStatus, direction: Direction): Set[Move] =
  within(status) {
    val (availablePositions, obstructedPositions) = direction(startingPosition).span(!occupied(_))
    (availablePositions ++ obstructedPositions.take(1)).map(Move(startingPosition, _)).toSet
  }
```

Quindi, è stata implementata la `StraightRule`, come una `DirectionalRule` le cui direzioni
di movimento sono quelle verticali ed orizzontali, ovvero quelle di una torre. Infatti, la
`RookRule` è un tipo particolare di `StraightRule`.
\
Analogamente, è stata implementata la `AllDirectionsRule`, come una `DirectionalRule` le
cui direzioni di movimento sono l'unione delle direzioni della `StraightRule` e della 
`DiagonalRule`, ovvero quelle di una regina. Infatti, la `QueenRule` è un tipo particolare
di `AllDirectionsRule`.

Per completare l'implementazione, è stato necessario creare i pezzi relativi alla torre e
alla regina, modellati rispettivamente dalle classe `Rook` e `Queen`, a cui sono associate
le regole di gioco corrispondenti.

Sfruttando a pieno le capacità del DSL per la generazione della scacchiera (realizzato da
Jahrim), sono stati definiti dei test per le regole in modo estremamente dichiarativo, se
non puramente grafico. Di seguito, si riporta un esempio dei test relativi al movimento
della regina, contenuti nel `QueenRuleSpec`.

```scala
it should "be blocked by pieces of the same team" in {
  val chessGameStatus: ChessGameStatus = ChessGameStatus(ChessBoard {
    * | * | * | * | * | * | * | *
    * | * | N | * | * | * | * | *
    * | * | * | * | * | R | * | *
    * | N | * | * | Q | * | * | *
    * | * | * | K | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | R | * | * | *
    * | * | * | * | * | * | * | *
  })
  getChessBoardFromMoves(queenPosition, chessGameStatus) shouldEqual ChessBoard {
    * | * | * | * | X | * | * | *
    * | * | * | * | X | * | * | *
    * | * | * | X | X | * | * | *
    * | * | X | X | * | X | X | X
    * | * | * | * | X | X | * | *
    * | * | * | * | X | * | X | *
    * | * | * | * | * | * | * | X
    * | * | * | * | * | * | * | *
  }
}
```

Il test riportato, verifica che una regina non possa eseguire mosse sovrapponendosi a pezzi
della stessa squadra.
\
Delle due scacchiere contenute nel test, la prima rappresenta la scacchiera sulla quale si trova
la regina di cui si vogliono trovare le mosse disponibili; la seconda rappresenta una scacchiera
su cui le posizioni raggiungibili dalla regina sono segnate con una X.
\
Infatti, i test relativi alle regole di movimento possono estendere la classe `AbstractRuleSpec`
per avere accesso alla funzionalità `getBoardFromMoves`, che restituisce una scacchiera su
cui sono posizionati dei marker sulle posizioni di arrivo delle mosse disponibili per un
certo pezzo.

## Servizio relativo all'engine
Per quanto riguarda il servizio relativo all'engine, modellato dalla classe `ChessGame`, ho
contribuito implementando le funzionalità relative all'analisi della scacchiera. In particolare,
ho realizzato il `ChessGameAnalyzer`, contenente gli algoritmi per identificare le situazioni di
scacco, scacco matto e stallo, dato lo stato del gioco.

Il `ChessGameAnalyzer` permette di analizzare lo stato del gioco attraverso il metodo `situationOf`,
che restituisce la situazione sulla scacchiera dal punto di vista di uno dei due giocatori (squadra
bianca o nera). Quindi, dalla prospettiva del giocatore di turno, le possibili situazioni, modellate
dalla classe `ChessGameSituation`, sono:
- `Check`: è verificata se esiste una mossa dell'avversario che gli permette di muovere un pezzo
  sul re del giocatore di turno. L'algoritmo di verifica è implementato come segue;
  ```scala
  def check(state: ChessGameStatus, teamPerspective: Team): Boolean =
    state.chessBoard.pieces(teamPerspective.oppositeTeam).exists {
      (opponentPosition, opponentPiece) =>
        opponentPiece.rule
          .findMoves(opponentPosition, state)
          .flatMap { move => state.chessBoard.pieces(teamPerspective).get(move.to) }
          .exists { case _: King => true; case _ => false }
    }
  ```
  
- `Stale`: è verificata se il giocatore di turno non ha mosse disponibili. L'algoritmo di verifica è
  implementato come segue;
  ```scala
  def stale(state: ChessGameStatus, teamPerspective: Team): Boolean =
    state.chessBoard
      .pieces(teamPerspective)
      .flatMap((position, piece) => piece.rule.findMoves(position, state))
      .isEmpty
  ```
- `CheckMate`: è verificata se il giocatore di turno si trova sia in `Check` che in `Stale`.

In seguito a refactoring, gli altri componenti del gruppo hanno anche aggiunto la situazione di 
`Promotion` a quelle esistenti.

Oltre al `ChessGameAnalyzer`, ho contribuito alla funzionalità di registrazione e cancellazione delle 
sottoscrizioni agli eventi del servizio relativo all'engine. In particolare, ho contribuito 
all'implementazione delle funzionalità `subscribe` e `unsubscribe` definite nel contratto del servizio.

## Interfaccia grafica
Per quanto riguarda l'interfaccia grafica del sistema, ho contribuito realizzando la schermata di gioco
dell'applicazione, definita nei file _game-page.fxml_ e _game-page.css_, e il controller relativo alla
scacchiera in tale schermata, modellato dalla classe `ChessBoardController`.

Alla creazione della pagina `GamePage`, quando il suo controller `GameController` viene inizializzato,
si costruisce il `ChessBoardController` a partire da una `GridPane` contenente le celle della scacchiera.
\
La costruzione del `ChessBoardController` è delegata al _factory method_ `fromGridPane` che mappa ogni cella
di una `GridPane` a una `CellView`, a cui è associata una posizione nella scacchiera. 
\
A supporto di questa operazione e di altre relative alla grafica, sono state realizzate diverse funzioni di
utilità all'interno della classe `FXUtils`.

Una `CellView` gestisce la rappresentazione grafica di una cella nella scacchiera, permettendo la visualizzazione
di un pezzo al suo interno, oppure la visualizzazione di un effetto relativo alla possibilità di spostare un pezzo
su tale cella, o ancora di evidenziare la cella con colori diversi.
\
Per visualizzare un pezzo, una `CellView` richiede in input una `PieceView`, che gestisce la rappresentazione
grafica di un pezzo degli scacchi, mappandolo alla corrispondente immagine.

Il `ChessBoardController` estende la classe `StatefulSystem`, che rappresenta un sistema con uno stato, permettendo
di specificarne il comportamento di entrata e di uscita da uno dei possibili stati che può assumere. Lo stato del 
`ChessBoardController` può essere:
- `NoneSelected`: in questo stato, nessun pezzo sulla scacchiera è stato ancora selezionato.
- `PieceSelected`: in questo stato, un pezzo è stato selezionato e le sue mosse disponibili sono visibili sulla 
   scacchiera.

Alla creazione il `ChessBoardController` inizializza gli handler relativi alla selezione tramite click di una 
`CellView` all'interno della scacchiera. Quando una `CellView` viene cliccata, lo stato del `ChessBoardController`
cambia di conseguenza, secondo il seguente comportamento:

```scala
private def onCellClicked(event: MouseEvent, clickedCell: CellView): Unit =
  this.getState match
    case NoneSelected => this.selectCell(clickedCell)
    case PieceSelected(selectedCell, availableMoves) =>
      availableMoves.get(clickedCell.position) match
        case Some(selectedMove) =>
          this.context.chessEngineProxy.applyMove(selectedMove)
          enter(NoneSelected)
        case None if clickedCell.position == selectedCell.position => enter(NoneSelected)
        case _ => this.selectCell(clickedCell)

private def selectCell(cell: CellView): Unit =
  this.chessBoardBelief.get(cell.position).map(_ => cell) match
    case Some(selectedCell) =>
      getAvailableMoves(selectedCell).onComplete {
        case Success(availableMoves) =>
          Platform.runLater { enter(PieceSelected(selectedCell, availableMoves)) }
        case Failure(exception) => throw exception
      }
    case None => enter(NoneSelected)

private def getAvailableMoves(cell: CellView): Future[Map[Position, Move]] =
  this.context.chessEngineProxy
      .findMoves(cell.position)
      .map(moves => moves.map(move => move.to -> move).toMap[Position, Move])
```

Come visto nel codice precedente, quando una `CellView` della scacchiera è premuta, si valuta lo stato del 
`ChessBoardController`:
- se lo stato è `NoneSelected` e la cella contiene un pezzo, la cella cliccata viene selezionata e quindi evidenziata.
  In tal caso, si richiede alla chess engine le possibili mosse che possono essere eseguite da quel pezzo, quindi 
  si entra in `PieceSelected`.
- se lo stato è `PieceSelected` e la cella cliccata è una delle posizioni raggiungibili dal pezzo selezionato,
  si richiede all'engine di eseguire la mossa che sposta il pezzo sulla cella cliccata.
  In ogni caso, alla fine la cella viene deselezionata, e in base alla cella cliccata si ritorna a `NoneSelected`
  o a `PieceSelected`.

[Back to index](../../index.md) |
[Back to implementation](../../6-implementation/index.md)
