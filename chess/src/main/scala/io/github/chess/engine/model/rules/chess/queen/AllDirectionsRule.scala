/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.queen

import AllDirectionsRule.*
import io.github.chess.engine.model.rules.chess.DirectionalRule
import io.github.chess.engine.model.rules.chess.bishop.DiagonalRule
import io.github.chess.engine.model.rules.chess.rook.StraightRule

/** Represents the chess rule that can find all the moves in every directions, analyzing the status. */
class AllDirectionsRule extends DirectionalRule:
  override def directions: Set[Direction] = rules.flatMap { _.directions }

/** Companion object of [[AllDirectionsRule]]. */
object AllDirectionsRule:
  private val rules: Set[DirectionalRule] = Set(DiagonalRule(), StraightRule())
