/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.pieces

import io.github.chess.model.Team
import io.github.chess.model.rules.chess.ChessRule
import io.github.chess.model.rules.chess.queen.QueenRule

/** Represents the particular piece of the queen. */
case class Queen(override val team: Team) extends Piece(team):

  override val rule: ChessRule = QueenRule()
