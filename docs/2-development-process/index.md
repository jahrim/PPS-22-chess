# Metodologia di sviluppo
In questa sezione si descriverà l'organizzazione del team di sviluppo.

## Scrum
La metodologia di sviluppo applicata su questo progetto è di tipo _Agile_, in particolare
si è adottata la variante _Scrum_.

Il processo di sviluppo Scrum è un processo _iterativo_ e _incrementale_, in cui a ogni
iterazione si aggiungono nuove funzionalità al sistema o si raffinano delle funzionalità
pre-esistenti.

La prima attività all'interno del processo di sviluppo Scrum è quella di redarre il
_Product Backlog_, ovvero una lista di funzionalità, dette _items_.

Le iterazioni del processo, chiamate _sprint_, saranno svolte settimanalmente. Ogni sprint
prevede le seguenti attività:
- _Sprint Planning_: riunione iniziale in cui si selezionano gli item da implementare
  durante lo sprint e si scompongono in sotto-funzionalità di cui il team di sviluppo
  fornisce stime sulla loro complessità e alcuni design di dettaglio. Al termine dello
  Sprint Planning si produce uno _Sprint Backlog_ che assegna a ogni membro del team
  dei _task_ da eseguire.
- _Daily Scrum_: una breve riunione giornaliera in cui il team di sviluppo si aggiorna
  sul progresso del progetto, riadattando lo Sprint Backlog.

Al termine dello sprint si prevedono due ulteriori attività:
- _Product Backlog Refinement_: una riunione in cui il team di sviluppo di accorda sulle
  migliorie che devono essere apportate al Product Backlog.
- _Sprint Review_: si valuta il progresso del progetto ottenuto al termine dello sprint,
  verificando che sia un _PSPI (Potentially Shippable Product Increment)_.
- _Sprint Retrospective_: si valuta il processo di sviluppo adottato, discutendo possibili
  cambiamenti che potrebbero aumentare l'efficacia del team.

L'organizzazione del personale all'interno del processo Scrum prevede tre ruoli:
- _Product Owner_: si occupa di redarre il _Product Backlog_ e di verificare l'adeguatezza del
  sistema realizzato. Il ruolo è stato assunto da Jahrim Gabriele Cesario;
- _Scrum Master_: agisce da esperto del processo Scrum e da mediatore tre gli altri due ruoli.
  Il ruolo è stato assunto da Mirko Felice;
- _Development Team_: si occupa di progettare soluzioni adeguate alle task definite dal Product
  Owner, stimando tempi di realizzazione e proponendo modifiche sul sistema al Product Owner.
  Il team di sviluppo sarà composto da:
    - Jahrim Gabriele Cesario
    - Mirko Felice
    - Maxim Derevyanchenko
    - Madina Kentpayeva

Le riunioni saranno svolte periodicamente su _Microsoft Teams_, mentre l'organizzazione
delle funzionalità del progetto sarà tenuta su [_Trello_](https://trello.com/b/2mgr8e1j/pps).

## Test-Driven Development
Durante lo sviluppo del sistema è stato scelto di applicare il più possibile il _Test-Driven Development (TDD)_,
il cui scopo è quello di anticipare il prima possibile la fase di testing per minimizzare i costi di manutenzione e 
il rischio di fallimento del progetto.

Il processo seguito durante il TDD è un processo iterativo chiamato _Red-Green-Refactor (RGR)_, che prevede a ogni
iterazione le seguenti fasi:
1. _Red_: scrivere un test che fallisca per una certa funzionalità da implementare
2. _Green_: scrivere il codice di produzione che soddisfi il test definito precedentemente
3. _Refactor_: ristrutturare sia il codice di testing che quello di produzione

A supporto di questo processo, sono stati adottati i seguenti strumenti:
- _ScalaTest_: framework per la definizione di unit test per scala
- _SCoverage_: strumento per valutare la qualità dei test come percentuale di codice di produzione analizzato

Poiché si è deciso di utilizzare il testing solo per quanto riguarda il modello, è stato scelto di utilizzare lo stile
di testing _FlatSpec_, in quanto questo stile non è puramente in stile _FunSuite_ perciò magari troppo poco 
descrittivo, e non è neanche troppo dettagliato e dispersivo come potrebbe risultare lo stile _FunSpec_.

Questa soluzione è sembrata la via di mezzo più adeguata per il Team di sviluppo e per cercare d'implementare i 
test da un punto di vista descrittivo.

## Quality Assurance
Per il controllo della qualità del sistema sono stati adottati i seguenti strumenti:
- _Scala Formatter_: controlla lo stile del codice scala
- _Wart Remover_: individua possibili difetti nel codice scala
- _Ktlint_: controlla lo stile del codice kotlin
- _Detekt_: individua possibili difetti nel codice kotlin

## Build Automation
Per automatizzare i processi di compilazione, testing e release del codice sviluppato si è deciso di utilizzare _Gradle_
come strumento di _Build Automation_.

È stato preferito Gradle al posto di Sbt, perché è una tecnologia più matura e gli sviluppatori hanno più esperienza
con tale strumento.

## Continuous Integration
Per fare in modo che il codice rimanga integro e corretto durante lo sviluppo, è stato utilizzato un workflow che
attraverso _GitHub Actions_ permette di eseguire i test del progetto su diverse configurazioni di sistemi operativi,
a ogni aggiornamento del progetto.

## Continuous Delivery
Analogamente, un altro workflow permette di automatizzare la release su _GitHub Releases_ e _Maven Central_.

## Automated Evolution
Per mantenere aggiornate le dipendenze del progetto in maniera automatica è stato utilizzato _Renovate Bot_, che osserva
le dipendenze del progetto e in caso di necessità apre delle _pull request_ sul repository GitHub per aggiornarle. 

## Versioning
Per il versionamento del sistema è stato adottato lo standard _Semantic Versioning_, il quale prevede che una versione
sia caratterizzata da:
- _Major_: numero identificativo da aggiornare a ogni cambiamento non-retrocompatibile
- _Minor_: numero identificativo da aggiornare a ogni aggiunta o modifica retrocompatibile di una funzionalità del 
  sistema
- _Patch_: numero identificativo da aggiornare a ogni correzione dei difetti del sistema

Attraverso l'adozione dello standard _Conventional Commits_ durante lo sviluppo tramite DVCS, è stato possibile
automatizzare l'assegnamento della versione al sistema sulla base dei commit effettuati. Per fare ciò, è stato sfruttato
_Semantic Release Bot_ per GitHub.

## Licensing
La licenza del progetto è di tipo _MIT_. \
Per verificare la compatibilità tra la licenza del progetto e quelle delle sue 
dipendenze è stato utilizzato _Fossa Bot_ per GitHub, che a ogni aggiornamento delle dipendenze esegue tale verifica.

[Back to index](../index.md) | 
[Previous Chapter](../1-introduction/index.md) | 
[Next Chapter](../3-requirements/index.md)
