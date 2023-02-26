/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.pawn

import io.github.chess.model.{Position, ChessGameStatus}
import io.github.chess.model.rules.chess.{ChessRule, RuleShorthands}
import io.github.chess.model.moves.Move
import PawnMovementRule.*

/** Set of Pawn movement rules. */
class PawnMovementRule extends ChessRule with RuleShorthands:
  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    val firstStep = forwardOneRule.findMoves(position, status)
    if firstStep.nonEmpty && isFirstMovementOf(position)(using status) then
      firstStep ++ doubleMoveRule.findMoves(position, status)
    else firstStep

/** Companion object of [[PawnMovementRule]]. */
object PawnMovementRule:
  private val forwardOneRule: ForwardOneRule = ForwardOneRule()
  private val doubleMoveRule: DoubleMoveRule = DoubleMoveRule()
