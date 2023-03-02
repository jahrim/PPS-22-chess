/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess

import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.game.{ChessGameStatus, Team}
import io.github.chess.engine.model.moves.{CaptureMove, Move}

/** Mixin that checks in which positions, among the found moves, there are enemy pieces, to perform the capture. */
trait BaseCaptureRule extends ChessRule:

  abstract override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    super
      .findMoves(position, status)
      .filter(move =>
        status.chessBoard.pieces.get(position) match
          case Some(piece) => status.chessBoard.pieces(piece.team.oppositeTeam).contains(move.to)
          case None        => false
      )
