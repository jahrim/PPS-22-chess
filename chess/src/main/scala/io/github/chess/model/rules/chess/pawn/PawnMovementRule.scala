/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.pawn

import io.github.chess.model.{ChessGameStatus, Position, Rank, Team}
import io.github.chess.model.rules.chess.{ChessRule, RuleShorthands}
import io.github.chess.model.moves.Move
import PawnMovementRule.*
import io.github.chess.model.pieces.Pawn

/** Set of Pawn movement rules. */
class PawnMovementRule extends ChessRule with RuleShorthands:
  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    val firstStep = forwardOneRule.findMoves(position, status)
    if firstStep.nonEmpty && isFirstMove(position, status) then
      firstStep ++ doubleMoveRule.findMoves(position, status)
    else firstStep

  private def isFirstMove(position: Position, status: ChessGameStatus): Boolean =
    isFirstMovementOf(position)(using status) && (pieceAt(position)(using status) match
      case Some(pawn: Pawn) if pawn.team == Team.WHITE => position.rank == Rank._2
      case Some(pawn: Pawn) if pawn.team == Team.BLACK => position.rank == Rank._7
      case _                                           => false
    )

/** Companion object of [[PawnMovementRule]]. */
object PawnMovementRule:
  private val forwardOneRule: ForwardOneRule = ForwardOneRule()
  private val doubleMoveRule: DoubleMoveRule = DoubleMoveRule()
