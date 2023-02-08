/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

/** Test suit for the Team enumeration. */
class TeamSpec extends AnyFlatSpec with Matchers:

  private val WhiteTeam = Team.WHITE
  private val BlackTeam = Team.BLACK

  "The opposite of the White Team" should "be the Black Team" in {
    WhiteTeam.oppositeTeam shouldEqual BlackTeam
  }

  "The opposite of the Black Team" should "be the White Team" in {
    BlackTeam.oppositeTeam shouldEqual WhiteTeam
  }
