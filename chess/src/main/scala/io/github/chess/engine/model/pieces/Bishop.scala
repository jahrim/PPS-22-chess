/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.pieces

import io.github.chess.engine.model.game.Team
import io.github.chess.engine.model.rules.chess.ChessRule
import io.github.chess.engine.model.rules.chess.bishop.BishopRule

/** Represents the particular piece of the bishop. */
case class Bishop(override val team: Team) extends Piece:
  override def rule: ChessRule = Bishop.bishopRule

/** Object for Bishop that creates and stores a single [[BishopRule]] that all other Bishops will use. */
object Bishop:
  private final val bishopRule = BishopRule()
