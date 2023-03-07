/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.application

import io.github.chess.util.scala.option.OptionExtension.getOrThrow
import io.github.chess.engine.ports.ChessPort
import scalafx.stage.Stage

/** The context of this application. */
trait ChessApplicationContext:
  /** @return the main stage where this application is displayed */
  def primaryStage: Stage

  /** @return the proxy used to interact with the chess engine service required by this application */
  def chessEngineProxy: ChessPort

/** Companion object of [[ChessApplicationContext]]. */
object ChessApplicationContext:
  /**
   * @param primaryStage the main stage where this application is displayed
   * @param chessEngineProxy the proxy used to interact with the chess engine service required by this application
   * @return a new context for this application
   */
  def apply(primaryStage: Stage, chessEngineProxy: ChessPort): ChessApplicationContext =
    BasicChessApplicationContext(primaryStage, chessEngineProxy)

  /** @return a builder for [[ChessApplicationContext]]s */
  def builder: ChessApplicationContextBuilder = ChessApplicationContextBuilder()

  /** Basic implementation of a [[ChessApplicationContext]]. */
  private case class BasicChessApplicationContext(
      override val primaryStage: Stage,
      override val chessEngineProxy: ChessPort
  ) extends ChessApplicationContext

  /** Builder for [[ChessApplicationContext]]. */
  case class ChessApplicationContextBuilder private[ChessApplicationContext] ():
    private var primaryStage: Option[Stage] = Option.empty
    private var chessEngineProxy: Option[ChessPort] = Option.empty

    /**
     * Set the main stage where this application is displayed to the specified stage.
     * @param primaryStage the specified stage
     * @return this
     */
    def setPrimaryStage(primaryStage: Stage): this.type =
      this.primaryStage = Option(primaryStage)
      this

    /**
     * Set the proxy used to interact with the chess engine service required by this application to the specified proxy.
     * @param chessEngineProxy the specified proxy
     * @return this
     */
    def setChessEngineProxy(chessEngineProxy: ChessPort): this.type =
      this.chessEngineProxy = Option(chessEngineProxy)
      this

    /**
     * @return a new application context created using the configuration of this builder
     * @throws IllegalStateException if any of the required configuration is missing
     */
    def build: ChessApplicationContext =
      ChessApplicationContext(
        this.primaryStage.getOrThrow {
          IllegalStateException("Primary stage not set")
        },
        this.chessEngineProxy.getOrThrow {
          IllegalStateException("Chess engine proxy not set")
        }
      )
