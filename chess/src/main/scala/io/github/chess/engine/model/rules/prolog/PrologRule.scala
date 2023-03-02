/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.prolog

import alice.tuprolog.Theory
import io.github.chess.engine.model.board.Position
import Position.given
import io.github.chess.util.general.PrologEngine.given
import io.github.chess.util.general.PrologEngine

trait PrologRule(val theory: String):

  private val engine: PrologEngine = PrologEngine("/theories/" + theory + ".pl")

  def findPositions(position: Position): LazyList[(Int, Int)] =
    val coords: (Int, Int) = position
    val xString = "X"
    val yString = "Y"
    val goal = s"${theory}(${coords._1}, ${coords._2}, $xString, $yString)"
    engine
      .solveAll(goal, xString, yString)
      .map(term => {
        val x = term(xString).toString.toInt
        val y = term(yString).toString.toInt
        (x, y)
      })
