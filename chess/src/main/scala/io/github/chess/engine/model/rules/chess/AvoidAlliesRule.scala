/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess

import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.game.ChessGameStatus
import io.github.chess.engine.model.moves.{CaptureMove, Move}

/**
 * Mixin for [[ChessRule]]s, that removes from the set of [[Move]]s those that would capture an ally piece
 * (removes "friendly fire").
 */
trait AvoidAlliesRule extends CaptureMoveMapper with RuleShorthands:

  abstract override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    pieceAt(position)(using status) match
      case Some(piece) =>
        super
          .findMoves(position, status)
          .filter(move =>
            move match
              case move: CaptureMove => move.capturedPiece.team != piece.team
              case _                 => true
          )
      case None => Set.empty
