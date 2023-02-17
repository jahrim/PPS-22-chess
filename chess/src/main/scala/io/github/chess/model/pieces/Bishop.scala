/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.pieces

import io.github.chess.model.rules.chess.{ChessRule, DiagonalRule}
import io.github.chess.model.Position

/** Represents the particular piece of the bishop. */
trait Bishop extends Piece

/** Factory for [[Bishop]] instances. */
object Bishop:

  /**
   * Creates a new bishop.
   * @return a new [[Bishop]]
   */
  def apply(): Bishop = SimpleBishop()

  private case class SimpleBishop() extends Bishop:

    override val rule: ChessRule = DiagonalRule()

    // TODO da rimuovere
    override def findMoves(position: Position): Set[Position] = ???
