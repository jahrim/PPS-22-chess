/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.king

import KingRuleCollection.*
import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.game.ChessGameStatus
import io.github.chess.engine.model.moves.Move
import io.github.chess.engine.model.rules.chess.ChessRule

/** An aggregation of the [[KingMovementRule]] and the [[CastlingRule]]. */
class KingRuleCollection extends ChessRule:
  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    castlingRule.findMoves(position, status) ++
      kingMovementRuleWithPresence.findMoves(position, status)

/** Companion object of [[KingRuleCollection]]. */
object KingRuleCollection:
  private val castlingRule = CastlingRule()
  private val kingMovementRuleWithPresence = KingMovementRule()
