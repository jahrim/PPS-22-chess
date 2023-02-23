/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.situations

import io.github.chess.model.ChessGameStatus

/** Situation in a game of chess (i.e.: check, checkmate, stalemate...). */
trait ChessBoardSituation:
  /**
   * @param state the specified state of the game
   * @return true if the game in the specified state is in this situation, false otherwise.
   */
  def checkOn(state: ChessGameStatus): Boolean
