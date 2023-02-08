/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.AbstractSpec
import org.scalatest.flatspec.AnyFlatSpec

/** Test suite for [[Pawn]]. */
class PawnSpec extends AbstractSpec:

  private val pawn = Pawn()

  "A Pawn" should "always give a set of positions not empty, regardless of the chess board" in {
    val position = Position(File.A, Rank._1)
    pawn.findMoves(position) shouldNot be(empty)
  }
