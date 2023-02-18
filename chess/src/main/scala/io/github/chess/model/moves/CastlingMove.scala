/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.moves

import io.github.chess.model.Position
import io.github.chess.model.pieces.King

/** Represents the moving from a [[Position]] to another [[Position]]. */
trait CastlingMove extends Move:

  def rookFromPosition: Position

  def rookToPosition: Position

/** Factory for [[Move]] instances. */
object CastlingMove:
  /**
   * Creates a castling move, using the two needed positions.
   * @param from source [[Position]]
   * @param to   target [[Position]]
   * @param rookFromPosition rook start position
   * @param rookToPosition rook target position
   * @return a new [[Move]]
   */
  def apply(
      from: Position,
      to: Position,
      rookFromPosition: Position,
      rookToPosition: Position
  ): CastlingMove = CastlingMoveImpl(from, to, rookFromPosition, rookToPosition)

  private case class CastlingMoveImpl(
      override val from: Position,
      override val to: Position,
      override val rookFromPosition: Position,
      override val rookToPosition: Position
  ) extends CastlingMove
