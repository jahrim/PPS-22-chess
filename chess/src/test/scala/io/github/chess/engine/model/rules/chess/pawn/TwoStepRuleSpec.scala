/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.pawn

import io.github.chess.engine.model.board.Position

/** Test suit for the rule that finds the move makes the [[io.github.chess.model.pieces.Pawn]] advance by two steps. */
class TwoStepRuleSpec extends AbstractPawnSpec:

  "Two Step Rule" should "always give the position that would make a white pawn advance by two steps " +
    "in north direction when it's in the initial position" in {
      whitePawnInitialPositions.foreach(initialPos => {
        addPiece(initialPos, whitePawn)
        val arrivingPosition = initialPos.rankUp().rankUp()

        TwoStepRule().findMoves(initialPos, status).map(_.to) should contain theSameElementsAs Set(
          arrivingPosition
        )
      })
    }

  it should "always give the position that would make a black pawn advance by two steps in south direction, " +
    "when it's in the initial position" in {
      blackPawnInitialPositions.foreach(initialPos => {
        addPiece(initialPos, blackPawn)
        val arrivingPosition = initialPos.rankDown().rankDown()

        TwoStepRule().findMoves(initialPos, status).map(_.to) should contain theSameElementsAs Set(
          arrivingPosition
        )
      })
    }

  it should "give an empty set if called on a pawn positioned on the adversary border" in {
    val whitePawnPosition: Position = (4, 7)
    addPiece(whitePawnPosition, whitePawn)
    TwoStepRule().findMoves(whitePawnPosition, status) should have size 0

    val blackPawnPosition: Position = (4, 0)
    addPiece(blackPawnPosition, blackPawn)
    TwoStepRule().findMoves(blackPawnPosition, status) should have size 0
  }

  it should "give an empty set if called on a pawn positioned on the rank before the adversary border" in {
    val whitePawnPosition: Position = (4, 6)
    addPiece(whitePawnPosition, whitePawn)
    TwoStepRule().findMoves(whitePawnPosition, status) should have size 0

    val blackPawnPosition: Position = (4, 1)
    addPiece(blackPawnPosition, blackPawn)
    TwoStepRule().findMoves(blackPawnPosition, status) should have size 0
  }
