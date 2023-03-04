/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.bishop

import io.github.chess.engine.model.board.ChessBoardBuilder.DSL.*
import io.github.chess.engine.model.board.{ChessBoard, File, Position, Rank}
import io.github.chess.engine.model.game.ChessGameStatus
import io.github.chess.engine.model.rules.AbstractChessRuleSpec

/** Test suite for [[BishopRule]]. */
class BishopRuleSpec extends AbstractChessRuleSpec:

  private val bishopPosition = Position(File.E, Rank._5)

  private val chessBoardOnlyBishop = ChessBoard {
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | B | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
  }
  private val chessBoardMovesOnlyBishop = ChessBoard {
    * | X | * | * | * | * | * | X
    * | * | X | * | * | * | X | *
    * | * | * | X | * | X | * | *
    * | * | * | * | * | * | * | *
    * | * | * | X | * | X | * | *
    * | * | X | * | * | * | X | *
    * | X | * | * | * | * | * | X
    X | * | * | * | * | * | * | *
  }

  "The bishop rule" should "allow the bishop to move in all the diagonal directions" in {
    val chessGameStatus = ChessGameStatus(chessBoardOnlyBishop)
    getChessBoardFromMoves(bishopPosition, chessGameStatus) shouldEqual chessBoardMovesOnlyBishop
  }

  private val chessBoardWithAllies = ChessBoard {
    * | * | * | * | * | * | * | *
    * | * | N | * | * | * | * | *
    * | * | * | * | * | R | * | *
    * | * | * | * | B | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | B
    K | * | * | * | * | * | * | *
  }
  private val chessBoardMovesWithAllies = ChessBoard {
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | X | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | X | * | X | * | *
    * | * | X | * | * | * | X | *
    * | X | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
  }

  it should "avoid the movement over the allied pieces" in {
    val chessGameStatus = ChessGameStatus(chessBoardWithAllies)
    getChessBoardFromMoves(bishopPosition, chessGameStatus) shouldEqual chessBoardMovesWithAllies
  }

  private val chessBoardWithEnemies = ChessBoard {
    * | * | * | * | * | * | * | r
    * | * | p | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | B | * | * | *
    * | * | * | * | * | * | * | *
    * | * | K | * | * | * | n | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
  }
  private val chessBoardMovesWithEnemies = ChessBoard {
    * | * | * | * | * | * | * | X
    * | * | X | * | * | * | X | *
    * | * | * | X | * | X | * | *
    * | * | * | * | * | * | * | *
    * | * | * | X | * | X | * | *
    * | * | * | * | * | * | X | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
  }

  it should "allow to capture enemy pieces" in {
    val chessGameStatus = ChessGameStatus(chessBoardWithEnemies)
    getChessBoardFromMoves(bishopPosition, chessGameStatus) shouldEqual chessBoardMovesWithEnemies
  }
