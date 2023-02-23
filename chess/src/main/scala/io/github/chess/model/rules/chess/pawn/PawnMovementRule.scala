/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.pawn

import io.github.chess.model.rules.chess.ChessRule
import io.github.chess.model.*
import io.github.chess.model.moves.Move

/** Set of Pawn movement rules. */
class PawnMovementRule extends ChessRule:

  private val forwardOneRule: ForwardOneRule = ForwardOneRule()
  private val doubleMoveRule: DoubleMoveRule = DoubleMoveRule()

  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    val firstStep = forwardOneRule.findMoves(position, status)
    if firstStep.nonEmpty && isFirstMove(position, status) then
      firstStep ++ doubleMoveRule.findMoves(position, status)
    else firstStep

  private def isFirstMove(position: Position, status: ChessGameStatus): Boolean =
    position.rank == (status.chessBoard.pieces.get(position) match
      case Some(piece) =>
        piece.team match
          case Team.WHITE => Rank._2
          case Team.BLACK => Rank._7
      case None => false
    )
