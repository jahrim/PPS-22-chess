/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.model.configuration.TimeConstraint
import io.github.chess.util.general.Timer

import java.util.concurrent.TimeUnit

/** Represents a timer(s) manager based on the [[TimeConstraint]] and designed to be used by [[ChessGame]]. */
trait TimerManager:

  /**
   * Starts the timer(s), according to the [[TimeConstraint]].
   * @param timeConstraint [[TimeConstraint]] to use for generate needed timer(s)
   * @param runnable runnable to run every interval of seconds
   * @param endedRunnable runnable to run when the current timer will end
   */
  def start(timeConstraint: TimeConstraint, runnable: => Unit, endedRunnable: => Unit): Unit

  /**
   * Stops the current timer.
   * @param currentTeam [[Team]] used to stop the current timer
   */
  def stop(currentTeam: Team): Unit

  /**
   * Restarts the current timer.
   * @param currentTeam [[Team]] used to restart the current timer
   */
  def restart(currentTeam: Team): Unit

  /**
   * Returns the current timer if present.
   * @param currentTeam [[Team]] used to retrieve the current timer
   * @return the current [[Timer]]
   */
  def currentTimer(currentTeam: Team): Option[Timer]

/** Factory for [[TimerManager]] instances. */
object TimerManager:

  /**
   * Creates a new [[TimerManager]].
   * @return a new fresh [[TimerManager]]
   */
  def apply(): TimerManager = TimerManagerImpl()

  private case class TimerManagerImpl() extends TimerManager:

    private var timeConstraint: TimeConstraint = TimeConstraint.NoLimit
    private var timerPerMove: Timer = Timer.fake
    private var timerPerWhitePlayer: Timer = Timer.fake
    private var timerPerBlackPlayer: Timer = Timer.fake

    override def start(
        timeConstraint: TimeConstraint,
        runnable: => Unit,
        endedRunnable: => Unit
    ): Unit =
      this.timeConstraint = timeConstraint
      timeConstraint match
        case TimeConstraint.NoLimit =>
        case TimeConstraint.MoveLimit =>
          this.timerPerMove = createTimer(runnable, endedRunnable)
          this.timerPerMove.restart()
        case TimeConstraint.PlayerLimit =>
          this.timerPerWhitePlayer = createTimer(runnable, endedRunnable)
          this.timerPerBlackPlayer = createTimer(runnable, endedRunnable)
          this.timerPerWhitePlayer.restart()

    override def stop(currentTeam: Team): Unit = this.timeConstraint match
      case TimeConstraint.NoLimit     =>
      case TimeConstraint.MoveLimit   => this.timerPerMove.stop()
      case TimeConstraint.PlayerLimit => this.currentPlayerTimer(currentTeam).stop()

    override def restart(currentTeam: Team): Unit = this.timeConstraint match
      case TimeConstraint.NoLimit   =>
      case TimeConstraint.MoveLimit => this.timerPerMove.restart()
      case TimeConstraint.PlayerLimit =>
        val currentTimer = this.currentPlayerTimer(currentTeam)
        if !currentTimer.stopped then currentTimer.stop()
        opposite(currentTimer).continue()

    override def currentTimer(currentTeam: Team): Option[Timer] = this.timeConstraint match
      case TimeConstraint.NoLimit     => None
      case TimeConstraint.MoveLimit   => Some(this.timerPerMove)
      case TimeConstraint.PlayerLimit => Some(this.currentPlayerTimer(currentTeam))

    private def createTimer(runnable: => Unit, endedRunnable: => Unit): Timer =
      lazy val timer: Timer = Timer(
        () =>
          runnable
          if timer.ended then
            timer.stop()
            endedRunnable
        ,
        this.timeConstraint.minutes,
        TimeUnit.MINUTES
      )
      timer

    private def currentPlayerTimer(currentTeam: Team): Timer = currentTeam match
      case Team.WHITE => this.timerPerWhitePlayer
      case Team.BLACK => this.timerPerBlackPlayer

    private def opposite(timer: Timer): Timer =
      if timer == this.timerPerWhitePlayer
      then this.timerPerBlackPlayer
      else this.timerPerWhitePlayer
