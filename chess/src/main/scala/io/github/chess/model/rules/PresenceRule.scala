/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules

import io.github.chess.model.rules.ChessRule
import io.github.chess.model.{ChessGameStatus, Move, Position}

/** Chess rule mixin that checks the presence of a piece in the destination position after finding it. */
trait PresenceRule extends ChessRule:

  abstract override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    super.findMoves(position, status).filter(move => !status.chessBoard.pieces.contains(move.to))
