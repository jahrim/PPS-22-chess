/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess

import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.game.{ChessGameAnalyzer, ChessGameStatus}
import io.github.chess.engine.model.moves.Move
import io.github.chess.util.general.GivenExtension.within

/** Mixin that removes all the moves that would put the current player in check from the specified rule. */
trait AvoidSelfCheckRule extends ChessRule with RuleShorthands:
  abstract override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    within(status) {
      // Checking if the piece belongs to the team currently playing is required to avoid infinite recursion
      super.findMoves(position, status).filter { move =>
        !isPieceOfCurrentTurn(position)
        ||
        !ChessGameAnalyzer.check(
          status.updateChessBoard {
            status.chessBoard.movePiece(move.from, move.to)
          }
        )
      }
    }
