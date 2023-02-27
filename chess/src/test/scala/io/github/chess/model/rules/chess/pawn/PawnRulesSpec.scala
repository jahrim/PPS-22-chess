/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.pawn

import io.github.chess.AbstractSpec
import io.github.chess.model.pieces.Pawn
import io.github.chess.model.{ChessBoard, ChessGameStatus, File, Position, Rank, Team}

/** Test suit for all Pawn movement rules. */
class PawnRulesSpec extends AbstractSpec:

  private val chessBoard: ChessBoard = ChessBoard.empty.setPiece((0, 1), Pawn(Team.WHITE))
  private val status = ChessGameStatus(chessBoard)

  "Single Step Rule" should "give an empty set if called on a pawned positioned on the adversary border" in {
    val whitePawnPosition: Position = (4, 7)
    chessBoard.setPiece(whitePawnPosition, Pawn(Team.WHITE))
    SingleStepRule().findMoves(whitePawnPosition, status) should have size 0

    val blackPawnPosition: Position = (4, 0)
    chessBoard.setPiece(blackPawnPosition, Pawn(Team.BLACK))
    SingleStepRule().findMoves(blackPawnPosition, status) should have size 0
  }

  "Two Step Rule" should "give an empty set if called on a pawned positioned on the adversary border" in {
    val whitePawnPosition: Position = (4, 7)
    chessBoard.setPiece(whitePawnPosition, Pawn(Team.WHITE))
    TwoStepRule().findMoves(whitePawnPosition, status) should have size 0

    val blackPawnPosition: Position = (4, 0)
    chessBoard.setPiece(blackPawnPosition, Pawn(Team.BLACK))
    TwoStepRule().findMoves(blackPawnPosition, status) should have size 0
  }

  it should "give an empty set if called on a pawned positioned on the rank before the adversary border" in {
    val whitePawnPosition: Position = (4, 6)
    chessBoard.setPiece(whitePawnPosition, Pawn(Team.WHITE))
    TwoStepRule().findMoves(whitePawnPosition, status) should have size 0

    val blackPawnPosition: Position = (4, 1)
    chessBoard.setPiece(blackPawnPosition, Pawn(Team.BLACK))
    TwoStepRule().findMoves(blackPawnPosition, status) should have size 0
  }

  "The Forward rule" should "let move the pawn only to the following rank, without changing its file" in {
    val pawnInitialPosition: Position = (0, 1)
    val pawnNextPosition: Position = (0, 2)
    val oneStepRule = ForwardOneRule()
    val moves = oneStepRule.findMoves(pawnInitialPosition, status)
    moves should have size 1
    all(moves) should have(Symbol("to")(pawnNextPosition))
  }

  "Double move rule" should "let move the pawn only to the rank two steps ahead of the pawn's current position, " +
    "without changing its file" in {
      val pawnInitialPosition: Position = (0, 1)
      val pawnDoubleStepPosition: Position = (0, 3)
      val moves = DoubleMoveRule().findMoves(pawnInitialPosition, status)
      moves should have size 1
      all(moves) should have(Symbol("to")(pawnDoubleStepPosition))
    }

// TODO add test to check that the double step works only on the first move of the pawn and not on the next ones.
//  it should "give no moves if the pawn is not in its first position" in {
//    val moves = doubleStepRule.findMoves(pawnNextPosition)
//    moves should have size 0
//  }
