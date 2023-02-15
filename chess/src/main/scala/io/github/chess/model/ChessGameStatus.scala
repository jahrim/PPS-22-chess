/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

/** The state of a chess game. */
trait ChessGameStatus:
  /** @return the chess board of this chess game */
  def chessBoard: ChessBoard

  /** @return the history of the moves executed in this chess game */
  def history: ChessGameHistory

  /** @return the team who is currently playing */
  def currentTurn: Team

  // TODO: consider these
  // def gameConfiguration: GameConfiguration
  // def timeRemaining: Time
