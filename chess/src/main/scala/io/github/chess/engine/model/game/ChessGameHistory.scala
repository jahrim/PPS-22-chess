/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.game

import io.github.chess.engine.model.moves.Move
import io.github.chess.engine.model.pieces.Piece

/** The history of the moves executed in a chess game. */
trait ChessGameHistory:
  /**
   * @return all the moves registered in this history ordered by decreasing age
   *         (the first move is the oldest, the last is the newest)
   */
  def all: Seq[Move]

  /**
   * @param p the specified piece
   * @return all the moves registered for the specified piece in this history ordered by decreasing age
   *         (the first move is the oldest, the last is the newest)
   */
  def ofPiece(p: Piece): Seq[Move]

  /**
   * Saves the specified move executed by the specified piece in this history.
   * @param p the piece executing the move
   * @param m the move executed
   * @return a new updated chess game history
   */
  def save(p: Piece, m: Move): ChessGameHistory

  /**
   * Saves the specified moves executed by the specified pieces in this history.
   * @param moves the specified moves executed by the specified pieces
   * @return a new updated chess game history
   */
  def saveAll(moves: Iterable[(Piece, Move)]): ChessGameHistory =
    moves.foldLeft(this) { (history, move) => history.save(move._1, move._2) }

/** Companion object of [[ChessGameHistory]]. */
object ChessGameHistory:
  /** @return a new basic history of the moves executed in a game of chess */
  def apply(): ChessGameHistory = BasicChessGameHistory()

  /** Basic implementation of a [[ChessGameHistory]]. */
  private case class BasicChessGameHistory(
      private val moves: List[(Piece, Move)] = List.empty
  ) extends ChessGameHistory:
    override def all: Seq[Move] = this.moves.map(_._2)

    override def ofPiece(p: Piece): Seq[Move] = this.moves.filter(p eq _._1).map(_._2)

    override def save(p: Piece, m: Move): ChessGameHistory =
      BasicChessGameHistory(this.moves :+ (p, m))
