/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.events

import io.github.chess.events.Event
import io.github.chess.model.moves.Move
import io.github.chess.model.pieces.Piece
import io.github.chess.model.{Position, Team}

/** Represents the event in which a piece was moved on the board. */
trait PieceMovedEvent extends Event:

  /**
   * The team that should be playing next.
   * @return the team that should be playing next
   */
  def currentTurn: Team

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
   * @param currentTurn the team that should be playing next
   * @param boardDisposition current disposition of the board
   * @param lastMove last move that was performed on the board
   * @return The event containing all the changes applied due to the move
   */
  def apply(
      currentTurn: Team,
      boardDisposition: Map[Position, Piece],
      lastMove: Move
  ): PieceMovedEvent =
    PieceMovedEventImpl(currentTurn, boardDisposition, lastMove)

  /**
   * Address on which this event will be communicated.
   * @return the string representing the address on which this event is published
   */
  def address(): String = PieceMovedEvent.getClass.toString

  private case class PieceMovedEventImpl(
      override val currentTurn: Team,
      override val boardDisposition: Map[Position, Piece],
      override val lastMove: Move
  ) extends PieceMovedEvent
