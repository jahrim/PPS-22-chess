/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.game

/** The state of a chess game. */
enum ChessGameState:
  /** When the game hasn't been configured yet. */
  case NotConfigured()

  /** When the game has been configured and has started. */
  case Running(status: ChessGameStatus)

  /**
   * When the game is paused, awaiting for the user to
   * promote his pawn.
   */
  case AwaitingPromotion(status: ChessGameStatus)
