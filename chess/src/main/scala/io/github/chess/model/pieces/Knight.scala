/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.pieces

import io.github.chess.model.rules.chess.{ChessRule, LRule}
import io.github.chess.model.Team

/** Represents the particular piece of the knight. */
case class Knight(override val team: Team) extends Piece(team):

  override val rule: ChessRule = LRule()
