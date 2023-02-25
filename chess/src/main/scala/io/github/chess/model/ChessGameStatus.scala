/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.model.configuration.GameConfiguration

/** The state of a chess game. */
trait ChessGameStatus:
  /** @return the chess board of this chess game */
  def chessBoard: ChessBoard

  /** @return the history of the moves executed in this chess game */
  def history: ChessGameHistory

  /** @return the team who is currently playing */
  def currentTurn: Team

  /** @return the configuration of the game */
  def gameConfiguration: GameConfiguration

  /**
   * Updates the chess board of this chess game status with the specified chess board.
   * @param newChessBoard the specified chess board
   * @return a new updated chess game status
   */
  def updateChessBoard(newChessBoard: ChessBoard): ChessGameStatus

  /**
   * Updates the history of this chess game status with the specified history.
   * @param newHistory the specified history
   * @return a new updated chess game status
   */
  def updateHistory(newHistory: ChessGameHistory): ChessGameStatus

  /**
   * Updates the game configuration of this chess game status with the specified game configuration.
   * @param newGameConfiguration the specified game configuration
   * @return a new updated chess game status
   */
  def updateGameConfiguration(newGameConfiguration: GameConfiguration): ChessGameStatus

  /**
   * Updates the currently playing team from White to Black and viceversa
   * @return a new updated chess game status
   */
  def changeTeam(): ChessGameStatus

  // TODO: consider this
  //  def timeRemaining: Time

/** Companion object of [[ChessGameStatus]]. */
object ChessGameStatus:
  /**
   * @param chessBoard the chess board of the game
   * @param history the history of the moves executed in the game
   * @param initialTurn the team who is playing first
   * @param gameConfiguration configuration of the game
   * @return a state
   */
  def apply(
      chessBoard: ChessBoard = ChessBoard.standard,
      history: ChessGameHistory = ChessGameHistory(),
      initialTurn: Team = Team.WHITE,
      gameConfiguration: GameConfiguration = GameConfiguration.default
  ): ChessGameStatus =
    BasicChessGameStatus(chessBoard, history, initialTurn, gameConfiguration)

  /** Basic implementation of a [[ChessGameStatus]]. */
  private case class BasicChessGameStatus(
      chessBoard: ChessBoard,
      history: ChessGameHistory,
      currentTurn: Team,
      gameConfiguration: GameConfiguration
  ) extends ChessGameStatus:
    override def updateChessBoard(newChessBoard: ChessBoard): ChessGameStatus =
      BasicChessGameStatus(newChessBoard, history, currentTurn, gameConfiguration)
    override def updateHistory(newHistory: ChessGameHistory): ChessGameStatus =
      BasicChessGameStatus(chessBoard, newHistory, currentTurn, gameConfiguration)
    override def updateGameConfiguration(newGameConfiguration: GameConfiguration): ChessGameStatus =
      BasicChessGameStatus(chessBoard, history, currentTurn, newGameConfiguration)
    override def changeTeam(): ChessGameStatus =
      BasicChessGameStatus(chessBoard, history, currentTurn.oppositeTeam, gameConfiguration)
