/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules

import io.github.chess.model.{ChessGameStatus, Move, Position}
import io.github.chess.util.Combinator

/** Represents the chess rule that can find the moves in all the directions stepped by one. */
class KingMovementRule extends ChessRule:

  private final val values = Set(0, -1, 1)

  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    Combinator
      .generatePositions(values, (x, y) => !(x == 0 && y == 0), position)
      .filter((x, y) => x >= 0 && x <= 7 && y >= 0 && y <= 7)
      .filter((x, y) => !status.chessBoard.pieces.contains((x, y)))
      .map(Move(position, _))
