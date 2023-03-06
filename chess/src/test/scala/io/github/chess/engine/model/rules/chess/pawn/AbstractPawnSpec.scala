/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.pawn

import io.github.chess.AbstractSpec
import io.github.chess.engine.model.pieces.{Pawn, Piece}
import io.github.chess.engine.model.board.{ChessBoard, File, Position, Rank}
import io.github.chess.engine.model.game.ChessGameStatus
import io.github.chess.engine.model.game.Team

/**
 * Abstract helper class for the pawn testing, that provides common functionalities.
 * It clears its board after each test.
 */
abstract class AbstractPawnSpec extends AbstractSpec:

  /** Contains all positions in which a pawn can be possibly found during a chess game. */
  protected val pawnPositions: Array[Position] =
    for
      file <- File.values
      rank <- Rank.values.slice(1, 7)
    yield Position(file, rank)

  /** Contains all positions in which white pawns start a chess game. */
  protected val whitePawnInitialPositions: Array[Position] = File.values.map(Position(_, Rank._2))

  /** Contains all positions in which black pawns start a chess game. */
  protected val blackPawnInitialPositions: Array[Position] = File.values.map(Position(_, Rank._7))

  /** Contains the current board. */
  protected var board: ChessBoard = ChessBoard.empty

  /** Contains the current status of the game. */
  protected var status: ChessGameStatus = ChessGameStatus(board)

  // Clears the chess board after each test.
  after { cleanBoard() }

  /**
   * Generator of pawns of the [[Team.WHITE]] team.
   *
   * @return a new instance of a pawn of the white team
   */
  protected def whitePawn: Pawn = Pawn(Team.WHITE)

  /**
   * Generator of pawns of the [[Team.BLACK]] team.
   *
   * @return a new instance of a pawn of the black team
   */
  protected def blackPawn: Pawn = Pawn(Team.BLACK)

  /**
   * Adds a piece to the chessboard at the specified position.
   *
   * @param position the position in which the piece should be placed
   * @param piece the piece to be placed on the board
   */
  def addPiece(position: Position, piece: Piece): Unit =
    board = board.setPiece(position, piece)
    status = status.updateChessBoard(board)

  /** Cleans the board, removing all the pieces it contains. */
  def cleanBoard(): Unit =
    board = ChessBoard.empty
    status = status.updateChessBoard(board)
