/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.events

import io.github.chess.engine.model.configuration.Player

/**
 * Represents the event ih which a [[io.github.chess.engine.model.game.ChessGameAnalyzer.ChessGameSituation.Check]]
 *  has happened.
 */
trait CheckNotificationEvent extends Event:

  /**
   * Returns the [[Player]] under check.
   * @return the [[Player]] under check
   */
  def playerUnderCheck: Player

/** Object helper for [[CheckNotificationEvent]]. */
object CheckNotificationEvent:

  /**
   * Creates a new [[CheckNotificationEvent]].
   * @param playerUnderCheck [[Player]] under check
   * @return a new fresh [[CheckNotificationEvent]]
   */
  def apply(playerUnderCheck: Player): CheckNotificationEvent = CheckNotificationEventImpl(
    playerUnderCheck
  )

  private case class CheckNotificationEventImpl(override val playerUnderCheck: Player)
      extends CheckNotificationEvent
