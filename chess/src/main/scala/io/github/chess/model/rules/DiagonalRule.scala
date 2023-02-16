/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules

import io.github.chess.model.{ChessGameStatus, Move, Position}

/** Represents the chess rule that can find all the moves in diagonal directions, analyzing the status. */
class DiagonalRule extends ChessRule:

  private val northWestRule = NWPrologRule()
  private val northEastRule = NEPrologRule()
  private val southWestRule = SouthWestRule()
  private val southEastRule = SouthEastRule()
  private val rules = Set(northWestRule, northEastRule, southEastRule, southWestRule)

  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    rules.flatMap(
      _.findPositions(position)
        .takeWhile((x, y) => !status.chessBoard.pieces.contains((x, y)))
        .map(Move(position, _))
        .toSet
    )
