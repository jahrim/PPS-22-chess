/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.pawn

import io.github.chess.AbstractSpec
import io.github.chess.model.Position

/** Test suit for all Pawn movement rules. */
class PawnRulesSpec extends AbstractSpec:

  val pawnInitialPosition: Position = (0, 1)
  val pawnNextPosition: Position = (0, 2)
  val pawnDoubleStepPosition: Position = (0, 3)
  val doubleStepRule: DoubleMoveRule = DoubleMoveRule()

  "The Forward rule" should "let move the pawn only to the following rank, without changing its file" in {
    val oneStepRule = ForwardOneRule()
    val moves = oneStepRule.findMoves(pawnInitialPosition)
    moves should have size 1
    all(moves) should have(Symbol("to")(pawnNextPosition))
  }

  "Double move rule" should "let move the pawn only to the rank two steps ahead of the pawn's current position, " +
    "without changing its file" in {
    val moves = doubleStepRule.findMoves(pawnInitialPosition)
    moves should have size 1
    all(moves) should have(Symbol("to")(pawnDoubleStepPosition))
  }

  // TODO add test to check that the double step works only on the first move of the pawn and not on the next ones.
//  it should "give no moves if the pawn is not in its first position" in {
//    val moves = doubleStepRule.findMoves(pawnNextPosition)
//    moves should have size 0
//  }
