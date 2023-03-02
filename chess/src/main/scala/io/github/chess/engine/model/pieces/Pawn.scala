/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.pieces

import io.github.chess.engine.model.game.Team
import io.github.chess.engine.model.rules.chess.ChessRule
import io.github.chess.engine.model.rules.chess.pawn.PawnRule

/** Represents the particular piece of the pawn. */
case class Pawn(override val team: Team) extends Piece:
  override def rule: ChessRule = Pawn.pawnRule

/** Object for Pawn that creates and stores a single [[PawnRule]] that all other Pawns will use. */
object Pawn:
  private final val pawnRule = PawnRule()
