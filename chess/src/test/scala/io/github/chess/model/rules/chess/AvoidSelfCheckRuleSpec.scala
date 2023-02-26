/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess

import io.github.chess.model.{ChessBoard, ChessGameStatus, Position, Team}
import io.github.chess.model.ChessBoard.*
import io.github.chess.AbstractSpec
import io.github.chess.model.pieces.Pawn
import io.github.chess.model.rules.AbstractRuleSpec

/** Test suit for the [[ChessBoardBuilder]]. */
class AvoidSelfCheckRuleSpec extends AbstractRuleSpec:

  val pawnPosition: Position = (1, 6)
  "The avoid self check mixin" should "forbid the pawn to make moves that would put or keep the current player in check" in {
    val chessGameStatus: ChessGameStatus = ChessGameStatus(
      ChessBoard {
        k | * | * | **
        * | p | * | **
        * | * | B | **
      },
      initialTurn = Team.BLACK
    )
    getChessBoardFromMoves(pawnPosition, chessGameStatus) shouldEqual ChessBoard {
      * | * | * | **
      * | * | * | **
      * | * | X | **
    }
  }

  val knightPosition: Position = (1, 6)
  it should "forbid the knight to make moves that would put or keep the current player in check" in {
    val chessGameStatus: ChessGameStatus = ChessGameStatus(
      ChessBoard {
        k | * | * | R | **
        * | n | * | * | **
        * | * | * | * | **
      },
      initialTurn = Team.BLACK
    )
    getChessBoardFromMoves(knightPosition, chessGameStatus) shouldEqual ChessBoard {
      * | * | * | X | **
      * | * | * | * | **
      * | * | * | * | **
    }
  }

  val bishopPosition: Position = (1, 6)
  it should "forbid the bishop to make moves that would put or keep the current player in check" in {
    val chessGameStatus: ChessGameStatus = ChessGameStatus(
      ChessBoard {
        k | * | * | R | **
        * | b | * | * | **
        * | * | * | * | **
      },
      initialTurn = Team.BLACK
    )
    getChessBoardFromMoves(bishopPosition, chessGameStatus) shouldEqual ChessBoard {
      * | * | X | * | **
      * | * | * | * | **
      * | * | * | * | **
    }
  }

  val rookPosition: Position = (1, 7)
  it should "forbid the rook to make moves that would put or keep the current player in check" in {
    val chessGameStatus: ChessGameStatus = ChessGameStatus(
      ChessBoard {
        k | r | * | R | **
        * | * | * | * | **
        * | * | * | * | **
      },
      initialTurn = Team.BLACK
    )
    getChessBoardFromMoves(rookPosition, chessGameStatus) shouldEqual ChessBoard {
      * | * | X | X | **
      * | * | * | * | **
      * | * | * | * | **
    }
  }

  val queenPosition: Position = (1, 6)
  it should "forbid the queen to make moves that would put or keep the current player in check" in {
    val chessGameStatus: ChessGameStatus = ChessGameStatus(
      ChessBoard {
        k | * | * | * | **
        * | q | * | * | **
        * | * | * | * | **
        * | * | * | Q | **
      },
      initialTurn = Team.BLACK
    )
    getChessBoardFromMoves(queenPosition, chessGameStatus) shouldEqual ChessBoard {
      * | * | * | * | **
      * | * | * | * | **
      * | * | X | * | **
      * | * | * | X | **
    }
  }

  val kingPosition: Position = (1, 6)
  it should "forbid the king to make moves that would put or keep the current player in check" in {
    val chessGameStatus: ChessGameStatus = ChessGameStatus(
      ChessBoard {
        * | * | * | * | **
        * | k | * | * | **
        R | * | * | * | **
        B | * | * | * | **
      },
      initialTurn = Team.BLACK
    )
    getChessBoardFromMoves(kingPosition, chessGameStatus) shouldEqual ChessBoard {
      * | X | X | * | **
      * | * | * | * | **
      X | * | * | * | **
      * | * | * | * | **
    }
  }
