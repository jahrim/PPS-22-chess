/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.ports

import io.github.chess.engine.events.Event
import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.configuration.{GameConfiguration, Player}
import io.github.chess.engine.model.game.ChessGameState
import io.github.chess.engine.model.moves.Move
import io.github.chess.engine.model.pieces.{Piece, PromotionPiece}
import io.vertx.core.eventbus.Message
import io.vertx.core.Handler

import scala.concurrent.Future
import scala.reflect.ClassTag

/** Represents the contract of a chess engine service. */
trait ChessPort:
  /** @return a future containing the state of the game of chess */
  def getState: Future[ChessGameState]

  /**
   * Starts the game, using from its [[GameConfiguration]].
   * @param gameConfiguration its [[GameConfiguration]]
   * @return a future completed when the game is started
   */
  def startGame(gameConfiguration: GameConfiguration): Future[Unit]

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

  /**
   * Promotes a pawn to another piece.
   * @param pawnPosition position of the pawn to promote
   * @param promotingPiece piece to promote the pawn
   * @return a future that completes when the promotion has been applied
   */
  // TODO refactor to return piece, so not repainting all board but single cell
  def promote[P <: Piece](pawnPosition: Position, promotingPiece: PromotionPiece[P]): Future[Unit]

  /**
   * Make the specified player surrender.
   * @param p the specified player
   * @return a future that completes when the player has effectively surrendered.
   */
  def surrender(p: Player): Future[Unit]

  /**
   * Subscribes an handler to a particular event.
   * @param handler a consumer for the specified event
   * @tparam T type parameter of the event extending superclass [[Event]]
   * @return a future that completes when the subscription has been registered
   */
  def subscribe[T <: Event: ClassTag](handler: T => Unit): Future[Unit]

  // todo allow unsubscribe to events
  // def subscribe[T <: Event: ClassTag](handler: T => Unit): Future[Subscription]
  // def unsubscribe(subscription: Subscription): Future[Unit]
