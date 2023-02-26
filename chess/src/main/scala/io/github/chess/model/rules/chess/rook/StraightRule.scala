/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.rook

import io.github.chess.model.rules.chess.DirectionalRule
import io.github.chess.model.rules.prolog.{EPrologRule, NPrologRule, SPrologRule, WPrologRule}
import StraightRule.*

/** Represents the chess rule that can find all the moves in straight directions, analyzing the status. */
class StraightRule extends DirectionalRule:
  override def directions: Set[Direction] = rules.map(prologRuleToDirection)

/** Companion object of [[StraightRule]]. */
object StraightRule:
  private val rules = Set(NPrologRule(), EPrologRule(), SPrologRule(), WPrologRule())
