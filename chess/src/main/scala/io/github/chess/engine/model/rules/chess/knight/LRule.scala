/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.knight

import io.github.chess.util.general.Combinator
import LRule.*
import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.game.ChessGameStatus
import io.github.chess.engine.model.moves.Move
import io.github.chess.engine.model.rules.chess.ChessRule

/** Represents the chess rule that can find all the moves executing an L direction movement. */
class LRule extends ChessRule:
  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    Combinator
      .generatePositions(values, position, (x, y) => x.abs != y.abs)
      .filter((x, y) => x >= 0 && x <= 7 && y >= 0 && y <= 7)
      .map(Move(position, _))

/** Companion object of [[LRule]]. */
object LRule:
  private final val values = Set(-1, 1, -2, 2)
