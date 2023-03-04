/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.board

import io.github.chess.AbstractSpec
import io.github.chess.util.exception.OutsideBoardException

/** Test suite for [[Rank]]. */
class RankSpec extends AbstractSpec:

  private val _1: Rank = Rank._1
  private val _2: Rank = Rank._2
  private val _3: Rank = Rank._3
  private val _4: Rank = Rank._4
  private val _5: Rank = Rank._5
  private val _6: Rank = Rank._6
  private val _7: Rank = Rank._7
  private val _8: Rank = Rank._8

  "A rank" should "follow the Universal Chess Interface protocol representation" in {
    "1" shouldEqual _1.toString
    "2" shouldEqual _2.toString
    "3" shouldEqual _3.toString
    "4" shouldEqual _4.toString
    "5" shouldEqual _5.toString
    "6" shouldEqual _6.toString
    "7" shouldEqual _7.toString
    "8" shouldEqual _8.toString
  }

  it should "correctly ranked up" in {
    _2 shouldEqual _1.up()
    _3 shouldEqual _2.up()
    _4 shouldEqual _3.up()
    _5 shouldEqual _4.up()
    _6 shouldEqual _5.up()
    _7 shouldEqual _6.up()
    _8 shouldEqual _7.up()
    an[OutsideBoardException] should be thrownBy _8.up()
  }

  it should "correctly ranked down" in {
    an[OutsideBoardException] should be thrownBy _1.down()
    _1 shouldEqual _2.down()
    _2 shouldEqual _3.down()
    _3 shouldEqual _4.down()
    _4 shouldEqual _5.down()
    _5 shouldEqual _6.down()
    _6 shouldEqual _7.down()
    _7 shouldEqual _8.down()
  }
