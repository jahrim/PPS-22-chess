/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.scala.exception

/** Utility for throwing exceptions. */
object Require:
  /**
   * Throws the specified [[Throwable]] if the specified condition is not met.
   *
   * @param condition the specified condition
   * @param throwable the specified throwable
   * @throws Throwable if the specified condition is not met
   */
  def condition(condition: Boolean, throwable: Throwable): Unit =
    if (!condition) throw throwable

  /** As [[Require.condition]] but throws an [[IllegalArgumentException]] with the specified message. */
  def input(argumentCondition: Boolean, errorMessage: String): Unit =
    Require.condition(argumentCondition, IllegalArgumentException(errorMessage))

  /** As [[Require.condition]] but throws an [[IllegalStateException]] with the specified message. */
  def state(stateCondition: Boolean, errorMessage: String): Unit =
    Require.condition(stateCondition, IllegalStateException(errorMessage))
