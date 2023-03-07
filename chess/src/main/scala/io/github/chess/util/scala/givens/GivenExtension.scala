/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.scala.givens

/** Utility for handling given instances. */
object GivenExtension:
  /**
   * Creates a context with the specified given instance.
   * @param givenInstance the specified given instance
   * @param context the created context where the given instance is made available
   * @tparam A the type of the given instance
   * @tparam B the type of the result of the configuration
   * @return the result of the configuration
   */
  def within[A, B](givenInstance: A)(context: A ?=> B): B =
    given A = givenInstance
    context
