/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess

import io.github.chess.model.moves.{CaptureMove, Move}
import io.github.chess.model.rules.chess.ChessRule
import io.github.chess.model.{ChessGameStatus, Position, Team}

/** Mixin that checks in which positions, among the found moves, there are enemy pieces, to perform the capture. */
trait BaseCaptureRule extends ChessRule:

  abstract override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    super
      .findMoves(position, status)
      .filter(move =>
        status.chessBoard.pieces.get(position) match
          case Some(piece) =>
            piece.team match
              case Team.WHITE => status.chessBoard.blackPieces.contains(move.to)
              case Team.BLACK => status.chessBoard.whitePieces.contains(move.to)
          case None => false
      )
