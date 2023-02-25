/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess

import io.github.chess.model.moves.Move
import io.github.chess.model.{ChessGameStatus, Position}

/** Chess rule mixin that checks the presence of a piece in the destination position after finding it. */
trait AvoidAllPiecesRule extends ChessRule:

  abstract override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    super.findMoves(position, status).filter(move => !status.chessBoard.pieces.contains(move.to))
