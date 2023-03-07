/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.scala.id

import io.github.chess.AbstractSpec

/** Test suit for the [[Id]] utility. */
class IdSpec extends AbstractSpec:
  val numberOfIds: Long = 10000
  val errorThreshold: Double = 1e-3

  "The scala Id utility" should "provide unique identifiers with a certain degree of confidence" in {
    percentageOfDuplicatedIds should be < errorThreshold
  }

  /**
   * Generates a set of identifiers.
   * @return the percentage of duplicated identifiers within the generated set
   */
  private def percentageOfDuplicatedIds: Double =
    val numberOfDistinctIds = (0L until numberOfIds).map(_ => Id()).distinct.size
    (numberOfIds - numberOfDistinctIds) / numberOfIds.toDouble
