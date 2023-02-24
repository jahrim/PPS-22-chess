/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.king

import io.github.chess.model.moves.Move
import io.github.chess.model.rules.chess.ChessRule
import io.github.chess.model.{ChessGameStatus, Position, moves}
import io.github.chess.util.general.Combinator

/** Represents the chess rule that can find the moves in all the directions stepped by one. */
class AllDirectionsOneStepRule extends ChessRule:

  private final val values = Set(0, -1, 1)

  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    Combinator
      .generatePositions(values, (x, y) => !(x == 0 && y == 0), position)
      .filter((x, y) => x >= 0 && x <= 7 && y >= 0 && y <= 7)
      .map(moves.Move(position, _))
