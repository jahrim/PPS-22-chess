/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.rook

import io.github.chess.model.moves.Move
import io.github.chess.model.rules.chess.ChessRule
import io.github.chess.model.{ChessGameStatus, Position}

/** Represent the movement rule for the [[Rook]]. */
class RookRule extends ChessRule:
  private val straightRule = StraightRule()
  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    this.straightRule.findMoves(position, status)
