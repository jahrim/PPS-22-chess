/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.scala.givens

import io.github.chess.AbstractSpec
import GivenExtension.*

/** Test suit for the [[GivenExtension]]. */
class GivenExtensionSpec extends AbstractSpec:

  "The scala given extension" should "provide an easier way to define given instances" in {
    within("someValue") { givenInstance[String] shouldBe "someValue" }
    within("someOtherValue") { givenInstance[String] shouldBe "someOtherValue" }
    within(1234) { givenInstance[Int] shouldBe 1234 }
  }

  /**
   * @tparam T the specified type
   * @return a given instance of the specified type
   */
  private def givenInstance[T](using givenInstance: T): T = givenInstance
