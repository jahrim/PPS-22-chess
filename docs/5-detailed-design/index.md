# Design di dettaglio
- Detailed software components...
  - Class diagrams...
  - Design patterns (strategy...)...

Come spiegato precedentemente, il sistema rappresentato in figura è suddiviso in due moduli:
- Engine Module
- Application Module

![diagramma](TODO)

Come si vede in figura, l'_Application Module_ dipende dall'_Engine Module_, in particolare utilizza
un `ChessLocalProxy` per comunicare con un `ChessLocalAdapter` messo a disposizione dall'engine.

Da notare come queste due classi per la comunicazione siano solo fittizie, in quanto il servizio e
l'applicazione vengono eseguiti sulla stessa macchina.

## Engine Module

La classe principale di questo modulo è il `ChessService`, il quale permette di avviare il servizio
relativo all'engine, inizializzando i vari `Adapter` e le relative porte del servizio.
Al momento il sistema presenta un unico _Adapter_, il `ChessLocalAdapter` che permette solo
interazioni locali con la `ChessPort` relativa al servizio, del quale espone il contratto
come visto nel _Design Architetturale_.
La `ChessPort` è implementata dalla classe `ChessGame`, la quale amministra un gioco di scacchi, permettendo l'avvio
e lo sviluppo di più partite, gestendole una alla volta.

Per poter avviare una partita, è necessaria una configurazione iniziale, modellata dalla classe `GameConfiguration`,
in cui vengono indicate le seguenti informazioni:
- `GameMode`: indica la modalità di gioco di una partita, individuando quindi i tipi di giocatori. Le modalità 
  possibili sono:
  - **PVP**: modalità attualmente implementata, che consiste nella sfida tra due giocatori reali.
  - **PVE**: modalità non implementata, che invece consiste in una sfida tra giocatore reale e computer.
- `TimeConstraint`: indica il vincolo temporale impostato alla partita. Esso può essere:
  - **NoLimit**: indica l'assenza di vincolo temporale.
  - **MoveLimit**: indica il vincolo temporale di limite di tempo per mossa.
  - **PlayerLimit**: indica il vincolo temporale di limite di tempo per giocatore, distribuito su tutte le mosse di 
    quest'ultimo.
- `Player`: indica uno dei due giocatori della partita, identificato dal proprio nome e dal _Team_ di appartenenza.

Nel caso di una partita con limiti di tempo, l'engine delega al `TimerManager` la gestione del vincolo temporale.

Una volta avviata la partita, è possibile ottenerne lo stato modellato dalla classe `ChessGameStatus`, la quale 
permette di accedere alle seguenti informazioni:
- `ChessBoard`: rappresenta la scacchiera contenente quindi la disposizione di tutti i pezzi del gioco.
- `ChessGameHistory`: rappresenta lo storico delle mosse, in particolare permette anche di ottenere le mosse specifiche 
  per un pezzo.
- `Team`: rappresenta la squadra **White** o **Black**. In questo caso viene utilizzata per rappresentare la squadra di 
  turno.
- `GameConfiguration`: rappresenta la configurazione della partita.

Una `ChessBoard` contiene un insieme di caselle, modellate dalla classe `Position`, identificate da un `File` e da 
un `Rank`, a cui sono associati dei `Piece`.
I `Piece` possono essere di vario tipo: `Queen`, `King`, `Rook`, `Knight`, `Bishop` e `Pawn`.
Questi sono caratterizzati da un `Team` di appartenenza e da una propria regola di movimento, modellata da una 
`ChessRule`.
Quest'ultima offre la possibilità di ritrovamento di mosse disponibili, a partire da una posizione data e dallo 
stato del gioco.

... rules

A partita avviata, è possibile ottenere l'insieme delle mosse disponibili da una specifica posizione.
Una mossa è rappresentata dalla classe `Move`, la quale contiene la `Position` di partenza e quella di arrivo.
Inoltre, sono presenti altri tipi di mosse più specifiche che estendono _Move_:
- `CaptureMove`: modella la cattura di un pezzo. Rispetto alla _Move_ contiene quindi anche il pezzo catturato.
- `CastlingMove`: modella l'_Arrocco_, perciò necessita della posizione di partenza e arrivo della torre 
  da spostare.
- `DoubleMove`: modella la _Mossa Doppia_ del pedone.
- `EnPassantMove`: modella la _Presa al varco_, contenente quindi anche la posizione del pedone catturato.


In ogni momento, è possibile sottoscriversi ai diversi eventi dell'engine, ricevendoli quando sono generati e 
quindi potendo reagire di conseguenza.
I tipi di eventi che vengono generati estendono la classe `Event` e sono:
- `GameOverEvent`: evento generato quando la partita è terminata per qualsiasi motivo. Esso contiene la causa del 
  termine della partita, modellata dalla classe `GameOverCause` e opzionalmente il vincitore.
- `PieceMovedEvent`: evento generato sia quando un pezzo viene mosso, sia quando viene effettuata la promozione.
- `PromotingPawnEvent`: evento generato quando un pedone deve essere promosso, informando della sua posizione e dei 
  pezzi in cui esso si possa promuovere. Quest'ultimo concetto viene modellato dalla classe `PromotionPiece`.
- `TimePassedEvent`: evento generato ogni secondo, nel caso la partita sia stata impostata con un vincolo temporale.
  Esso informa del tempo rimasto al giocatore corrente.

Al termine dell'applicazione della mossa, l'engine controlla la scacchiera delegando l'analisi al `ChessGameAnalyzer`
il quale è in grado di riconoscere le varie situazioni di scacco, stallo o scatto matto.

Inoltre, è possibile promuovere i pedoni e arrendersi.

![diagramma](TODO)

## Application Module

La classe principale di questo modulo è `ChessApplication`, che permette di avviare l'interfaccia grafica fornendole
il proxy per comunicare con l'engine.

L'applicazione è composta da diversi componenti modellati dalla classe `ChessApplicationComponent` a cui viene fornito
il contesto dell'applicazione, modellata dalla classe `ChessApplicationContext`, la quale permette di accedere da una 
parte allo _Stage_ primario dell'applicazione e dall'altra al proxy.

Un `FXComponent` è un componente che ha accesso alle funzionalità implicite di ScalaFX.
Uno `StageComponent` è un _FXComponent_ che gestisce un proprio _Stage_ (eventualmente non quello primario).
Esistono due tipi di _FXComponent_:
- `Page`: gestisce la visualizzazione di una schermata dell'applicazione.
  Un tipo di particolare di _Page_ è un `FXMLPage` la cui rappresentazione grafica è definita da un file _FXML_.
- `Controller`: gestisce la logica d'interazione con un certo componente grafico dell'applicazione.
  Un tipo particolare di _Controller_ è un `FXMLController` che estende l'interfaccia _Initializable_, permettendo 
  all'_FXMLLoader_ di ScalaFX d'inizializzarlo in autonomia.

Nell'applicazione sono presenti tre schermate principali:
- `MainMenuPage`: rappresenta la pagina iniziale ed è controllata dal `MainMenuController`.
- `GameConfigurationPage`: rappresenta la pagina di configurazione di una partita ed è controllata dal 
  `GameConfigurationController`.
- `GamePage`: rappresenta la pagina in cui si sviluppa una partita ed è controllata dal `GameController`. 
  In particolare la scacchiera è composta da un insieme di `CellView` ed è controllata dal `ChessBoardController`.
  La rappresentazione grafica di ciascuno dei pezzi sulla scacchiera è gestita dalla classe `PieceView`.

Il _GameController_ alla creazione si sottoscrive agli eventi dell'engine e aggiorna lo stato visualizzato della 
partita, in relazioni agli eventi ricevuti.


[Back to index](../index.md) |
[Previous Chapter](../4-architectural-design/index.md) |
[Next Chapter](../6-implementation/index.md)