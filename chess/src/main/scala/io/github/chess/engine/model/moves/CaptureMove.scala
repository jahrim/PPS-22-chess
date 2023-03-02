/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.moves

import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.pieces.Piece

/** A move where a piece is captured. */
trait CaptureMove extends Move:
  /** @return the piece captured with this move. */
  def capturedPiece: Piece

/** Companion object of [[CaptureMove]]. */
object CaptureMove:

  /**
   * Creates a capture move, using the two needed positions and the captured piece.
   * @param from source [[Position]]
   * @param to target [[Position]]
   * @param capturedPiece the piece that was captured with this move.
   * @return a new [[CaptureMove]]
   */
  def apply(from: Position, to: Position, capturedPiece: Piece): CaptureMove =
    BasicCaptureMove(from, to, capturedPiece)

  /** Basic implementation of [[CaptureMove]]. */
  private case class BasicCaptureMove(
      override val from: Position,
      override val to: Position,
      override val capturedPiece: Piece
  ) extends CaptureMove:
    override def toString: String = s"${this.from}${this.to}"
