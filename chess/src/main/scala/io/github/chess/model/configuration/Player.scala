/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.configuration

import io.github.chess.model.Team

/**
 * Represents a player of the chess game.
 * @param name his name
 * @param team his [[Team]]
 */
case class Player(name: String, team: Team)

object Player:

  /** Creates a no name white player. */
  def noNameWhitePlayer: Player = Player("", Team.WHITE)

  /** Creates a no name black player. */
  def noNameBlackPlayer: Player = Player("", Team.BLACK)
