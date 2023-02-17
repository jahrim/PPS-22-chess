/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.pawn

import io.github.chess.model.{ChessGameStatus, Move, Pawn, Position}
import io.github.chess.model.rules.ChessRule

/** Implementation of the [[ChessRule]] that applies the rules of the [[Pawn]]. */
case class PawnRule() extends ChessRule:

  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    PawnMovementRule().findMoves(position, status) ++
      PawnCaptureRule().findMoves(position, status)
