/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

/** The cause of a game over. */
enum GameOverCause:
  /** When the king of a player is threatened, but the player has no available moves. */
  case Checkmate

  /** When a player has no available moves. */
  case Stale

  /** When a player has surrendered. */
  case Surrender

  /** When the timer of a player has run out. */
  case Timeout
