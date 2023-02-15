/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

/** The history of the moves executed in a chess game. */
trait ChessGameHistory:
  /** @return all the moves registered in this history */
  def all: Seq[Move]

  /**
   * @param p the specified piece
   * @return all the moves registered for the specified piece in this history
   */
  def ofPiece(p: Piece): Seq[Move]

  /**
   * Saves the specified move executed by the specified piece in this history.
   * @param p the piece executing the move
   * @param m the move executed
   * @return this
   */
  def save(p: Piece, m: Move): Unit
