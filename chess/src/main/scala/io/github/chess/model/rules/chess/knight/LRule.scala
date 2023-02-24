/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.knight

import io.github.chess.model.moves.Move
import io.github.chess.model.rules.chess.ChessRule
import io.github.chess.model.{ChessGameStatus, Position, moves}
import io.github.chess.util.general.Combinator

/** Represents the chess rule that can find all the moves executing an L direction movement. */
class LRule extends ChessRule:

  private final val values = Set(-1, 1, -2, 2)

  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    Combinator
      .generatePositions(values, (x, y) => x.abs != y.abs, position)
      .filter((x, y) => x >= 0 && x <= 7 && y >= 0 && y <= 7)
      .map(moves.Move(position, _))
