/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess

import io.github.chess.model.pieces.Piece
import io.github.chess.model.{ChessGameStatus, Position}

/**
 * A set of shorthands for writing rules.
 *
 * @note extending this trait will make the shorthands available to the extending class
 */
trait RuleShorthands:
  /**
   * @param p      the specified position
   * @param status the specified state of the game as a given
   * @return true if the specified position is occupied in the chess board
   */
  protected def occupied(p: Position)(using status: ChessGameStatus): Boolean =
    status.chessBoard.pieces.contains(p)

  /**
   * @param p      the specified position
   * @param status the specified state of the game as a given
   * @return an option containing the piece at the specified position or an empty option
   */
  protected def pieceAt(p: Position)(using status: ChessGameStatus): Option[Piece] =
    status.chessBoard.pieces.get(p)

  /**
   * @param p the specified position
   * @param status the specified state of the game as a given
   * @return true if the piece at the specified position belongs to the team currently playing,
   *         false otherwise
   */
  protected def isPieceOfCurrentTurn(p: Position)(using status: ChessGameStatus): Boolean =
    pieceAt(p).exists(piece => piece.team == status.currentTurn)

  /**
   * @param p      the specified position
   * @param status the specified state of the game as a given
   * @return true if the piece at the specified position has never moved before, false otherwise
   */
  protected def isFirstMovementOf(p: Position)(using status: ChessGameStatus): Boolean =
    pieceAt(p).exists(piece => status.history.ofPiece(piece).isEmpty)
