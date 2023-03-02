/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.adapters

import io.github.chess.engine.ports.ChessPort
import io.vertx.core.Vertx

/**
 * Represents an adapter that allows to interact with a specific port
 * of a service, exposing the port to local interactions.
 */
class ChessLocalAdapter(override val port: ChessPort) extends Adapter[ChessPort]
