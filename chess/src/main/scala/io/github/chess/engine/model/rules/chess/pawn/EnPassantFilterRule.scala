/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.pawn

import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.game.{ChessGameStatus, Team}
import io.github.chess.engine.model.moves.{DoubleMove, EnPassantMove, Move}
import io.github.chess.engine.model.rules.chess.{ChessRule, RuleShorthands}


/** Mixin that checks whether the "en passant" rule can be applied. */
trait EnPassantFilterRule extends ChessRule with RuleShorthands:

  abstract override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    status.history.all.lastOption match
      case Some(doubleMove: DoubleMove) =>
        super
          .findMoves(position, status)
          .filter(move => move.to == doubleMove.middlePosition)
          .map(move =>
            pieceAt(doubleMove.to)(using status) match
              case Some(capturedPiece) =>
                EnPassantMove(move.from, move.to, doubleMove.to, capturedPiece)
              case None => move
          )
      case _ => Set.empty
