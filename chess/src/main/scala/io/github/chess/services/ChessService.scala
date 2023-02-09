/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.services

import io.github.chess.adapters.ChessAdapter
import io.github.chess.controllers.ChessController
import io.vertx.core.{AbstractVerticle, Promise}

/** Service to instantiates all the adapters. */
class ChessService(private val chessController: ChessController) extends AbstractVerticle:

  private var _chessAdapter: Option[ChessAdapter] = None

  override def start(startPromise: Promise[Void]): Unit =
    _chessAdapter = Some(ChessAdapter(chessController, vertx))

  /**
   * Returns the [[ChessAdapter]].
   * @return the [[ChessAdapter]]
   */
  def chessAdapter: ChessAdapter = _chessAdapter match
    case Some(value) => value
    case None        => throw IllegalStateException()
