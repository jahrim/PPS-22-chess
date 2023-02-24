/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.events

import io.github.chess.model.{Position, PromotionPiece}

/** Represents the event in which a pawn is being promoted. */
trait PromotingPawnEvent extends Event:

  /**
   * Returns the position of the pawn being promoted.
   * @return the position of the pawn being promoted
   */
  def pawnPosition: Position

  /**
   * Returns the array containing all the pieces in which the pawn can be promoted.
   * @return the array containing all the pieces in which the pawn can be promoted
   */
  def promotionPieces: Array[PromotionPiece[_]]

/** Object helper for [[PromotingPawnEvent]]. */
object PromotingPawnEvent:

  /**
   * Creates a new [[PromotingPawnEvent]].
   * @param pawnPosition position of the pawn to be promoted
   * @param promotionPieces array containing all the pieces in which the pawn can be promoted
   * @return a new fresh [[PromotingPawnEvent]]
   */
  def apply(pawnPosition: Position, promotionPieces: Array[PromotionPiece[_]]): PromotingPawnEvent =
    PromotingPawnEventImpl(pawnPosition, promotionPieces)

  /**
   * Address on which this event will be communicated.
   *
   * @return the string representing the address on which this event is published
   */
  def address(): String = PromotingPawnEvent.getClass.toString

  private case class PromotingPawnEventImpl(
      override val pawnPosition: Position,
      override val promotionPieces: Array[PromotionPiece[_]]
  ) extends PromotingPawnEvent
