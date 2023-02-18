/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.king

import io.github.chess.model.moves.Move
import io.github.chess.model.{ChessGameStatus, Position}
import io.github.chess.model.rules.chess.ChessRule

/** Represents the chess rule that can find the moves of the [[King]]. */
class KingRule extends ChessRule:

  private val castlingRule = CastlingRule()
  private val kingMovementRuleWithPresence = KingMovementRule()

  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    castlingRule.findMoves(position, status) ++
      kingMovementRuleWithPresence.findMoves(position, status)
