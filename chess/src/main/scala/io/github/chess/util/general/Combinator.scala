/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.general

import io.github.chess.engine.model.board.{File, Position, Rank}

/** Combinator object providing combination generations. */
object Combinator:

  /**
   * Generate a set of positions, starting from a specific [[Position]] and combining it with the values as [[File]] and [[Rank]]
   *
   * @param values values to combine
   * @param position starting [[Position]]
   * @param condition condition used to filter the combinations, default to true
   * @return
   */
  def generatePositions(
      values: Set[Int],
      position: Position,
      condition: (Int, Int) => Boolean = (_, _) => true
  ): Set[(Int, Int)] =
    for (
      x <- values;
      y <- values
      if condition(x, y)
    )
      yield (position.file.ordinal + x, position.rank.ordinal + y)
