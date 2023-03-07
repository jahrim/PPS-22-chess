/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.scala.number

import io.github.chess.AbstractSpec
import NumberExtension.*

/** Test suit for the [[NumberExtension]]. */
class NumberExtensionSpec extends AbstractSpec:

  "The scala number extension" should "be able to recognize odd numbers" in {
    2.isOdd shouldBe false
    3.isOdd shouldBe true
  }

  it should "be able to recognize even numbers" in {
    2.isEven shouldBe true
    3.isEven shouldBe false
  }

  it should "be able to recognize numbers with the same parity" in {
    2 sameParityAs 4 shouldBe true
    3 sameParityAs 5 shouldBe true
    2 sameParityAs 3 shouldBe false
  }
