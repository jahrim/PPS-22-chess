/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules

import io.github.chess.model.{ChessGameStatus, Move, Position}

/** Represents the chess rule that can find all the moves executing an L direction movement. */
class LRule extends ChessRule:

  private final val values = Set(-1, 1, -2, 2)

  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    findPositions(position)
      .filter((x, y) => x >= 0 && x <= 7 && y >= 0 && y <= 7)
      .filter((x, y) => !status.chessBoard.pieces.contains((x, y)))
      .map(Move(position, _))

  private def findPositions(position: Position): Set[(Int, Int)] =
    for (
      x <- values;
      y <- values
      if x.abs != y.abs
    )
      yield (position.file.ordinal + x, position.rank.ordinal + y)
