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
Inoltre, sono presenti altri tipi di mosse più specifiche che estendono `Move`:
- `CaptureMove`: modella la cattura di un pezzo. Rispetto alla `Move` contiene quindi anche il pezzo catturato.
- `CastlingMove`: modella l'_Arrocco_, perciò necessita della posizione di partenza e arrivo della torre 
  da spostare.
- `DoubleMove`: modella la _Mossa Doppia_ del pedone.
- `EnPassantMove`: modella la _Presa al varco_, contenente quindi anche la posizione del pedone catturato.


-- subscribe -> parlare degli eventi

Inoltre, è possibile applicare le mosse, promuovere i pedoni e arrendersi.

![diagramma](TODO)

## Application Module


[Back to index](../index.md) |
[Previous Chapter](../4-architectural-design/index.md) |
[Next Chapter](../6-implementation/index.md)