/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.moves

import io.github.chess.engine.model.board.Position

/** Represents the moving from a [[Position]] to another [[Position]]. */
trait CastlingMove extends Move:

  /**
   * Returns the [[Position]] where the rook should be before the move.
   * @return the [[Position]] where the rook should be before the move
   */
  def rookFromPosition: Position

  /**
   * Returns the [[Position]] where the rook should be after the move.
   * @return the [[Position]] where the rook should be after the move
   */
  def rookToPosition: Position

/** Factory for [[CastlingMove]] instances. */
object CastlingMove:
  /**
   * Creates a castling move, using the needed positions.
   * @param kingFrom king start [[Position]]
   * @param kingTo king target [[Position]]
   * @param rookFromPosition rook start [[Position]]
   * @param rookToPosition rook target [[Position]]
   * @return a new [[CastlingMove]]
   */
  def apply(
      kingFrom: Position,
      kingTo: Position,
      rookFromPosition: Position,
      rookToPosition: Position
  ): CastlingMove = CastlingMoveImpl(kingFrom, kingTo, rookFromPosition, rookToPosition)

  private case class CastlingMoveImpl(
      override val from: Position,
      override val to: Position,
      override val rookFromPosition: Position,
      override val rookToPosition: Position
  ) extends CastlingMove
