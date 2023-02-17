/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess

import io.github.chess.adapters.Adapter
import io.github.chess.model.ChessGame
import io.github.chess.ports.ChessPort
import io.github.chess.services.ChessService
import io.github.chess.util.debug.Logger
import io.github.chess.viewcontroller.{ChessApplication, ChessLocalProxy}
import io.vertx.core.{Future, Promise, Vertx}

/** The main application. */
@main def main(): Unit =
  deployServiceLocally()
    .map {
      deployApplicationLocally(_)
    }
    .onFailure { error =>
      error.printStackTrace()
      System.exit(1)
    }

/**
 * Deploys the chess engine service locally.
 * @return a future that completes when the chess engine service has been successfully deployed
 */
def deployServiceLocally(): Future[ChessService] =
  Logger.info("Start", "Deploying chess engine service...")
  val vertx = Vertx.vertx()
  val service = ChessService(ChessGame(vertx))
  val serviceDeployed: Promise[ChessService] = Promise.promise()
  vertx
    .deployVerticle(service)
    .onSuccess { _ =>
      Logger.info("Start", "Chess engine service deployed.")
      serviceDeployed.complete(service)
    }
    .onFailure { error =>
      Logger.error("Start", "Chess engine service failed to deploy.")
      serviceDeployed.fail(error)
    }
  serviceDeployed.future()

/**
 * Deploys the chess game application locally.
 * @param service the chess engine service required by the chess game application
 */
def deployApplicationLocally(service: ChessService): Unit =
  service.localAdapter.foreach { localAdapter =>
    Logger.info("Start", "Starting application...")
    ChessApplication.launch(ChessLocalProxy(localAdapter.port))(Array.empty)
    Logger.info("Start", "Application started.")
  }
