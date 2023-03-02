/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.events

import io.github.chess.engine.model.configuration.Player
import io.github.chess.engine.model.game.GameOverCause

/** An event triggered when the game is over. */
trait GameOverEvent extends Event:

  /** @return the cause of this game over event. */
  def cause: GameOverCause

  /**
   * @return an option containing the winner of the game, or
   *         an empty option if the game has no winner
   */
  def winner: Option[Player]

/** Companion object of [[GameOverEvent]]. */
object GameOverEvent:
  def apply(cause: GameOverCause, winner: Option[Player] = None): GameOverEvent =
    BasicGameOverEvent(cause, winner)

  /** Basic implementation of [[GameOverEvent]]. */
  private case class BasicGameOverEvent(
      override val cause: GameOverCause,
      override val winner: Option[Player]
  ) extends GameOverEvent
