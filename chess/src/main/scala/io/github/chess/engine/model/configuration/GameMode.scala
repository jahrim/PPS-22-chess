/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.configuration

/** Represents the possible game mode for the chess game. */
enum GameMode:

  /** Represents the game mode in which 2 human players play against each other. */
  case PVP

  /** Represents the game mode in which 1 human player plays against the computer. */
  case PVE
