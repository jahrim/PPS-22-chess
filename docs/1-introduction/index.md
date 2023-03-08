# Introduzione
In questo progetto si vuole realizzare un sistema che permetta a due utenti di giocare a scacchi sullo stesso 
dispositivo.

Per definire in maniera univoca i requisiti e il corrispettivo linguaggio, a ogni concetto viene correlato lo 
specifico termine che verrà utilizzato anche nel software.

Gli scacchi sono un gioco di strategia in cui è prevista una scacchiera (_ChessBoard_) di gioco, contenente celle 
(_Position_) identificate da colonna (_File_) e riga (_Rank_).

Sulla scacchiera sono presenti dei pezzi (_Piece_), ovvero delle pedine, di colori bianco (_White_) e nero (_Black_)
corrispondenti alle due squadre (_Team_) nel gioco. Esistono diversi tipi di pezzi:
- Pedone (_Pawn_)
- Cavallo (_Knight_)
- Alfiere (_Bishop_)
- Torre (_Rook_)
- Re (_King_)
- Regina (_Queen_)

Ogni pezzo è caratterizzato da un insieme di mosse (_Move_) che può eseguire: 
- si può _muovere_ sulla scacchiera in base a delle specifiche regole di movimento
- può _catturare_ i pezzi della squadra avversaria in base a delle specifiche regole di cattura. Durante la _cattura_ di 
  un pezzo, il pezzo viene rimosso dalla scacchiera e rimpiazzato dal pezzo attaccante.

Quando un pezzo può catturare il re avversario nel turno successivo, la situazione prende il nome di scacco (_Check_). 
Nel caso in cui il giocatore non può effettuare una mossa per uscire dalla situazione di scacco, allora si parla di 
scacco matto (_CheckMate_). Quando invece un giocatore non si trova nella situazione di scacco ma non può muovere 
nessun pezzo, allora si parla di stallo (_Stale_).

Il gioco si svolge a _turni_ alternati tra i giocatori. Durante un turno, un giocatore può effettuare una mossa con uno
dei suoi pezzi. Lo scopo del gioco è quello di dare lo scacco matto al proprio avversario.

[Back to index](../index.md) | 
[Next Chapter](../2-development-process/index.md)
