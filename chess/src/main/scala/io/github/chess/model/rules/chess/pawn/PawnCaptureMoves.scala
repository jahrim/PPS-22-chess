/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.pawn

import io.github.chess.model.moves.Move
import io.github.chess.model.rules.chess.{ChessRule, RuleShorthands}
import io.github.chess.model.rules.prolog.{BlackPawnCaptureRule, WhitePawnCaptureRule}
import io.github.chess.model.{ChessGameStatus, Position, Team, moves}

/** Finds all moves with which a pawn can capture an adversary piece. */
class PawnCaptureMoves extends ChessRule with RuleShorthands:

  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    status.chessBoard.pieces.get(position) match
      case Some(piece) =>
        (piece.team match
          case Team.WHITE => WhitePawnCaptureRule()
          case Team.BLACK => BlackPawnCaptureRule()
        ).findPositions(position).map(Move(position, _)).toSet
      case None => Set.empty
