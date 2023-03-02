# Design Architetturale

Per quanto riguarda il design architetturale del sistema si identificano due macro-componenti principali,
i quali vengono suddivisi a loro volta in altri sotto-componenti:
- Engine: gestisce la creazione e lo sviluppo di una partita degli scacchi.
  È stato realizzato come servizio, seguendo un'architettura di tipo esagonale,
  anche conosciuta come _ports & adapters_.
  - Modello: definisce la _business logic_ del sistema.
  - Porta: espone le funzionalità del modello in rapporto a uno specifico caso d'uso del servizio.
  - Adapter: permette l'interazione con una specifica porta attraverso una specifica tecnologia.
- Applicativo: gestisce l'interazione dell'utente col servizio.
  - Proxy: media l'interazione tra l'applicazione e l'engine al fine di proteggere l'utilizzatore dai
    cambiamenti del servizio.
  - ViewController: gestisce l'interazione tra utente e l'applicazione.

![Design Architetturale](../images/architectural-design.png)

## Engine

Per esplicitare i vari casi d'uso, il contratto del servizio prevede le seguenti funzionalità:
- _Ottenimento dello stato del gioco corrente_: funzionalità che permette di ottenere lo stato della scacchiera, lo 
  storico delle mosse compiute, il turno del giocatore corrente e la configurazione della partita.
- _Avvio della partita_: funzionalità che, a partire da una data configurazione, permette di avviare 
  una partita.
- _Ottenimento delle possibili mosse_: funzionalità che, a partire da una posizione su una scacchiera, trova tutte le 
  possibili mosse.
- _Applicazione di una mossa_: funzionalità che applica una specifica mossa alla partita.
- _Promozione_: funzionalità che, a partire da una posizione e da un tipo di pezzo, promuove il pedone in tale posizione
  al pezzo specificato.
- _Resa_: funzionalità che permette al giocatore di turno di arrendersi.

In seguito all'applicazione delle varie funzionalità, vengono creati determinati eventi, ai quali è possibile 
sottoscriversi:
- _GameOverEvent_: evento generato per avvisare l'utente che la partita è terminata. Esso può essere
  generato in più momenti: quando un giocatore subisce lo scacco matto, quando il giocatore di turno non ha 
  mosse disponibili (stallo), quando un giocatore si arrende oppure allo scadere del tempo, nel caso in cui la 
  partita sia stata impostata con un vincolo temporale.
- _TimePassedEvent_: evento generato per avvisare l'utente che è trascorso del tempo, nel caso in cui
  la partita sia stata impostata con un vincolo temporale.
- _PieceMovedEvent_: evento generato per avvisare l'utente che un pezzo è stato mosso e che è cambiato il giocatore 
  di turno.
- _PromotingPawnEvent_: evento generato per avvisare l'utente che un pedone deve essere promosso.

[//]: # (TODO aggiungere diagrammi)

## View

L'applicazione prevede quattro schermate principali, presentati nei seguenti mockup.
Per estendibilità, i mockup sono stati realizzati considerando anche i requisiti opzionali.

### Main Menu

Nella schermata raffigurata, sono presenti quattro pulsanti:
- _New Game_: quando premuto, visualizza la pagina di configurazione della partita.
- _Leaderboard_: quando premuto, visualizza la pagina della classifica dei giocatori.
- _Settings_: quando premuto, visualizza la pagine per le impostazioni grafiche dell'applicazione.
- _Exit_: quando premuto, termina l'applicazione.

![Main Menu Page](TODO)

### Game Configuration

Nella schermata seguente, sono presenti vari controlli per permettere di configurare la partita, impostando diversi 
parametri, come vincoli temporali, modalità di gioco, nomi dei giocatori, difficoltà dell'AI.

Premendo il pulsante _Start Game_, viene visualizzata la pagina della partita, dopo aver richiesto l'avvio della 
partita all'engine.

![Game Configuration Page](TODO)

### Game 

In questa schermata, viene rappresentato lo stato corrente del gioco, di cui viene principalmente mostrata la 
scacchiera di gioco, la quale contiene tutti i pezzi attualmente disponibili.

Inoltre, vengono visualizzati il turno corrente, il tempo rimanente al giocatore di turno e l'ultima mossa
effettuata, i quali vengono mantenuti aggiornati in relazione agli eventi generati dall'engine.

Infine, è presente anche un pulsante di resa che, in seguito al comando appropriato all'engine, comporta l'immediata 
sconfitta del giocatore di turno.

Quando l'interfaccia viene notificata della terminazione della partita da parte dell'engine, viene visualizzata la 
finestra di risultato della partita.

In quest'interfaccia, è possibile selezionare una casella della scacchiera per due scopi diversi:
- Visualizzazione delle mosse possibili del pezzo selezionato
- Applicazione di una delle mosse visualizzate

Entrambe dipendono dalle rispettive funzionalità dell'engine.

Quando l'interfaccia viene notificata della avvenente promozione di un pedone, viene visualizzata la finestra di 
scelta del pezzo in cui promuoverlo.

![Game Page](TODO)

### Promotion Dialog

In seguito alla scelta, viene richiesto all'engine di completare la promozione del pedone.

![Promotion Dialog](TODO)

### Result Dialog

Tale finestra contiene il risultato finale della partita, il quale indica la causa della terminazione e un messaggio
contenente ulteriori dettagli.

![Result Dialog](TODO)


## Scelte Tecnologiche

Per la realizzazione del sistema, non saranno necessarie delle tecnologie specifiche, ma sicuramente
sarà necessaria una tecnologia per l'interfaccia grafica.
Inoltre, allo scopo di rendere il progetto predisposto a una modalità distribuita, è necessario un'ulteriore tecnologia 
che permetta all'engine di essere asincrono e reattivo.


[Back to index](../index.md) |
[Previous Chapter](../3-requirements/index.md) |
[Next Chapter](../5-detailed-design/index.md)