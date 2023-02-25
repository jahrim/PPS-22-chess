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
 * Mixin for [[ChessRule]]s, that removes from the set of [[Move]]s those that would capture an ally piece
 * (removes "friendly fire").
 */
trait AvoidAlliesRule extends CaptureMoveMapper:

  abstract override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    status.chessBoard.pieces.get(position) match
      case Some(piece) =>
        super
          .findMoves(position, status)
          .filter(move =>
            move match
              case move: CaptureMove => move.capturedPiece.team != piece.team
              case _                 => true
          )
      case None => Set.empty
