/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.pieces

import io.github.chess.model.Team
import io.github.chess.model.rules.chess.ChessRule
import io.github.chess.model.rules.chess.king.KingRule

/** Represents the particular piece of the king. */
case class King(override val team: Team) extends Piece(team):

  override def rule: ChessRule = King.kingRule

/** Object for King that creates and stores a single [[KingRule]] that all other Kings will use. */
object King:

  private final val kingRule = KingRule()
