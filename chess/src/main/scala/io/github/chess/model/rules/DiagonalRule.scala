/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules

import io.github.chess.model.{Move, Position}

/** Represents the chess rule that can find all the moves in diagonal directions, analyzing the status. */
class DiagonalRule extends ChessRule:

  private val northWestRule = NWPrologRule()
  private val northEastRule = NEPrologRule()
  private val southWestRule = SouthWestRule()
  private val southEastRule = SouthEastRule()

  override def findMoves(position: Position): Set[Move] =
    reduceInsideBoard(northWestRule.findPositions(position), position) ++
      reduceInsideBoard(northEastRule.findPositions(position), position) ++
      reduceInsideBoard(southWestRule.findPositions(position), position) ++
      reduceInsideBoard(southEastRule.findPositions(position), position)

  private def reduceInsideBoard(
      infiniteDirectionPositions: LazyList[(Int, Int)],
      position: Position
  ): Set[Move] =
    infiniteDirectionPositions
      .takeWhile((x, y) => x >= 0 && x <= 7 && y >= 0 && y <= 7)
      .map(Move(position, _))
      .toSet
