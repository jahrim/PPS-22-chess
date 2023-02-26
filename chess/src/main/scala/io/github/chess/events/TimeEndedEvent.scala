/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.events

import io.github.chess.model.configuration.Player

/** Represents the event in which the time has ended. */
trait TimeEndedEvent extends Event:

  /** Returns the loser [[Player]]. */
  def loserPlayer: Player

/** Object helper for [[TimeEndedEvent]]. */
object TimeEndedEvent:

  /**
   * Creates a new [[TimeEndedEvent]], .
   * @param loserPlayer loser [[Player]]
   * @return a new fresh [[TimeEndedEvent]]
   */
  def apply(loserPlayer: Player): TimeEndedEvent = TimeEndedEventImpl(loserPlayer)

  private case class TimeEndedEventImpl(override val loserPlayer: Player) extends TimeEndedEvent
