/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.scala.option

import io.github.chess.AbstractSpec
import OptionExtension.*
import OptionExtension.given

/** Test suit for the [[OptionExtension]]. */
class OptionExtensionSpec extends AbstractSpec:
  @SuppressWarnings(Array("org.wartremover.warts.Null"))
  val nullValue: Null = null

  "The scala option extension" should "allow to get the content of an option or throw a specified exception" in {
    Option("someValue").getOrThrow(Exception()) shouldBe "someValue"
    an[Exception] shouldBe thrownBy { Option.empty.getOrThrow(Exception()) }
  }

  it should "allow to convert whatever value to an option containing that value" in {
    anyToOptionOfAny("someValue") shouldBe Some("someValue")
    anyToOptionOfAny(nullValue) shouldBe None
  }
