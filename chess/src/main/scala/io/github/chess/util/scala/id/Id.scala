/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.scala.id

import scala.util.Random

/** Utility for generating identifiers. */
object Id:
  /**
   * @param idBase the base used to convert the generated numeric identifier into a string:
   *               - 10: string identifiers contain only numeric characters
   *               - 36: string identifiers contain only alpha-numeric characters
   *               - 64: string identifiers contain alpha-numeric characters and symbols
   * @return a new randomly generated string identifier.
   * @note the uniqueness of the identifier is not guaranteed but highly probable.
   *       The identifier is chosen randomly between 2&#94;63 possible identifiers.
   */
  def apply(idBase: Int = 36): String =
    BigInt.long2bigInt(Random.nextLong(Long.MaxValue)).toString(idBase)
