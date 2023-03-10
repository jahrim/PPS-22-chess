/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.pieces

import io.github.chess.engine.model.game.Team
import io.github.chess.engine.model.rules.chess.ChessRule
import io.github.chess.engine.model.rules.chess.rook.RookRule

/** Represents the particular piece of the rook. */
case class Rook(override val team: Team) extends Piece:
  override def rule: ChessRule = Rook.rookRule

/** Object for Rook that creates and stores a single [[RookRule]] that all other Rooks will use. */
object Rook:
  private final val rookRule = RookRule()
