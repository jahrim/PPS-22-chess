/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.util.option.OptionExtension.anyToOptionOfAny
import io.github.chess.model.ChessBoard
import io.github.chess.model.ChessBoard.*
import io.github.chess.AbstractSpec
import io.github.chess.model.pieces.{Pawn, Piece}

/** Test suit for the [[ChessBoard]]. */
class ChessBoardSpec extends AbstractSpec:
  val piece: Piece = Pawn(Team.WHITE)
  val position: Position = Position(File.A, Rank._1)
  val otherPiece: Piece = Pawn(Team.BLACK)
  val otherPosition: Position = Position(File.A, Rank._8)

  "A chess board" should s"have ${ChessBoard.NumberOfPositions} possible positions" in {
    ChessBoard.Positions.size shouldEqual ChessBoard.NumberOfPositions
  }

  it should s"have a total of ${ChessBoard.Size} columns (files)" in {
    ChessBoard.Positions.groupBy(_.rank).foreach(_._2.size shouldEqual ChessBoard.Size)
  }

  it should s"have a total of ${ChessBoard.Size} rows (ranks)" in {
    ChessBoard.Positions.groupBy(_.file).foreach(_._2.size shouldEqual ChessBoard.Size)
  }

  it should s"have a pretty string representation" in {
    ChessBoard.standard.toString shouldEqual (
      "\n" +
        "r | n | b | q | k | b | n | r\n" +
        "p | p | p | p | p | p | p | p\n" +
        "* | * | * | * | * | * | * | *\n" +
        "* | * | * | * | * | * | * | *\n" +
        "* | * | * | * | * | * | * | *\n" +
        "* | * | * | * | * | * | * | *\n" +
        "P | P | P | P | P | P | P | P\n" +
        "R | N | B | Q | K | B | N | R\n"
    )
  }

  "An empty chess board" should "initially contain no pieces" in {
    ChessBoard.empty.pieces shouldBe empty
  }

  it should "allow to place a piece on it" in {
    ChessBoard.empty.setPiece(position, piece).pieces(position) should be theSameInstanceAs piece
  }

  it should "allow to place more pieces on it" in {
    val chessBoard: ChessBoard =
      ChessBoard.empty
        .setPiece(position, piece)
        .setPiece(otherPosition, otherPiece)
    chessBoard.pieces(position) should be theSameInstanceAs piece
    chessBoard.pieces(otherPosition) should be theSameInstanceAs otherPiece
  }

  it should "allow to replace a piece already present on it" in {
    ChessBoard.empty
      .setPiece(position, piece)
      .update(position -> otherPiece)
      .pieces(position) should be theSameInstanceAs otherPiece
  }

  it should "allow to remove a piece already present on it" in {
    ChessBoard.empty
      .setPiece(position, piece)
      .removePiece(position)
      .pieces
      .get(position) shouldNot be(defined)
  }

  "A chess board at the beginning of a chess game" should "abide to the standard configuration for the chess pieces" in {
    ChessBoard.standard shouldEqual ChessBoard {
      r | n | b | q | k | b | n | r
      p | p | p | p | p | p | p | p
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      P | P | P | P | P | P | P | P
      R | N | B | Q | K | B | N | R
    }
  }
