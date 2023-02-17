/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.pawn

import io.github.chess.model.moves.Move
import io.github.chess.model.rules.chess.ChessRule
import io.github.chess.model.rules.prolog.{BlackPawnCaptureRule, WhitePawnCaptureRule}
import io.github.chess.model.{ChessGameStatus, Position, Team, moves}

/** Finds all moves with which a pawn can capture an adversary piece. */
class PawnCaptureRule extends ChessRule:

  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    (status.currentTurn match
      case Team.WHITE => WhitePawnCaptureRule()
      case Team.BLACK => BlackPawnCaptureRule()
    ).findPositions(position).map(Move(position, _)).toSet

    // TODO Add check that there is an adversary piece in the destination position
