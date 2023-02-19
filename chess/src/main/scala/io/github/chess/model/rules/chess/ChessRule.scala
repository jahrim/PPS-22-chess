/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess

import io.github.chess.model.moves.Move
import io.github.chess.model.{ChessGameStatus, Position}

/** A rule in the game of chess that determines the possible moves of a chess piece. */
trait ChessRule:
  /**
   * @param position the specified position
   * @param status   the specified state of the game
   * @return all the possible moves of the piece in the specified position, given the specified state of the game
   */
  def findMoves(position: Position, status: ChessGameStatus): Set[Move]

/** Companion object of [[ChessRule]]. */
object ChessRule:
  extension (self: Set[ChessRule])
    /**
     * Aggregate the rules contained by this set of chess rules.
     * @param position the specified position
     * @param status the specified state of the game
     * @return all the possible moves of the piece in the specified position, given the specified state of the game
     */
    def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
      self.flatMap(_.findMoves(position, status))
