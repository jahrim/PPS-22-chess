/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.configuration

import io.github.chess.engine.model.game.Team

/**
 * Represents the configuration of the game containing all needed settings.
 * @param timeConstraint [[TimeConstraint]] representing the time limit mode
 * @param gameMode [[GameMode]] chosen by the user
 * @param whitePlayer [[Player]] representing the white pieces
 * @param blackPlayer [[Player]] representing the black pieces
 */
case class GameConfiguration(
    timeConstraint: TimeConstraint,
    gameMode: GameMode,
    whitePlayer: Player,
    blackPlayer: Player
):
  /**
   * @param team the specified team
   * @return the player of the specified team
   */
  def player(team: Team): Player = if team == Team.WHITE then whitePlayer else blackPlayer

object GameConfiguration:

  /** Creates a default game configuration, with no time constraint, pvp game mode and no name players. */
  def default: GameConfiguration = GameConfiguration(
    TimeConstraint.NoLimit,
    GameMode.PVP,
    Player.noNameWhitePlayer,
    Player.noNameBlackPlayer
  )
