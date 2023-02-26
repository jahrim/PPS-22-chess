/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.bishop

import io.github.chess.model.rules.chess.DirectionalRule
import io.github.chess.model.rules.prolog.{NEPrologRule, NWPrologRule, SEPrologRule, SWPrologRule}
import DiagonalRule.*

/** Represents the chess rule that can find all the moves in diagonal directions, analyzing the status. */
class DiagonalRule extends DirectionalRule:
  override def directions: Set[Direction] = rules.map(prologRuleToDirection)

/** Companion object of [[DiagonalRule]]. */
object DiagonalRule:
  private val rules = Set(NEPrologRule(), SEPrologRule(), SWPrologRule(), NWPrologRule())
