/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.configuration

import io.github.chess.engine.model.game.Team

/**
 * Represents a Black [[Player]].
 * @param name name of the player
 */
case class BlackPlayer(override val name: String) extends Player:

  override val team: Team = Team.BLACK

object BlackPlayer:

  /** Creates a no name black player. */
  def default: BlackPlayer = BlackPlayer("blackPlayer")
