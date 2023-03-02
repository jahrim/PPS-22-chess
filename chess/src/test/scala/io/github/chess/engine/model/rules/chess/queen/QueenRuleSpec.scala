/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.queen

import io.github.chess.engine.model.pieces.Queen
import io.github.chess.engine.model.board.ChessBoard.*
import io.github.chess.engine.model.board.{ChessBoard, File, Position, Rank}
import io.github.chess.engine.model.game.ChessGameStatus
import io.github.chess.engine.model.rules.AbstractChessRuleSpec

/** Test suite for [[Queen]]. */
class QueenRuleSpec extends AbstractChessRuleSpec:
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
    getChessBoardFromMoves(queenPosition, chessGameStatus) shouldEqual ChessBoard {
      * | X | * | * | X | * | * | X
      * | * | X | * | X | * | X | *
      * | * | * | X | X | X | * | *
      X | X | X | X | * | X | X | X
      * | * | * | X | X | X | * | *
      * | * | X | * | X | * | X | *
      * | X | * | * | X | * | * | X
      X | * | * | * | X | * | * | *
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
    getChessBoardFromMoves(queenPosition, chessGameStatus) shouldEqual ChessBoard {
      * | * | * | * | X | * | * | *
      * | * | * | * | X | * | * | *
      * | * | * | X | X | * | * | *
      * | * | X | X | * | X | X | X
      * | * | * | * | X | X | * | *
      * | * | * | * | X | * | X | *
      * | * | * | * | * | * | * | X
      * | * | * | * | * | * | * | *
    }
  }

  it should "be able to capture pieces of the opposite team" in {
    val chessGameStatus: ChessGameStatus = ChessGameStatus(ChessBoard {
      * | p | * | * | * | * | * | *
      * | * | p | * | p | * | * | *
      * | * | * | * | * | * | * | *
      * | * | K | * | Q | p | * | *
      * | * | * | * | * | * | * | *
      * | * | K | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | p | * | * | *
    })
    getChessBoardFromMoves(queenPosition, chessGameStatus) shouldEqual ChessBoard {
      * | * | * | * | * | * | * | X
      * | * | X | * | X | * | X | *
      * | * | * | X | X | X | * | *
      * | * | * | X | * | X | * | *
      * | * | * | X | X | X | * | *
      * | * | * | * | X | * | X | *
      * | * | * | * | X | * | * | X
      * | * | * | * | X | * | * | *
    }
  }
