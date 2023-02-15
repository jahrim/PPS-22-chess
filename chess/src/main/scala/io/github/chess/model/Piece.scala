/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.model.rules.ChessRule

/** Represents every piece of the [[ChessBoard]]. */
trait Piece:

  /** Returns the [[ChessRule]] able to give all the possible moves from a starting position. */
  def rule: ChessRule

  // TODO da rimuovere
  /**
   * Returns all the possibile positions that the piece can go to, considering only its own rules.
   * @param position source [[Position]]
   * @return a [[Set]] of [[Position]]
   */
  def findMoves(position: Position): Set[Position]
