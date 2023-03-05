/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.king

import io.github.chess.engine.model.board.ChessBoardBuilder.DSL.*
import io.github.chess.engine.model.board.{ChessBoard, File, Position, Rank}
import io.github.chess.engine.model.game.ChessGameStatus
import io.github.chess.engine.model.rules.AbstractChessRuleSpec

/** Test suite for [[KingRule]]. */
class KingRuleSpec extends AbstractChessRuleSpec:

  private val kingPosition = Position(File.E, Rank._5)

  private val chessBoardOnlyKing = ChessBoard {
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | K | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
  }
  private val chessBoardMovesOnlyKing = ChessBoard {
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | X | X | X | * | *
    * | * | * | X | * | X | * | *
    * | * | * | X | X | X | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
  }

  "The king rule" should "allow the king to move in all the directions stepped by one" in {
    val chessGameStatus = ChessGameStatus(chessBoardOnlyKing)
    getChessBoardFromMoves(kingPosition, chessGameStatus) shouldEqual chessBoardMovesOnlyKing
  }

  private val chessBoardWithAllies = ChessBoard {
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | R | * | B | * | *
    * | * | * | * | K | * | * | *
    * | * | * | * | Q | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
  }
  private val chessBoardMovesWithAllies = ChessBoard {
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | X | * | * | *
    * | * | * | X | * | X | * | *
    * | * | * | X | * | X | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
  }

  it should "avoid the movement over the allied pieces" in {
    val chessGameStatus = ChessGameStatus(chessBoardWithAllies)
    getChessBoardFromMoves(kingPosition, chessGameStatus) shouldEqual chessBoardMovesWithAllies
  }

  private val chessBoardWithEnemies = ChessBoard {
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | R | * | B | * | *
    * | * | * | * | K | * | * | *
    * | * | * | * | p | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
  }
  private val chessBoardMovesWithEnemies = ChessBoard {
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | X | * | * | *
    * | * | * | X | * | X | * | *
    * | * | * | X | X | X | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
  }

  it should "allow to capture enemy pieces" in {
    val chessGameStatus = ChessGameStatus(chessBoardWithEnemies)
    getChessBoardFromMoves(kingPosition, chessGameStatus) shouldEqual chessBoardMovesWithEnemies
  }

  private val castlingPosition = Position.whiteKingPosition
  private val chessBoardForCastling = ChessBoard {
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    R | * | * | * | K | * | * | R
  }
  private val chessBoardMovesForCastling = ChessBoard {
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | X | X | X | * | *
    * | * | X | X | * | X | X | *
  }

  it should "allow to castling" in {
    val chessGameStatus = ChessGameStatus(chessBoardForCastling)
    getChessBoardFromMoves(castlingPosition, chessGameStatus) shouldEqual chessBoardMovesForCastling
  }
