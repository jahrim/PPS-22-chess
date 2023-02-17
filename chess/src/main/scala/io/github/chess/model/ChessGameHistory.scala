/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.model.moves.Move
import io.github.chess.model.pieces.Piece

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
   * @return this
   */
  def save(p: Piece, m: Move): Unit

/** Companion object of [[ChessGameHistory]]. */
object ChessGameHistory:
  /** @return a new basic history of the moves executed in a game of chess */
  def apply(): ChessGameHistory = BasicChessGameHistory()

  /** Basic implementation of a [[ChessGameHistory]]. */
  private case class BasicChessGameHistory() extends ChessGameHistory:
    private var moves: List[(Piece, Move)] = List.empty
    override def all: Seq[Move] = this.moves.map(_._2)

    override def ofPiece(p: Piece): Seq[Move] = this.moves.filter(p eq _._1).map(_._2)

    override def save(p: Piece, m: Move): Unit = this.moves :+= (p, m)
