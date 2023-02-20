/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.pawn

import io.github.chess.model.moves.Move
import io.github.chess.model.rules.chess.ChessRule
import io.github.chess.model.{ChessGameStatus, Position, Team, moves}

/** Implementation of a chess rule that makes move a piece one step forward in the column. */
class SingleStepRule extends ChessRule:

  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    status.chessBoard.pieces.get(position) match
      case Some(piece) =>
        Set(
          Move(
            position,
            piece.team match
              case Team.WHITE => position.rankUp()
              case Team.BLACK => position.rankDown()
          )
        )
      case None => Set.empty
