/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.controllers

import io.github.chess.events.Event
import io.github.chess.model.{ChessBoard, Move, Position}
import io.vertx.core.eventbus.Message
import io.vertx.core.{Handler, Vertx}

/** Represents the controller of the game. */
class ChessController(private val vertx: Vertx):

  private val chessBoard = ChessBoard(vertx)

  // TODO inserire gameConfiguration parameter e pensare a cosa ritornare
  // (discutere se la view crea subito il modello e cambia la gameConfiguration con i vari input dell'utente)
//  def createGame(): Map[Position, Piece] = chessBoard.pieces

  /**
   * Returns all moves available from a particular position.
   * @param position starting [[Position]]
   * @return a [[Set]] of [[Move]]
   */
  def findMoves(position: Position): Set[Move] = chessBoard.findMoves(position)

  /**
   * Apply a move to the game.
   * @param move [[Move]] to apply
   */
  def move(move: Move): Unit = chessBoard.move(move)

//  def findMoves(position: Position): Set[Position] = ???
//
//  def move(from: Position, to: Position): Unit = ???

  // TODO inserire player parameter
//  def surrender(): Unit = ???

  /**
   * Subscribes an handler to a particular event.
   * @param address address to subscribe on
   * @param handler [[Handler]] to inform when the event is published
   * @tparam T type parameter of the event extending superclass [[Event]]
   */
  def subscribe[T <: Event](address: String, handler: Handler[Message[T]]): Unit =
    vertx.eventBus().consumer(address, handler)
