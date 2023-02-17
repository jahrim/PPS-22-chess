/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller

import io.github.chess.ports.ChessPort

/**
 * A proxy for interacting with a chess engine service through local interactions.
 * @param port the local port of the chess engine service
 */
case class ChessLocalProxy(private val port: ChessPort) extends ChessPort:
  export port.*
