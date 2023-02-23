/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.moves

import io.github.chess.model.Position

/** Represents the moving of a pawn by two steps forward. */
trait DoubleMove extends Move:

  /**
   * Gives the position skipped while performing this move.
   *
   * @return position between the starting position and arrival
   */
  def middlePosition: Position

/** Factory for [[DoubleMove]] instances. */
object DoubleMove:

  /**
   * Creates a double move, using the two needed positions.
   *
   * @param from source [[Position]]
   * @param to   target [[Position]]
   * @return a new [[DoubleMove]]
   */
  def apply(from: Position, to: Position): DoubleMove = PawnDoubleMove(from, to)

  private case class PawnDoubleMove(
      override val from: Position,
      override val to: Position
  ) extends DoubleMove:

    override val middlePosition: Position =
      if from._2 > to._2
      then from.rankDown()
      else from.rankUp()
