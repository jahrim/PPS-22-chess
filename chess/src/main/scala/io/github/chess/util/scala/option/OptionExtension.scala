/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.scala.option

/** Extension for the [[Option]] class. */
object OptionExtension:
  /**
   * Implicitly transforms an object to an optional containing that object.
   * @tparam T the type of object
   * @return an optional containing the specified object
   */
  given anyToOptionOfAny[T]: Conversion[T, Option[T]] = Option(_)

  extension [T](self: Option[T])
    /**
     * @param t the specified throwable
     * @return the content of this optional or throws the specified throwable if this optional is empty
     */
    def getOrThrow(t: Throwable): T = self.getOrElse { throw t }
