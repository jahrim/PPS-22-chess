/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

/** Represents the moving from a [[Position]] to another [[Position]]. */
trait Move:

  /**
   * Returns the source [[Position]].
   * @return a [[Position]]
   */
  def from: Position

  /**
   * Returns the target [[Position]].
   * @return a [[Position]]
   */
  def to: Position

/** Factory for [[Move]] instances. */
object Move:

  /**
   * Creates a move, using the two needed positions.
   * @param from source [[Position]]
   * @param to target [[Position]]
   * @return a new [[Move]]
   */
  def apply(from: Position, to: Position): Move = SimpleMove(from, to)

  private case class SimpleMove(override val from: Position, override val to: Position)
      extends Move:
    override def toString: String = s"${this.from}${this.to}"
