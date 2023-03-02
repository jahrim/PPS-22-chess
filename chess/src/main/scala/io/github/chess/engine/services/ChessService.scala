/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.services

import io.github.chess.engine.adapters.ChessLocalAdapter
import io.github.chess.engine.ports.ChessPort
import io.vertx.core.{AbstractVerticle, Promise}

/** Service to instantiates all the adapters. */
class ChessService(private val chessPort: ChessPort) extends AbstractVerticle:

  private var _chessAdapter: Option[ChessLocalAdapter] = None

  override def start(startPromise: Promise[Void]): Unit =
    _chessAdapter = Some(ChessLocalAdapter(chessPort))
    startPromise.complete()

  /**
   * Returns the [[ChessLocalAdapter]].
   * @return the [[ChessLocalAdapter]]
   */
  def localAdapter: Option[ChessLocalAdapter] = _chessAdapter
