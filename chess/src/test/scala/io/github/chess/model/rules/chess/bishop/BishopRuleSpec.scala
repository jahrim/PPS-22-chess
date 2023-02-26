/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.bishop

import io.github.chess.AbstractSpec
import io.github.chess.model.pieces.Pawn
import io.github.chess.model.rules.chess.bishop.DiagonalRule
import io.github.chess.model.{ChessBoard, ChessGameStatus, Position, Team}

/** Test suite for [[Bishop]]. */
class BishopRuleSpec extends AbstractSpec:

  private val initialPosition: Position = (2, 3)
  private val northWestPositions: Set[Position] = Set((1, 4), (0, 5))
  private val northEastPositions: Set[Position] =
    Set((3, 4), (4, 5), (5, 6), (6, 7))
  private val southWestPositions: Set[Position] = Set((1, 2))
  private val southEastPositions: Set[Position] = Set((3, 2), (4, 1), (5, 0))
  private val diagonalRule = DiagonalRule()
  private val chessBoard: ChessBoard = ChessBoard.empty.setPiece((0, 1), Pawn(Team.WHITE))
  private val chessGameStatus = ChessGameStatus(chessBoard)
  private val moves = diagonalRule.findMoves(initialPosition, chessGameStatus).map(_.to)

  "The diagonal rule" should "let move the bishop in all the north west positions" in {
    moves should contain allElementsOf northWestPositions
  }

  it should "let move the bishop in all the north east positions" in {
    moves should contain allElementsOf northEastPositions
  }

  it should "let move the bishop in all the south west positions" in {
    moves should contain allElementsOf southWestPositions
  }

  it should "let move the bishop in all the south east positions" in {
    moves should contain allElementsOf southEastPositions
  }
