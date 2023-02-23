/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.pieces

import io.github.chess.model.rules.chess.ChessRule
import io.github.chess.model.{ChessBoard, Team}

/** Represents every piece of the [[ChessBoard]]. */
trait Piece:

  /** @return the team who owns this piece */
  def team: Team

  /** Returns the [[ChessRule]] able to give all the possible moves from a starting position. */
  def rule: ChessRule
