/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.exception

/** Represents the situation in which a value (rank or file) goes outside the chess board. */
case class OutsideBoardException() extends RuntimeException("Value goes outside the chess board.")
