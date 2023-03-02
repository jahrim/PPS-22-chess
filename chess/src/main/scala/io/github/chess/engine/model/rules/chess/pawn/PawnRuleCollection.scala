/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.pawn

import PawnRuleCollection.*
import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.game.ChessGameStatus
import io.github.chess.engine.model.moves.Move
import io.github.chess.engine.model.rules.chess.ChessRule

/** An aggregation of the [[PawnMovementRule]], the [[PawnCaptureRule]] and the [[EnPassantRule]]. */
class PawnRuleCollection extends ChessRule:
  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    pawnMovementRule.findMoves(position, status) ++
      pawnCaptureRule.findMoves(position, status) ++
      enPassantRule.findMoves(position, status)

/** Companion object of [[PawnCaptureRule]]. */
object PawnRuleCollection:
  private val pawnMovementRule: PawnMovementRule = PawnMovementRule()
  private val pawnCaptureRule: PawnCaptureRule = PawnCaptureRule()
  private val enPassantRule: EnPassantRule = EnPassantRule()
