/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.rook

import io.github.chess.AbstractSpec
import io.github.chess.model.ChessBoard.*
import io.github.chess.model.{ChessBoard, ChessGameStatus, File, Position, Rank, Team}
import io.github.chess.model.pieces.{Piece, Rook}
import io.github.chess.model.rules.AbstractRuleSpec
import io.github.chess.model.rules.chess.rook.RookRule

/** Test suite for [[Rook]]. */
class RookRuleSpec extends AbstractRuleSpec:
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
    getChessBoardFromMoves(rookRule, rookPosition, chessGameStatus) shouldEqual ChessBoard {
      * | * | * | * | X | * | * | *
      * | * | * | * | X | * | * | *
      * | * | * | * | X | * | * | *
      X | X | X | X | * | X | X | X
      * | * | * | * | X | * | * | *
      * | * | * | * | X | * | * | *
      * | * | * | * | X | * | * | *
      * | * | * | * | X | * | * | *
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
    getChessBoardFromMoves(rookRule, rookPosition, chessGameStatus) shouldEqual ChessBoard {
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | X | * | * | *
      * | * | * | X | * | X | X | X
      * | * | * | * | X | * | * | *
      * | * | * | * | X | * | * | *
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
    getChessBoardFromMoves(rookRule, rookPosition, chessGameStatus) shouldEqual ChessBoard {
      * | * | * | * | * | * | * | *
      * | * | * | * | X | * | * | *
      * | * | * | * | X | * | * | *
      X | X | X | X | * | X | * | *
      * | * | * | * | X | * | * | *
      * | * | * | * | X | * | * | *
      * | * | * | * | X | * | * | *
      * | * | * | * | X | * | * | *
    }
  }
