/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.knight

import io.github.chess.engine.model.board.ChessBoardBuilder.DSL.*
import io.github.chess.engine.model.board.{ChessBoard, File, Position, Rank}
import io.github.chess.engine.model.game.ChessGameStatus
import io.github.chess.engine.model.rules.AbstractChessRuleSpec

/** Test suite for [[KnightRule]]. */
class KnightRuleSpec extends AbstractChessRuleSpec:

  private val knightPosition = Position(File.E, Rank._5)

  private val chessBoardOnlyKnight = ChessBoard {
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | N | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
  }
  private val chessBoardMovesOnlyKnight = ChessBoard {
    * | * | * | * | * | * | * | *
    * | * | * | X | * | X | * | *
    * | * | X | * | * | * | X | *
    * | * | * | * | * | * | * | *
    * | * | X | * | * | * | X | *
    * | * | * | X | * | X | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
  }

  "The knight rule" should "allow the knight to move in all the exact positions following the rule" in {
    val chessGameStatus = ChessGameStatus(chessBoardOnlyKnight)
    getChessBoardFromMoves(knightPosition, chessGameStatus) shouldEqual chessBoardMovesOnlyKnight
  }

  private val chessBoardWithAllies = ChessBoard {
    * | * | * | * | * | * | * | *
    * | * | * | * | * | B | * | *
    * | * | R | * | * | * | * | *
    * | * | * | * | N | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | Q | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
  }
  private val chessBoardMovesWithAllies = ChessBoard {
    * | * | * | * | * | * | * | *
    * | * | * | X | * | * | * | *
    * | * | * | * | * | * | X | *
    * | * | * | * | * | * | * | *
    * | * | X | * | * | * | X | *
    * | * | * | X | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
  }

  it should "avoid the movement over the allied pieces" in {
    val chessGameStatus = ChessGameStatus(chessBoardWithAllies)
    getChessBoardFromMoves(knightPosition, chessGameStatus) shouldEqual chessBoardMovesWithAllies
  }

  private val chessBoardWithEnemies = ChessBoard {
    * | * | * | * | * | * | * | *
    * | * | * | * | * | B | * | *
    * | * | r | * | * | * | * | *
    * | * | * | * | N | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | q | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
  }
  private val chessBoardMovesWithEnemies = ChessBoard {
    * | * | * | * | * | * | * | *
    * | * | * | X | * | * | * | *
    * | * | X | * | * | * | X | *
    * | * | * | * | * | * | * | *
    * | * | X | * | * | * | X | *
    * | * | * | X | * | X | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
  }

  it should "allow to capture enemy pieces" in {
    val chessGameStatus = ChessGameStatus(chessBoardWithEnemies)
    getChessBoardFromMoves(knightPosition, chessGameStatus) shouldEqual chessBoardMovesWithEnemies
  }
