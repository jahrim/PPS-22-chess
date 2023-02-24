/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.general

import io.github.chess.model.Position

/** Combinator object providing combination generations. */
object Combinator:

  /**
   * Generate a set of positions, starting from a specific [[Position]] and combining it with the values as [[io.github.chess.model.File]] and [[io.github.chess.model.Rank]]
   * @param values values to combine
   * @param condition condition used to filter the combinations
   * @param position starting [[Position]]
   * @return
   */
  def generatePositions(
      values: Set[Int],
      condition: (Int, Int) => Boolean,
      position: Position
  ): Set[(Int, Int)] =
    for (
      x <- values;
      y <- values
      if condition(x, y)
    )
      yield (position.file.ordinal + x, position.rank.ordinal + y)
