/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.pieces

import io.github.chess.model.rules.chess.ChessRule
import io.github.chess.model.rules.chess.pawn.PawnRule
import io.github.chess.model.Position

/** Represents the particular piece of the pawn. */
trait Pawn extends Piece

/** Factory for [[Pawn]] instances. */
object Pawn:

  /**
   * Creates a new pawn.
   * @return a new [[Pawn]]
   */
  def apply(): Pawn = SimplePawn()

  private case class SimplePawn() extends Pawn:

    override val rule: ChessRule = PawnRule()

    // TODO da rimuovere
    override def findMoves(position: Position): Set[Position] =
      List
        .iterate(0, 8)(_ + 1)
        .flatMap { i => List.iterate(0, 8)(_ + 1).map[Position]((i, _)) }
        .filter { _ != position }
        .toSet
      // TODO Simple set of position
