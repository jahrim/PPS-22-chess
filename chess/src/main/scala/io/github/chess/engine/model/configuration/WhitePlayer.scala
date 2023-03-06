/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.configuration

import io.github.chess.engine.model.game.Team

/**
 * Represents a White [[Player]].
 * @param name name of the player
 */
case class WhitePlayer(override val name: String) extends Player:

  override val team: Team = Team.WHITE

object WhitePlayer:

  /** Creates a no name white player. */
  def default: WhitePlayer = WhitePlayer("whitePlayer")
