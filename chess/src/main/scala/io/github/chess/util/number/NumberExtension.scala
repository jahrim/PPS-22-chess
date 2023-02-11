/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.number

/** Extension for number classes. */
object NumberExtension:
  extension (self: Int)
    /** @return true if this integer is odd, false otherwise. */
    def isOdd: Boolean = self % 2 == 1

    /** @return true if this integer is even, false otherwise. */
    def isEven: Boolean = !self.isOdd

    /**
     * @param other the specified integer
     * @return true if this integer and the specified integer share the same parity, false otherwise.
     */
    def sameParityAs(other: Int): Boolean = !(self.isOdd ^ other.isOdd)
