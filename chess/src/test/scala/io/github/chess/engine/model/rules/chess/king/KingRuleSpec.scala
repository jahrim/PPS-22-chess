/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.king

import io.github.chess.AbstractSpec
import io.github.chess.engine.model.board.{ChessBoard, Position}
import io.github.chess.engine.model.game.{ChessGameStatus, Team}
import io.github.chess.engine.model.pieces.{King, Pawn}

/** Test suite for [[King]]. */
class KingRuleSpec extends AbstractSpec:

  private val initialPosition: Position = (1, 2)
  private val exactPositions: Set[Position] =
    Set((0, 2), (0, 3), (1, 1), (1, 3), (2, 1), (2, 2), (2, 3))
  private val rule = KingMovementRule()
  private val chessBoard: ChessBoard =
    ChessBoard.empty
      .setPiece((0, 1), Pawn(Team.WHITE))
      .setPiece(initialPosition, King(Team.WHITE))
  private val chessGameStatus = ChessGameStatus(chessBoard)
  private val moves = rule.findMoves(initialPosition, chessGameStatus).map(_.to)

  "The KingMovementRule" should "let move the king in all the directions stepped by one" in {
    moves shouldEqual exactPositions
  }
