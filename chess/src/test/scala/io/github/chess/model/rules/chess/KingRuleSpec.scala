/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess

import io.github.chess.AbstractSpec
import io.github.chess.model.pieces.Pawn
import io.github.chess.model.{ChessBoard, ChessGameStatus, Position}

/** Test suite for [[King]]. */
class KingRuleSpec extends AbstractSpec:

  private val initialPosition: Position = (1, 2)
  private val exactPositions: Set[Position] =
    Set((0, 2), (0, 3), (1, 1), (1, 3), (2, 1), (2, 2), (2, 3))
  private val rule = KingMovementRule()
  private val chessBoard: ChessBoard = ChessBoard.empty
  chessBoard.setPiece((0, 1), Pawn())
  private val chessGameStatus = ChessGameStatus(chessBoard)
  private val moves = rule.findMoves(initialPosition, chessGameStatus).map(_.to)

  "The KingMovementRule" should "let move the king in all the directions stepped by one" in {
    moves shouldEqual exactPositions
  }
