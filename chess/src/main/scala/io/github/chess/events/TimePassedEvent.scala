/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.events

import scala.concurrent.duration.{Duration, MINUTES}

/** Represents the event in which the time remaining has passed. */
trait TimePassedEvent extends Event:

  /** Returns the time remaining as a [[Duration]]. */
  def timeRemaining: Duration

/** Object helper for [[TimePassedEvent]]. */
object TimePassedEvent:

  /**
   * Creates a new [[TimePassedEvent]], using its time remaining.
   * @param timeRemaining time remaining as a [[Duration]]
   * @return a new fresh [[TimePassedEvent]]
   */
  def apply(timeRemaining: Duration): TimePassedEvent = TimePassedEventImpl(timeRemaining)

  /**
   * Address on which this event will be communicated.
   *
   * @return the string representing the address on which this event is published
   */
  def address(): String = TimePassedEvent.getClass.toString

  private case class TimePassedEventImpl(override val timeRemaining: Duration)
      extends TimePassedEvent