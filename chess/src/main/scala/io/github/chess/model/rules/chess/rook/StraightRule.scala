/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.rook

import io.github.chess.util.general.GivenExtension.within
import io.github.chess.model.moves.Move
import io.github.chess.model.rules.chess.{ChessRule, DirectionalRule}
import io.github.chess.model.rules.chess.ChessRule.*
import io.github.chess.model.rules.chess.rook.StraightRule.{
  ePrologRule,
  nPrologRule,
  sPrologRule,
  wPrologRule
}
import io.github.chess.model.rules.prolog.{
  EPrologRule,
  NPrologRule,
  PrologRule,
  SPrologRule,
  WPrologRule
}
import io.github.chess.model.{ChessGameStatus, Position, moves}

/** Represents the chess rule that can find all the moves in straight directions, analyzing the status. */
class StraightRule extends ChessRule with DirectionalRule:
  private val rules: Set[PrologRule] =
    Set(
      StraightRule.nPrologRule,
      StraightRule.wPrologRule,
      StraightRule.sPrologRule,
      StraightRule.ePrologRule
    )
  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    this.rules.flatMap { analyseDirection(position, status, _) }

/**
 * Object for the Straight Rule that creates and stores a single instance of the prolog engine
 *  for each of its directions.
 */
object StraightRule:
  private val nPrologRule = NPrologRule()
  private val wPrologRule = WPrologRule()
  private val sPrologRule = SPrologRule()
  private val ePrologRule = EPrologRule()
