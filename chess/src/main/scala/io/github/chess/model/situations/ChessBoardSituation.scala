package io.github.chess.model.situations

import io.github.chess.model.ChessGameStatus

/**
 * Situation in a game of chess (i.e.: check, checkmate, stalemate...).
 */
trait ChessBoardSituation:
  /**
   * @param state the specified state of the game
   * @return true if the game in the specified state is in this situation, false otherwise.
   */
  def checkOn(state: ChessGameStatus): Boolean