/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.pawn

import io.github.chess.model.{ChessGameStatus, Move, Position, Rank, Team}
import io.github.chess.model.rules.ChessRule

/** Set of Pawn movement rules. */
class PawnMovementRule extends ChessRule:

  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    val firstStep = ForwardOneRule().findMoves(position, status)
    if firstStep.nonEmpty && isFirstMove(position, status) then
      firstStep ++ DoubleMoveRule().findMoves(position, status)
    else firstStep

  private def isFirstMove(position: Position, status: ChessGameStatus): Boolean =
    position.rank == (status.currentTurn match
      case Team.WHITE => Rank._2
      case Team.BLACK => Rank._7
    )
