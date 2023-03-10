/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.pieces

import io.github.chess.engine.model.game.Team
import io.github.chess.engine.model.rules.chess.ChessRule
import io.github.chess.engine.model.rules.chess.queen.QueenRule

/** Represents the particular piece of the queen. */
case class Queen(override val team: Team) extends Piece:
  override def rule: ChessRule = Queen.queenRule

/** Object for Queen that creates and stores a single [[QueenRule]] that all other Queen will use. */
object Queen:
  private final val queenRule = QueenRule()
