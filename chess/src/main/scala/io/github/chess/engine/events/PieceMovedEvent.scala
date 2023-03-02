/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.events

import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.configuration.Player
import io.github.chess.engine.model.moves.Move
import io.github.chess.engine.model.pieces.Piece

/** Represents the event in which a piece was moved on the board. */
trait PieceMovedEvent extends Event:

  /**
   * The player that should be playing next.
   * @return the player that should be playing next
   */
  def currentPlayer: Player

  /**
   * The disposition of the board after the move was performed.
   * @return Current disposition of the board
   */
  def boardDisposition: Map[Position, Piece]

  /**
   * Last move that was performed on the board.
   * @return last move that was performed on the board
   */
  def lastMove: Move

/** Object helper for PieceMovedEvent. */
object PieceMovedEvent:

  /**
   * Creates an instance of the Piece Moved Event.
   * @param currentPlayer the player that should be playing next
   * @param boardDisposition current disposition of the board
   * @param lastMove last move that was performed on the board
   * @return The event containing all the changes applied due to the move
   */
  def apply(
      currentPlayer: Player,
      boardDisposition: Map[Position, Piece],
      lastMove: Move
  ): PieceMovedEvent =
    PieceMovedEventImpl(currentPlayer, boardDisposition, lastMove)

  private case class PieceMovedEventImpl(
      override val currentPlayer: Player,
      override val boardDisposition: Map[Position, Piece],
      override val lastMove: Move
  ) extends PieceMovedEvent
