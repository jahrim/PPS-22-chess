/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess

import io.github.chess.controllers.ChessController
import io.github.chess.services.ChessService
import io.github.chess.viewcontroller.ScalaFXHelloWorld
import io.vertx.core.Vertx

/** The main application. */
@main def main() =
  val vertx = Vertx.vertx()
  val service = ChessService(ChessController(vertx))
  vertx.deployVerticle(service)
  ScalaFXHelloWorld.main(Array.empty)
