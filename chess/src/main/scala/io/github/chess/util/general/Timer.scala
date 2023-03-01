/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.general

import java.util.concurrent.{Executors, ScheduledExecutorService, TimeUnit}
import scala.concurrent.duration.Duration
import scala.concurrent.duration.DurationInt

/** Represents a countdown timer that decrement the time remaining and executes every N seconds. */
trait Timer:

  /** Starts the timer. */
  def start(): Unit

  /** Stops the timer definitely. */
  def stop(): Unit

  /** Reset the timer with the initial time. */
  def reset(): Unit

  /** Continues running the timer. */
  def continue(): Unit

  /** Restarts the timer, as stopping, resetting and continue running it. */
  def restart(): Unit =
    this.stop()
    this.reset()
    this.continue()

  /**
   * Returns true if the timer has ended, false otherwise.
   * @return true if the timer has ended, false otherwise
   */
  def ended: Boolean

  /**
   * Returns true if the timer is stopped, false otherwise.
   * @return true if the timer is stopped, false otherwise
   */
  def stopped: Boolean

  /**
   * Returns the time remaining as a [[Duration]].
   * @return the time remaining as a [[Duration]]
   */
  def timeRemaining: Duration

  /**
   * Sets the time remaining.
   * @param value time remaining value
   * @param timeUnit [[TimeUnit]] of the value
   */
  def setTime(value: Int, timeUnit: TimeUnit): Unit

/** Factory for [[Timer]] instances. */
object Timer:

  /**
   * Creates a new [[Timer]].
   * @param runnable runnable to run every execution
   * @param startingTime starting time value
   * @param timeUnit [[TimeUnit]] of the value
   * @param decrement seconds to decrement the starting time
   * @return a new fresh [[Timer]]
   */
  def apply(
      runnable: () => Unit,
      startingTime: Int,
      timeUnit: TimeUnit,
      decrement: Int = 1
  ): Timer =
    TimerImpl(runnable, Duration(startingTime, timeUnit), decrement)

  private case class TimerImpl(
      private val runnable: () => Unit,
      private var startingTime: Duration,
      private val decrement: Int
  ) extends Timer:

    private var executor: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()
    private var _timeRemaining = this.startingTime
    private final val Decrement = decrement.seconds

    override def start(): Unit =
      if this.stopped then
        throw IllegalStateException(
          "Can not start after being stopped. You can call @continue or @restart."
        )
      else
        this.executor.scheduleAtFixedRate(
          () =>
            runnable()
            countdown()
          ,
          0,
          decrement,
          TimeUnit.SECONDS
        )

    override def stop(): Unit = this.executor.shutdownNow()

    override def reset(): Unit = if this.stopped then this._timeRemaining = this.startingTime

    override def continue(): Unit =
      this.executor = Executors.newSingleThreadScheduledExecutor()
      this.start()

    override def ended: Boolean = this._timeRemaining == Duration.Zero

    override def stopped: Boolean = this.executor.isShutdown

    override def timeRemaining: Duration = this._timeRemaining

    override def setTime(value: Int, timeUnit: TimeUnit): Unit =
      this.startingTime = Duration(value, timeUnit)
      this._timeRemaining = this.startingTime

    private def countdown(): Unit = this._timeRemaining = this._timeRemaining - Decrement
