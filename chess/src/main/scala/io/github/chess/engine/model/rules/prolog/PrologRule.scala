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

/**
 * Represents a Prolog rule that must be structured in the following way:
 *
 * name_of_the_rule(SF, SR, AF, AR) :- ...
 *
 * where:
 *  - name_of_the_rule must be equal to its filename and so to the overridden theory
 *  - SF is Starting File
 *  - SR is Starting Rank
 *  - AF is Arrival File
 *  - AR is Arrival Rank
 */
trait PrologRule(val theory: String):

  private val engine: PrologEngine = PrologEngine("/theories/" + theory + ".pl")

  /**
   * Generates new coordinates starting from a particular [[Position]].
   * @param position starting [[Position]]
   * @return a [[LazyList]] containing all the new coordinates
   */
  def findPositions(position: Position): LazyList[(Int, Int)] =
    val coords: (Int, Int) = position
    val xString = "X"
    val yString = "Y"
    val goal = s"$theory(${coords._1}, ${coords._2}, $xString, $yString)"
    engine
      .solveAll(goal, xString, yString)
      .map(term => {
        val x = term(xString).toString.toInt
        val y = term(yString).toString.toInt
        (x, y)
      })
