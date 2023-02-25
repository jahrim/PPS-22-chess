/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess

import io.github.chess.model.{ChessGameStatus, Position}
import io.github.chess.model.moves.{CaptureMove, Move}

/**
 *  Mixin for [[ChessRule]]s, that checks whether there are some [[Move]]s that imply 
 *  the capture of a piece (without looking if it's an ally or adversary piece) 
 *  and maps them into [[CaptureMove]]s
 */
trait CaptureMoveMapper extends ChessRule:

  abstract override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    super
      .findMoves(position, status)
      .map(move =>
        status.chessBoard.pieces.get(move.to) match
          case Some(piece) => CaptureMove(move.from, move.to, piece)
          case None        => move
      )
