/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.moves

import io.github.chess.model.Position
import io.github.chess.model.pieces.Piece

/** Represents the capture of a pawn by the adversary pawn, due to the application of the "en passant" rule . */
trait EnPassantMove extends CaptureMove:

  /**
   * The position of the captured pawn.
   *
   * @return the position in which the captured pawn is located
   */
  def capturedPiecePosition: Position

/** Factory for [[EnPassantMove]] instances. */
object EnPassantMove:

  /**
   * Creates an "en passant" move.
   *
   * @param from source [[Position]]
   * @param to   target [[Position]]
   * @param capturedPawnPosition [[Position]] of the captured pawn
   * @param capturedPawn the captured pawn
   * @return a new [[DoubleMove]]
   */
  def apply(
      from: Position,
      to: Position,
      capturedPawnPosition: Position,
      capturedPawn: Piece
  ): EnPassantMove =
    EnPassantCaptureMove(from, to, capturedPawnPosition, capturedPawn)

  private case class EnPassantCaptureMove(
      from: Position,
      to: Position,
      capturedPiecePosition: Position,
      capturedPiece: Piece
  ) extends EnPassantMove
