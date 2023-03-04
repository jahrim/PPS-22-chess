/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.events

import io.github.chess.util.general.timer.Time

/** Represents the event in which the time remaining has passed. */
trait TimePassedEvent extends Event:

  /** Returns the time remaining as a [[Time]]. */
  def timeRemaining: Time

/** Object helper for [[TimePassedEvent]]. */
object TimePassedEvent:

  /**
   * Creates a new [[TimePassedEvent]], using its time remaining.
   * @param timeRemaining time remaining as a [[Time]]
   * @return a new fresh [[TimePassedEvent]]
   */
  def apply(timeRemaining: Time): TimePassedEvent = TimePassedEventImpl(timeRemaining)

  private case class TimePassedEventImpl(override val timeRemaining: Time) extends TimePassedEvent
