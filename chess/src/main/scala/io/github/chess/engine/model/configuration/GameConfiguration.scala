/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.configuration

import io.github.chess.engine.model.game.Team
import io.github.chess.engine.model.game.Team.{BLACK, WHITE}

/**
 * Represents the configuration of the game containing all needed settings.
 * @param timeConstraint [[TimeConstraint]] representing the time limit mode
 * @param gameMode [[GameMode]] chosen by the user
 * @param whitePlayer [[WhitePlayer]] representing the white pieces
 * @param blackPlayer [[BlackPlayer]] representing the black pieces
 */
case class GameConfiguration(
    timeConstraint: TimeConstraint,
    gameMode: GameMode,
    whitePlayer: WhitePlayer,
    blackPlayer: BlackPlayer
):
  /**
   * @param team the specified team
   * @return the player of the specified team
   */
  def player(team: Team): Player = team match
    case WHITE => whitePlayer
    case BLACK => blackPlayer

object GameConfiguration:

  /** Creates a default game configuration, with no time constraint, pvp game mode and no name players. */
  def default: GameConfiguration = GameConfiguration(
    TimeConstraint.NoLimit,
    GameMode.PVP,
    WhitePlayer.default,
    BlackPlayer.default
  )
