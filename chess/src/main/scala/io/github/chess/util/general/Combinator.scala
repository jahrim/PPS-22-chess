/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.general

/** Combinator object providing combination generations. */
object Combinator:

  /**
   * Generate a set of coordinates, starting from a specific couple of coordinates and combining it with the values as X and Y.
   * @param values values to combine
   * @param initialCoords initial coordinates a couple of (Int, Int)
   * @param condition condition used to filter the combinations, default to true
   * @return a [[Set]] of coordinates (Int, Int)
   */
  def generateCoordinates(
      values: Set[Int],
      initialCoords: (Int, Int),
      condition: (Int, Int) => Boolean = (_, _) => true
  ): Set[(Int, Int)] =
    for {
      x <- values
      y <- values
      if condition(x, y)
    } yield (initialCoords._1 + x, initialCoords._2 + y)
