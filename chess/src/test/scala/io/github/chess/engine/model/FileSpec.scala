/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model

import io.github.chess.AbstractSpec
import io.github.chess.util.exception.OutsideBoardException

/** Test suite for [[File]]. */
class FileSpec extends AbstractSpec:

  private val a: File = File.A
  private val b: File = File.B
  private val c: File = File.C
  private val d: File = File.D
  private val e: File = File.E
  private val f: File = File.F
  private val g: File = File.G
  private val h: File = File.H

  "A file" should "follow the Universal Chess Interface protocol representation" in {
    "a" shouldEqual a.toString
    "b" shouldEqual b.toString
    "c" shouldEqual c.toString
    "d" shouldEqual d.toString
    "e" shouldEqual e.toString
    "f" shouldEqual f.toString
    "g" shouldEqual g.toString
    "h" shouldEqual h.toString
  }

  it should "correctly forwarded" in {
    b shouldEqual a.forward()
    c shouldEqual b.forward()
    d shouldEqual c.forward()
    e shouldEqual d.forward()
    f shouldEqual e.forward()
    g shouldEqual f.forward()
    h shouldEqual g.forward()
    an[OutsideBoardException] should be thrownBy h.forward()
  }

  it should "correctly backwarded" in {
    an[OutsideBoardException] should be thrownBy a.backward()
    a shouldEqual b.backward()
    b shouldEqual c.backward()
    c shouldEqual d.backward()
    d shouldEqual e.backward()
    e shouldEqual f.backward()
    f shouldEqual g.backward()
    g shouldEqual h.backward()
  }
