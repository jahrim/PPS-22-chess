/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.queen

import io.github.chess.AbstractSpec
import io.github.chess.model.pieces.{Piece, Queen}
import io.github.chess.model.rules.chess.queen.QueenRule
import io.github.chess.model.ChessBoard.*
import io.github.chess.model.{ChessBoard, ChessGameStatus, File, Position, Rank, Team}
import io.github.chess.util.debug.Logger

/** Test suite for [[Queen]]. */
class QueenRuleSpec extends AbstractSpec:
  val queenRule: QueenRule = QueenRule()
  val queenPosition: Position = Position(File.E, Rank._5)

  "The queen" should "move in all possible directions" in {
    val chessGameStatus: ChessGameStatus = ChessGameStatus(ChessBoard {
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | Q | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
    })
    this.getChessBoardFromMoves(chessGameStatus) shouldEqual ChessBoard {
      * | Q | * | * | Q | * | * | Q
      * | * | Q | * | Q | * | Q | *
      * | * | * | Q | Q | Q | * | *
      Q | Q | Q | Q | * | Q | Q | Q
      * | * | * | Q | Q | Q | * | *
      * | * | Q | * | Q | * | Q | *
      * | Q | * | * | Q | * | * | Q
      Q | * | * | * | Q | * | * | *
    }
  }

  it should "be blocked by pieces of the same team" in {
    val chessGameStatus: ChessGameStatus = ChessGameStatus(ChessBoard {
      * | * | * | * | * | * | * | *
      * | * | N | * | * | * | * | *
      * | * | * | * | * | R | * | *
      * | N | * | * | Q | * | * | *
      * | * | * | K | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | R | * | * | *
      * | * | * | * | * | * | * | *
    })
    this.getChessBoardFromMoves(chessGameStatus) shouldEqual ChessBoard {
      * | * | * | * | Q | * | * | *
      * | * | * | * | Q | * | * | *
      * | * | * | Q | Q | * | * | *
      * | * | Q | Q | * | Q | Q | Q
      * | * | * | * | Q | Q | * | *
      * | * | * | * | Q | * | Q | *
      * | * | * | * | * | * | * | Q
      * | * | * | * | * | * | * | *
    }
  }

  it should "be able to capture pieces of the opposite team" in {
    val chessGameStatus: ChessGameStatus = ChessGameStatus(ChessBoard {
      * | p | * | * | * | * | * | *
      * | * | * | * | p | * | * | *
      * | * | * | * | * | * | * | *
      * | * | K | * | Q | p | * | *
      * | * | * | * | * | * | * | *
      * | * | K | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | p | * | * | *
    })
    this.getChessBoardFromMoves(chessGameStatus) shouldEqual ChessBoard {
      * | Q | * | * | * | * | * | Q
      * | * | Q | * | Q | * | Q | *
      * | * | * | Q | Q | Q | * | *
      * | * | * | Q | * | Q | * | *
      * | * | * | Q | Q | Q | * | *
      * | * | * | * | Q | * | Q | *
      * | * | * | * | Q | * | * | Q
      * | * | * | * | Q | * | * | *
    }
  }

  /**
   * @param chessGameStatus the state of the game required for evaluating the moves available to the queen
   * @return a chess board where each positions that is reachable by the queen is marked with a white queen piece
   */
  private def getChessBoardFromMoves(chessGameStatus: ChessGameStatus): ChessBoard =
    ChessBoard.fromMap {
      queenRule
        .findMoves(queenPosition, chessGameStatus)
        .map { move => move.to -> Queen(Team.WHITE) }
        .toMap[Position, Piece]
    }
