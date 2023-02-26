/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.knight

import io.github.chess.AbstractSpec
import io.github.chess.model.pieces.{Knight, Pawn}
import io.github.chess.model.rules.chess.knight.KnightRule
import io.github.chess.model.{ChessBoard, ChessGameStatus, Position, Team}

/** Test suite for [[Knight]]. */
class KnightRuleSpec extends AbstractSpec:

  private val initialPosition: Position = (1, 3)
  private val exactPositions: Set[Position] = Set((3, 4), (3, 2), (2, 1), (2, 5), (0, 5))
  private val lRule = KnightRule()
  private val chessBoard: ChessBoard =
    ChessBoard.empty
      .setPiece((0, 1), Pawn(Team.WHITE))
      .setPiece(initialPosition, Knight(Team.WHITE))
  private val chessGameStatus = ChessGameStatus(chessBoard)
  private val moves = lRule.findMoves(initialPosition, chessGameStatus).map(_.to)

  "The L rule" should "let move the knight in all the exact positions following the rule" in {
    moves shouldEqual exactPositions
  }
