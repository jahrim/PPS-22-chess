/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.pawn

import io.github.chess.engine.model.board.{Position, Rank}
import io.github.chess.engine.model.game.{ChessGameStatus, Team}
import io.github.chess.engine.model.moves.{DoubleMove, Move}
import io.github.chess.engine.model.rules.chess.ChessRule

/** Implementation of a chess rule that makes move a piece two positions forward in the column. */
class TwoStepRule extends ChessRule:

  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    status.chessBoard.pieces.get(position) match
      case Some(piece) =>
        if (piece.team == Team.WHITE && position.rank.ordinal < Rank._7.ordinal) ||
          (piece.team == Team.BLACK && position.rank.ordinal > Rank._2.ordinal)
        then Set(DoubleMove(position, piece))
        else Set.empty
      case None => Set.empty
