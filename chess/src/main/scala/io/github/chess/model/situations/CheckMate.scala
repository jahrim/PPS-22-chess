/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.situations

import io.github.chess.model.ChessGameStatus

/**
 * The "checkmate" situation in a chess game is when the king is always captured
 * by the opponent in the next turn.
 */
class CheckMate extends ChessBoardSituation:
  override def checkOn(state: ChessGameStatus): Boolean = ???
