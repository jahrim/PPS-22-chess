/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.ports

import io.github.chess.events.Event
import io.github.chess.model.{ChessGameStatus, Move, Position}
import io.vertx.core.eventbus.Message
import io.vertx.core.{Future, Handler}

/** Represents the contract of a chess engine service. */
trait ChessPort:

  // TODO inserire gameConfiguration parameter e pensare a cosa ritornare
  // (discutere se la view crea subito il modello e cambia la gameConfiguration con i vari input dell'utente)
  // def createGame(gc: GameConfiguration): ChessGameStatus

  // TODO inserire player parameter
  // def surrender(p: Player): Unit = ???

  /** @return a future containing the state of the game of chess */
  def getState: Future[ChessGameStatus]

  /**
   * @param position the specified position
   * @return a future containing all the possible moves that are available
   *         from the specified position
   */
  def findMoves(position: Position): Future[Set[Move]]

  /**
   * Applies the specified move to this game of chess.
   * @param move the specified move
   * @return a future that completes when the move has been applied
   */
  def applyMove(move: Move): Future[Unit]

  // TODO: reason about changing it to subscribe[T <: Event](handler: (Event) => Unit)
  /**
   * Subscribes an handler to a particular event.
   * @param address address to subscribe on
   * @param handler [[Handler]] to inform when the event is published
   * @tparam T type parameter of the event extending superclass [[Event]]
   * @return a future that completes when the subscription has been registered
   */
  def subscribe[T <: Event](address: String, handler: Handler[Message[T]]): Future[Unit]
