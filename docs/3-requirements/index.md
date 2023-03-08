# Requisiti di sistema
Durante l'analisi del problema sono stati individuati i seguenti requisiti del sistema da realizzare.

## Business
- Creare un sistema in grado di poter effettuare una partita a scacchi,
  completa di tutte le regole di cui è provvisto il gioco originale.
- Permettere di giocare le partite in modalità interattiva tra due utenti reali,
  in modalità _hotseat_.

## Utente
- Gli utenti dovranno interagire con il sistema tramite un'interfaccia grafica (GUI).
- Gli utenti possono visualizzare i diversi fattori che rappresentano lo stato attuale della partita:
    - la disposizione delle pedine
    - il giocatore di turno
    - la pedina selezionata
    - le mosse disponibili della pedina selezionata
    - l'ultima mossa effettuata dall'altro giocatore
    - il tempo disponibile per eseguire la propria mossa
    - lo stato finale della partita
- Gli utenti possono interagire col sistema effettuando diverse azioni:
    - impostare la modalità di gioco
    - impostare i vincoli temporali
    - impostare il proprio nome
    - avviare la partita
    - selezionare/deselezionare una pedina
    - eseguire una mossa per la pedina selezionata
    - selezionare il pezzo a cui promuovere il pedone
    - eseguire la resa

## Funzionali
- La partita deve essere avviata con una certa configurazione che comprende:
    - i tipi di giocatore (umano)
    - i vincoli temporali (senza limiti, con limite per mossa definito dall'utente)
    - i nominativi del giocatore bianco e del giocatore nero
- Il gioco si svolge in maniere interattiva, facendo eseguire le mosse dei giocatori una volta a testa.
- Il campo da gioco deve essere rappresentato da una scacchiera, ossia una griglia in cui righe e colonne
  siano identificate rispettivamente da un numero da 1 a 8 e da una lettera da A a H,
  per cui le singole celle siano univoche rispetto alla combinazione dei due valori.
- A ogni giocatore devono essere assegnati i propri pezzi degli scacchi, disposti in maniera adeguata, e suddivisi
  per tipo, ossia 2 torri, 2 alfieri, 2 cavalli, 8 pedoni, 1 regina e 1 re.
- Ogni tipo di pezzo deve sapere le proprie regole di movimento, al fine di poter vedere le mosse disponibili:
    - Torre: la torre può muoversi orizzontalmente o verticalmente di N posizioni.
    - Alfiere: l'alfiere può muoversi diagonalmente di N posizioni.
    - Cavallo: il cavallo può muoversi seguendo una traiettoria ad _L_.
    - Pedone: il pedone può muoversi solo in avanti di una posizione.
    - Regina: la regina può muoversi in tutte le direzioni di N posizioni.
    - Re: il re può muoversi in tutte le direzioni di una sola posizione.
- Sono presenti alcune eccezioni a tali regole di movimento:
    - mossa doppia: il pedone può muoversi di 2 in avanti, nel caso in cui non abbia ancora effettuato 
      alcuna mossa.
    - l'arrocco: nel caso in cui il re non abbia ancora effettuato alcuna mossa, esso può muoversi di due caselle in 
      direzione di una delle due torri alla sua destra o sinistra, soltanto nel caso in cui anche queste non abbiano 
      effettuato alcuna mossa e non sia presente alcun altro pezzo nelle celle intermedie tra i due pezzi. A questo 
      punto la torre si muove del numero di caselle necessarie per scavalcare il re.
- Qualunque regola di movimento non deve permettere di scavalcare altri pezzi e deve obbligare il movimento nei limiti 
  della scacchiera.
- Ogni pezzo può effettuare la cattura di un altro pezzo dell'avversario, muovendosi nella posizione di tale
  pezzo, causando la rimozione dalla scacchiera.
- Sono presenti alcune eccezioni alle regole di cattura:
    - Il pedone effettua la cattura sempre e solo, muovendosi di una posizione in avanti in diagonale.
    - Quando un pedone avversario, effettuando la _mossa doppia_ finisce esattamente accanto, ovvero sulla stessa traversa
      e su colonna adiacente, al pedone del giocatore del turno successivo, quest'ultimo in tale turno può catturarlo 
      come se il pedone avversario si fosse mosso di un passo solo. Tale mossa viene definita _En Passant_.
- Il sistema deve essere in grado di riconoscere particolari stati del gioco:
    - scacco
    - scacco matto
    - stallo
    - resa

## Non funzionali
- Realizzazione di software in grado di essere facilmente ampliabile, in termini di estendibilità di funzionalità e 
  di preparazione a una eventuale modalità distribuita.
- Realizzazione di un'interfaccia grafica che aiuti l'utente a realizzare le mosse in maniera intuitiva e rapida, 
  mostrando quindi le mosse disponibili di un dato pezzo.
- Realizzazione di un algoritmo di analisi delle mosse disponibili che venga effettuata in modo sufficientemente 
  rapido, in maniera tale da non far attendere all'utente una quantità di tempo non trascurabile.
- Sviluppo di una notazione standard per le mosse che sia interoperabile con altri sistemi.

## Di implementazione:
- Utilizzo di Scala 3.x
- Utilizzo di TuProlog
- Utilizzo di JDK 11+

## Opzionali:
- Aggiunta di un'ulteriore modalità di gioco che consiste nella sfida contro un'intelligenza artificiale.
- Aggiunta di un ulteriore vincolo temporale in cui ogni giocatore ha un tempo preimpostato relativo al totale delle 
  mosse compiute, al termine della quale il giocatore di turno perde.
- Sviluppo di una funzionalità di tracciamento dei pezzi mangiati di ciascun giocatore.
- Sviluppo di un sistema di leaderboard con il numero di vittorie dei giocatori.
- Sviluppo di un sistema di replay delle partite.
- Impostazioni aggiuntive relative alla GUI.

[Back to index](../index.md) | 
[Previous Chapter](../2-development-process/index.md) | 
[Next Chapter](../4-architectural-design/index.md)
