/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.king

import io.github.chess.util.general.Combinator
import AllDirectionsOneStepRule.*
import io.github.chess.engine.model.board.{ChessBoard, Position}
import io.github.chess.engine.model.game.ChessGameStatus
import io.github.chess.engine.model.moves.Move
import io.github.chess.engine.model.rules.chess.ChessRule

/** Represents the chess rule that can find the moves in all the directions stepped by one. */
class AllDirectionsOneStepRule extends ChessRule:
  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    Combinator
      .generateCoordinates(values, position, (x, y) => !(x == 0 && y == 0))
      .filter((x, y) => x >= 0 && x < ChessBoard.Size && y >= 0 && y < ChessBoard.Size)
      .map(Move(position, _))

/** Companion object of [[AllDirectionsOneStepRule]]. */
object AllDirectionsOneStepRule:
  private final val values = Set(0, -1, 1)
