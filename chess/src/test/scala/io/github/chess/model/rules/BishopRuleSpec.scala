/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules

import io.github.chess.AbstractSpec
import io.github.chess.model.Position

/** Test suite for [[Bishop]]. */
class BishopRuleSpec extends AbstractSpec:

  private val initialPosition: Position = (1, 1)
  private val northWestPositions: Set[Position] = Set((0, 2))
  private val northEastPositions: Set[Position] =
    Set((2, 2), (3, 3), (4, 4), (5, 5), (6, 6), (7, 7))
  private val southWestPositions: Set[Position] = Set((0, 0))
  private val southEastPositions: Set[Position] = Set((2, 0))
  private val diagonalRule = DiagonalRule()
  private val moves = diagonalRule.findMoves(initialPosition).map(_.to)

  "The diagonal rule" should "let move the bishop in all the north west positions" in {
    moves should contain allElementsOf northWestPositions
  }

  it should "let move the bishop in all the north east positions" in {
    moves should contain allElementsOf northEastPositions
  }

  it should "let move the bishop in all the south west positions" in {
    moves should contain allElementsOf southWestPositions
  }

  it should "let move the bishop in all the south east positions" in {
    moves should contain allElementsOf southEastPositions
  }
