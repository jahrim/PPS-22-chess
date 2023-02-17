/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.pieces

import io.github.chess.model.rules.chess.{ChessRule, LRule}
import io.github.chess.model.Position

/** Represents the particular piece of the knight. */
trait Knight extends Piece

/** Factory for [[Knight]] instances. */
object Knight:

  /**
   * Creates a new knight.
   * @return a new [[Knight]]
   */
  def apply(): Knight = SimpleKnight()

  private class SimpleKnight extends Knight:

    override def rule: ChessRule = LRule()

    // TODO da rimuovere
    override def findMoves(position: Position): Set[Position] = ???
