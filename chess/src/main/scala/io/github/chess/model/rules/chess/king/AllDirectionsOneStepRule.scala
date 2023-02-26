/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.king

import io.github.chess.model.moves.Move
import io.github.chess.model.rules.chess.ChessRule
import io.github.chess.model.{ChessBoard, ChessGameStatus, Position}
import io.github.chess.util.general.Combinator
import AllDirectionsOneStepRule.*

/** Represents the chess rule that can find the moves in all the directions stepped by one. */
class AllDirectionsOneStepRule extends ChessRule:
  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    Combinator
      .generatePositions(values, (x, y) => !(x == 0 && y == 0), position)
      .filter((x, y) => x >= 0 && x < ChessBoard.Size && y >= 0 && y < ChessBoard.Size)
      .map(Move(position, _))

/** Companion object of [[AllDirectionsOneStepRule]]. */
object AllDirectionsOneStepRule:
  private final val values = Set(0, -1, 1)
