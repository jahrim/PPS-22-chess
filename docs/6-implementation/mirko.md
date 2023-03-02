Durante gli Sprint il lavoro è stato cercato di essere suddiviso il più possibile. Nel complesso
è stato sviluppato codice completamente da parte mia, mentre altre parti sono state sviluppate in cooperazione.

I file completamente attribuili al sottoscritto sono:
- `Position`
- `File`
- `Rank`
- `OutsideBoardException`
- `PromotingPawnEvent`
- `PromotionPiece`
- `GameConfiguration`
- `Player`
- `GameMode`
- `TimeConstraint`
- `Timer`
- `TimerManager`
- `TimePassedEvent`
- `Combinator`
- `SouthWestRule`
- `SouthEastRule`
- `CastlingRule`
- `CastlingMove`

Altri file a cui ho collaborato sono:
- `PrologEngine` (con max)
- `PrologRule` (con max)
- DiagonalRule? LRule? KingRule?
- `GameConfigurationPageController` (con struttura base di jahrim)
- `ChessGame` (tutti? -> promote, startGame(gestione del tempo), in parte applyMove)
- `ChessBoardController` (con tutti -> evidenziamento cattura)

Per meglio rappresentare il codice sviluppato, si possono raggruppare i file per gruppi di funzionalità.

## Configurazione della partita 
[//]: # (TODO eliminare? non importante?)

La configurazione della partita è stata sviluppata in realtà in maniera basilare poiché essa rappresenta
solamente un insieme d'informazioni.
Difatti essa contiene la `GameMode` implementata come un'enumerazione di casi, alias **PVP** e **PVE**.

....


## Casella (o cella)

Un concetto importante da sviluppare per quanto riguarda la scacchiera è il concetto di casella.

Questa è stata modellata come una classe `Position` identificata dal proprio `File` e dal proprio `Rank`, 
implementati come enumerazioni in quanto i valori sono e saranno sempre e solo quelli (da A a H per i _File_
e da 1 a 8 per i _Rank_).

### File/Rank

Una logica non citata in precedenza è la possibilità di avanzare o indietreggiare di un _File_ o di
aumentare o diminuire di un _Rank_ rispetto alla posizione attuale, implementata sviluppando le rispettive 
funzionalità all'interno di _Rank_ (`up()` e `down()`) e _File_ (`forward()` e `backward()`) utilizzando
l'idiomatico _**pattern matching**_.

Inoltre, per rappresentare il concetto di limite della scacchiera è stata modellata un'eccezione 
`OutsideBoardException`, la quale viene lanciata nel momento in cui si tenta ad aumentare il già massimo _Rank 8_ o
di diminuire il già minimo _Rank 1_.
Analogamente succede per quanto riguarda il _File_.

Poi, per cercare di adeguare la rappresentazione testuale al protocollo _Universal Chess Interface(UCI)_
è stato sovrascritto il metodo `toString()`.

### Position

Il file si compone di due strutture: trait e corrispettivo companion object.

#### Trait

Rappresenta il contratto dell'oggetto, per definire tutte le funzionalità che possono essere utilizzate
esternamente.

#### Object

Il companion object è stato utilizzato per diverse ragioni.
Prima di tutto per rendere la costruzione dell'oggetto _Position_ pulita per l'utilizzatore:
definendo infatti il metodo apply() è possibile costruire un oggetto senza mostrare la mera implementazione.
\
Es. _Position(File.A, Rank.\_1)_

Un'ulteriore ragione è la definizione di una funzionalità 
(`def findHorizontalBetween(p1: Position, p2: Position): Seq[Position]`) che permette di trovare 
tutte le posizioni orizzontalmente presenti tra due posizioni di partenza. 
Questa funzionalità è necessaria per controlli relativi alla regola dell'_arrocco_.

Infine, per aiutare in diverse situazioni la costruzione e decostruzione degli oggetti di `Position`, sono stati
creati due `given Conversion` per trasformare una coppia di valori interi a _Position_ e viceversa.
\
Es. _Position(0, 0) => Position(File.A, Rank.\_1)_

## Promozione

Per sviluppare la funzionalità di promozione, sono state necessarie due classi aggiuntive.

Difatti, prima di poter effettuare una promozione, l'engine deve avvisare l'utente che il pedone si trovi
nella posizione corretta.\
Per questo motivo, nel momento di applicazione della mossa, viene eseguita un'analisi per verificare che nella
posizione di arrivo sia ora presente un pedone, e che esso abbia raggiunto la base nemica (Rank 8 per i bianchi, 
Rank 1 per i neri).\
A questo punto, nel caso positivo viene pubblicato l'evento appropriato.

### PromotingPawnEvent

Questa classe rappresenta tale evento. 
Essa contiene infatti due informazioni necessarie all'applicativo: la posizione attuale del pedone e
l'insieme dei pezzi in cui poter promuovere il pedone stesso.

La posizione è necessaria per far capire all'engine quale pezzo deve sostituire, mentre l'insieme dei pezzi
serve all'interfaccia grafica per permettere di far scegliere all'utente in quale pezzo effettuare la promozione.

Inoltre, sempre per nascondere l'implementazione all'utilizzatore, la classe viene fornita di un _companion object_ 
capace di costruire l'oggetto.

### PromotionPiece

Siccome in realtà l'utente deve sapere solo il tipo del pezzo in cui promuoversi e siccome tali tipi
sono stabili, è stato scelto di costruire un'enumerazione che rappresenti tale concetto.

Come si vede in figura, ciascuno di questi contiene il _Class_ relativo al pezzo rappresentato.
In questa maniera, da parte dell'engine viene sfruttata la _reflection_ per costruire una nuova istanza del tipo di 
pezzo scelto.

[//]: # (TODO aggiungere screenshot)

## Gestione del tempo

Una partita può essere configurata indicando dei vincoli temporali.
È quindi necessario gestire il tempo nell'engine, a seconda della modalità scelta.

Innanzitutto per avvertire l'utente che il tempo sta passando, è stato creato un evento apposito:
`TimePassedEvent`, contenente il tempo rimanente.

Per rappresentare il passare del tempo è stato scelto d'implementare un agente in grado di effettuare il
_countdown_ in maniera asincrona.

### Timer

Il contratto di questa classe infatti, permette di effettuare funzionalità classiche di un timer.

Per rendere il codice estendibile, si è cercato di rendere la classe più generica possibile; infatti non vi è alcuna
dipendenza dal modello.

### TimerManager

Poiché la logica di gestione del tempo è abbastanza complessa e lunga si è deciso di separarla concettualmente in 
una classe a parte.
In effetti tale classe viene utilizzata come se fosse una collezione di `Timer`.\
Lavorando con le classi appena descritte si è pensato d'impostare diversi timer, a seconda del vincolo temporale
impostato per la partita.
\
Infatti, nel caso in cui il limite di tempo sia per mossa, viene impostato un solo timer, il quale viene
continuamente riavviato da capo al termine del turno.
\
Contrariamente nel caso in cui il limite di tempo sia per giocatore, è necessario memorizzarsi il tempo
rimanente di ciascun giocatore, e per far ciò si è optato di usufruire di due timer diversi, uno per ogni
giocatore.
In questa maniera, al termine del turno viene fermato il timer del giocatore di turno corrente e fatto ripartire
quello del giocatore avversario.

## Regole

[//]: # (TODO fare)

### CastlingRule

### CastlingMove

### Combinator

Questo _object_ permette di generare nuove coordinate, a partire da una coppia di coordinate e da un insieme
di valori, opzionalmente filtrando anche dei valori.

[//]: # (TODO aggiungere screenshot)

Il codice infatti sfrutta la _**for comprehension**_ di Scala per combinare i possibili valori di _X_ e _Y_,
da aggiungere poi alla coppia di coordinate specificata.