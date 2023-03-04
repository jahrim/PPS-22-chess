/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.pawn

import io.github.chess.engine.model.board.Position

/** Test suit for the rule that finds the move makes the [[io.github.chess.model.pieces.Pawn]] advance by one step. */
class SingleStepRuleSpec extends AbstractPawnSpec:

  "Single Step Rule" should "always have the next position in north direction as possible move for any white pawn" in {
    pawnPositions.foreach(pos => {
      addPiece(pos, whitePawn)
      val nextPosition = pos.rankUp()

      SingleStepRule().findMoves(pos, status).map(_.to) should contain theSameElementsAs Set(
        nextPosition
      )
    })
  }

  it should "always have the next position in south direction as possible move for any black pawn" in {
    pawnPositions.foreach(pos => {
      addPiece(pos, blackPawn)
      val nextPosition = pos.rankDown()

      SingleStepRule().findMoves(pos, status).map(_.to) should contain theSameElementsAs Set(
        nextPosition
      )
    })
  }

  it should "give an empty set if called on a pawn positioned on the adversary border" in {
    val whitePawnPosition: Position = (4, 7)
    addPiece(whitePawnPosition, whitePawn)
    SingleStepRule().findMoves(whitePawnPosition, status) should be(empty)

    val blackPawnPosition: Position = (4, 0)
    addPiece(blackPawnPosition, blackPawn)
    SingleStepRule().findMoves(blackPawnPosition, status) should be(empty)
  }
