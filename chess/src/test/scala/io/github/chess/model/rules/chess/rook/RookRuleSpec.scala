/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.rook

import io.github.chess.AbstractSpec
import io.github.chess.model.ChessBoard.*
import io.github.chess.model.{ChessBoard, ChessGameStatus, Position, File, Rank, Team}
import io.github.chess.model.pieces.{Piece, Rook}
import io.github.chess.model.rules.chess.rook.RookRule

/** Test suite for [[Rook]]. */
class RookRuleSpec extends AbstractSpec:
  val rookRule: RookRule = RookRule()
  val rookPosition: Position = Position(File.E, Rank._5)

  "The rook" should "move in all straight directions" in {
    val chessGameStatus: ChessGameStatus = ChessGameStatus(ChessBoard {
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | R | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
    })
    this.getChessBoardFromMoves(chessGameStatus) shouldEqual ChessBoard {
      * | * | * | * | R | * | * | *
      * | * | * | * | R | * | * | *
      * | * | * | * | R | * | * | *
      R | R | R | R | * | R | R | R
      * | * | * | * | R | * | * | *
      * | * | * | * | R | * | * | *
      * | * | * | * | R | * | * | *
      * | * | * | * | R | * | * | *
    }
  }

  it should "be blocked by pieces of the same team" in {
    val chessGameStatus: ChessGameStatus = ChessGameStatus(ChessBoard {
      * | * | * | * | * | * | * | *
      * | * | * | * | K | * | * | *
      * | * | * | * | * | * | * | *
      * | * | Q | * | R | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | N | * | * | *
      * | * | * | * | * | * | * | *
    })
    this.getChessBoardFromMoves(chessGameStatus) shouldEqual ChessBoard {
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | R | * | * | *
      * | * | * | R | * | R | R | R
      * | * | * | * | R | * | * | *
      * | * | * | * | R | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
    }
  }

  it should "be able to capture pieces of the opposite team" in {
    val chessGameStatus: ChessGameStatus = ChessGameStatus(ChessBoard {
      * | * | * | * | * | * | * | *
      * | * | * | * | p | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | R | p | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | p | * | * | *
    })
    this.getChessBoardFromMoves(chessGameStatus) shouldEqual ChessBoard {
      * | * | * | * | * | * | * | *
      * | * | * | * | R | * | * | *
      * | * | * | * | R | * | * | *
      R | R | R | R | * | R | * | *
      * | * | * | * | R | * | * | *
      * | * | * | * | R | * | * | *
      * | * | * | * | R | * | * | *
      * | * | * | * | R | * | * | *
    }
  }

  /**
   * @param chessGameStatus the state of the game required for evaluating the moves available to the queen
   * @return a chess board where each positions that is reachable by the queen is marked with a rook piece
   */
  private def getChessBoardFromMoves(chessGameStatus: ChessGameStatus): ChessBoard =
    ChessBoard.fromMap {
      this.rookRule
        .findMoves(this.rookPosition, chessGameStatus)
        .map { move => move.to -> Rook(Team.WHITE) }
        .toMap[Position, Piece]
    }
