/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.moves

import io.github.chess.AbstractSpec
import io.github.chess.model.moves.Move
import io.github.chess.model.{File, Position, Rank, moves}
import org.scalatest.flatspec.AnyFlatSpec

/** Test suite for [[Move]]. */
class MoveSpec extends AbstractSpec:

  private val fromPosition: Position = Position(File.A, Rank._1)
  private val toPosition: Position = Position(File.B, Rank._2)
  private val move: Move = moves.Move(fromPosition, toPosition)

  "A Move" should "be equal to the same object with same parameters" in {
    val sameMove = moves.Move(fromPosition, toPosition)
    move shouldEqual sameMove
  }

  it should "have the same from Position and to Position initialized with" in {
    move.from shouldEqual fromPosition
    move.to shouldEqual toPosition
  }
