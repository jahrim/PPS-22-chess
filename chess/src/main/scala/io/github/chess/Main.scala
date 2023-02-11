/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess

import io.github.chess.controllers.ChessController
import io.github.chess.services.ChessService
import io.github.chess.util.debug.Logger
import io.github.chess.viewcontroller.ChessGameInterface
import io.vertx.core.Vertx

/** The main application. */
@main def main(): Unit =
  Logger.info("Start", "Application started...")
  val vertx = Vertx.vertx()
  val service = ChessService(ChessController(vertx))
  vertx.deployVerticle(service)
  ChessGameInterface.launch(
    /* TODO: insert a proxy/adapter for the chess engine service here */ Array.empty
  )
