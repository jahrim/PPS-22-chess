/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.scala.exception

import io.github.chess.AbstractSpec

/** Test suit for the [[Require]] utility. */
class RequireSpec extends AbstractSpec:
  private val satisfiedRequirement: Boolean = true
  private val unsatisfiedRequirement: Boolean = false
  private val errorMessage: String = "Error message"

  "The scala Require utility" should "provide a way to require a certain condition" in {
    noException shouldBe thrownBy {
      Require.condition(satisfiedRequirement, Exception())
    }
    an[Exception] shouldBe thrownBy {
      Require.condition(unsatisfiedRequirement, Exception())
    }
  }

  it should "provide a way to require a certain state" in {
    noException shouldBe thrownBy {
      Require.state(satisfiedRequirement, errorMessage)
    }
    an[IllegalStateException] shouldBe thrownBy {
      Require.state(unsatisfiedRequirement, errorMessage)
    }
  }

  it should "provide a way to require an input to have a certain property" in {
    noException shouldBe thrownBy {
      Require.input(satisfiedRequirement, errorMessage)
    }
    an[IllegalArgumentException] shouldBe thrownBy {
      Require.input(unsatisfiedRequirement, errorMessage)
    }
  }
