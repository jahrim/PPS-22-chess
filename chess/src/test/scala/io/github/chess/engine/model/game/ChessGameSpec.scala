/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.game

import io.github.chess.AbstractSpec
import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.configuration.GameConfiguration
import io.github.chess.engine.model.game.ChessGame
import io.github.chess.engine.model.moves.Move
import io.vertx.core.Vertx

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, SECONDS}

class ChessGameSpec extends AbstractSpec:

  val blackPawnPosition: Position = (0, 6)
  val whitePawnPosition: Position = (0, 1)
  val whitePawnMove: Move = Move(whitePawnPosition, (0, 3))
  val chessGame: ChessGame = ChessGame(Vertx.vertx())
  val maxDuration: Duration = Duration(5, SECONDS)
  Await.result(chessGame.startGame(GameConfiguration.default), maxDuration)

  "The Chess Game" should "let the white player move a pawn of their team in the beginning of a standard game" in {
    Await.result(chessGame.getState, maxDuration).currentTurn should be(Team.WHITE)
    Await.result(chessGame.findMoves(whitePawnPosition), maxDuration) shouldNot be(empty)
  }

  it should "forbid the initial player from moving a pawn of the black team" in {
    Await.result(chessGame.findMoves(blackPawnPosition), maxDuration) should be(empty)
  }

  it should "change the turn after a move" in {
    Await.result(chessGame.applyMove(whitePawnMove), maxDuration)
    Await.result(chessGame.getState, maxDuration).currentTurn should be(Team.BLACK)
    Await.result(chessGame.findMoves(blackPawnPosition), maxDuration) shouldNot be(empty)
  }

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
