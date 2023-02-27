/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.pawn

import io.github.chess.model.moves.{DoubleMove, Move}
import io.github.chess.model.rules.chess.ChessRule
import io.github.chess.model.{ChessGameStatus, Position, Rank, Team, moves}

/** Implementation of a chess rule that makes move a piece two positions forward in the column. */
class TwoStepRule extends ChessRule:

  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    status.chessBoard.pieces.get(position) match
      case Some(piece) =>
        piece.team match
          case Team.WHITE if position.rank.ordinal <= Rank._7.ordinal =>
            Set(DoubleMove(position, position.rankUp().rankUp()))
          case Team.BLACK if position.rank.ordinal >= Rank._2.ordinal =>
            Set(DoubleMove(position, position.rankDown().rankDown()))
          case _ => Set.empty
      case None => Set.empty
