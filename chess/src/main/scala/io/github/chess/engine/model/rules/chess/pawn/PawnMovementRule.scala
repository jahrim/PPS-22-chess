/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.pawn

import PawnMovementRule.*
import io.github.chess.util.scala.givens.GivenExtension.within
import io.github.chess.engine.model.board.{Position, Rank}
import io.github.chess.engine.model.game.{ChessGameStatus, Team}
import io.github.chess.engine.model.moves.Move
import io.github.chess.engine.model.pieces.Pawn
import io.github.chess.engine.model.rules.chess.{ChessRule, RuleShorthands}

/** Set of Pawn movement rules. */
class PawnMovementRule extends ChessRule with RuleShorthands:
  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    val firstStep = forwardOneRule.findMoves(position, status)
    if firstStep.nonEmpty && isFirstMove(position, status) then
      firstStep ++ doubleMoveRule.findMoves(position, status)
    else firstStep

  private def isFirstMove(position: Position, status: ChessGameStatus): Boolean =
    within(status) {
      isFirstMovementOf(position) && (pieceAt(position) match
        case Some(pawn: Pawn) if pawn.team == Team.WHITE => position.rank == Rank._2
        case Some(pawn: Pawn) if pawn.team == Team.BLACK => position.rank == Rank._7
        case _                                           => false
      )
    }

/** Companion object of [[PawnMovementRule]]. */
object PawnMovementRule:
  private val forwardOneRule: ForwardOneRule = ForwardOneRule()
  private val doubleMoveRule: DoubleMoveRule = DoubleMoveRule()
