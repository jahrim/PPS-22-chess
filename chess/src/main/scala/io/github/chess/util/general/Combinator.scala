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
   * @param position starting position as a couple of coordinates
   * @param condition condition used to filter the combinations, default to true
   * @return a [[Set]] of coordinates (Int, Int)
   */
  def generateCoordinates(
      values: Set[Int],
      position: (Int, Int),
      condition: (Int, Int) => Boolean = (_, _) => true
  ): Set[(Int, Int)] =
    for {
      x <- values
      y <- values
      if condition(x, y)
    } yield (position._1 + x, position._2 + y)
