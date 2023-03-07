/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.scala.debug

import scala.util.{Failure, Success, Try}

/** Utility for debugging exceptions. */
object Exceptions:
  /**
   * Observes the specified block of code. When an exception is thrown, prints the exception and exits the code.
   * @param code the specified block of code
   * @tparam T the return type of the code
   * @return a succeeded try if the code does not throw any exceptions, a failed try otherwise.
   * @example {{{ Exception.observe { myCode() } getOrElse { System.exit(1) } }}}
   */
  def observe[T](code: => T): Try[T] =
    try { Success(code) }
    catch { case e: Exception => e.printStackTrace(); Failure(e) }
