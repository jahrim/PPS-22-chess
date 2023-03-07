/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.events

import io.github.chess.engine.model.configuration.Player

/** An event triggered when the turn in the chess game has changed. */
trait TurnChangedEvent extends Event:

  /** @return the player who is currently playing in the chess game */
  def currentPlayer: Player

/** Companion object of [[TurnChangedEvent]]. */
object TurnChangedEvent:

  /**
   * @param currentPlayer the player who is currently playing in the chess game
   * @return a new turn changed event
   */
  def apply(currentPlayer: Player): TurnChangedEvent =
    BasicTurnChangedEvent(currentPlayer)

  /** Basic implementation of [[TurnChangedEvent]]. */
  private case class BasicTurnChangedEvent(override val currentPlayer: Player)
      extends TurnChangedEvent
