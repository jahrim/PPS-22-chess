/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.AbstractSpec

class ChessGameSpec extends AbstractSpec

/* TODO consider these tests
 "The chess board" should "initially have a piece in the position a2" in {
   chessBoard.pieces.get(pawnInitialPosition) should be(defined)
 }

 it should "let you move its pawn from a2 to a3" in {
   chessBoard.findMoves(pawnInitialPosition) should contain(pawnNextPosition)
 }

 it should "see the new position of the pawn, after its move" in {
   chessBoard.move(firstMove)
   chessBoard.pieces.get(pawnInitialPosition) shouldNot be(defined)
   chessBoard.pieces.get(pawnNextPosition) should be(defined)
 }


//  it should "forbid the player from moving the pieces of the opposite team" in {
//    chessBoard.move(firstMove)
//    chessBoard.pieces.get(pawnNextPosition) should be(defined)
//    val thirdPosition = pawnNextPosition.rankUp()
//    val secondMove = Move(pawnNextPosition, thirdPosition)
//    chessBoard.move(secondMove)
//    chessBoard.pieces.get(pawnNextPosition) should be(defined)
//    chessBoard.pieces.get(thirdPosition) shouldNot be(defined)
//  }

 "All the moves" should "be available from the same starting position" in {
   all(chessBoard.findMoves(pawnInitialPosition)) should have(Symbol("from")(pawnInitialPosition))
 }
 */
