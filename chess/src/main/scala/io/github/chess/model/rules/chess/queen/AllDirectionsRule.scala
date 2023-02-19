/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.queen

import io.github.chess.model.moves.Move
import io.github.chess.model.rules.chess.ChessRule
import io.github.chess.model.rules.chess.bishop.DiagonalRule
import io.github.chess.model.rules.chess.rook.StraightRule
import io.github.chess.model.{ChessGameStatus, Position}

/** Represents the chess rule that can find all the moves in every directions, analyzing the status. */
class AllDirectionsRule extends ChessRule:
  private val rules: Set[ChessRule] = Set(DiagonalRule(), StraightRule())
  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    this.rules.findMoves(position, status)
