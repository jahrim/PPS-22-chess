/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.prolog

import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.game.ChessGameStatus
import io.github.chess.engine.model.moves.Move

/** Represents a rule that limits the positions inside the board. */
trait InsideBoardRule(private val maxX: Int = 7, private val maxY: Int = 7) extends PrologRule:

  abstract override def findPositions(position: Position): LazyList[(Int, Int)] =
    super.findPositions(position).takeWhile((x, y) => x >= 0 && x <= maxX && y >= 0 && y <= maxY)
