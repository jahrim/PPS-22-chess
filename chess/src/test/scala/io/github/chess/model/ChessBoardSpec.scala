/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.AbstractSpec

/** Test suit for the [[ChessBoard]]. */
class ChessBoardSpec extends AbstractSpec:

  val pawnPosition: Position = Position(File.A, Rank._2)
  val possibleDestination: Position = Position(File.A, Rank._3)
  val move: Move = Move(pawnPosition, possibleDestination)
  val chessBoard: ChessBoard = ChessBoard()

  "The chess board" should "have a pawn in the position a2" in {
    chessBoard.pieces.get(pawnPosition) should be(defined)
  }

  it should "let you move its pawn from a2 to a3" in {
    chessBoard.findMoves(pawnPosition) should contain(move)
  }

  it should "see the new position of the pawn, after its move" in {
    chessBoard.move(move)
    chessBoard.pieces.get(pawnPosition) shouldNot be(defined)
    chessBoard.pieces.get(possibleDestination) should be(defined)
  }
