/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.util.option.OptionExtension.anyToOptionOfAny
import io.github.chess.model.{ChessBoardBuilder, ChessBoard}
import io.github.chess.model.ChessBoardBuilder.*
import io.github.chess.AbstractSpec

/** Test suit for the [[ChessBoardBuilder]]. */
class ChessBoardBuilderDSLSpec extends AbstractSpec:

  "The DSL of a chess board builder" should "provide a working syntax for faster configuration" in {
    val chessBoardBuilder: ChessBoardBuilder = ChessBoardBuilder.configure {
      p | p | p | p | p | p | p | p
      p | p | p | p | p | p | p | p
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      P | P | P | P | P | P | P | P
      P | P | P | P | P | P | P | P
    }
    ChessBoard.Positions
      .map { position => position -> chessBoardBuilder.build.pieces.get(position) }
      .foreach {
        case (position, piece) if Set(Rank._8, Rank._7)(position.rank) =>
          piece shouldBe defined
          piece.foreach(_ shouldEqual Pawn( /*TODO black*/ ))
        case (position, piece) if Set(Rank._1, Rank._2)(position.rank) =>
          piece shouldBe defined
          piece.foreach(_ shouldEqual Pawn( /*TODO white*/ ))
        case (_, piece) => piece shouldNot be(defined)
      }
  }

  it should "provide a syntax for defining empty rows quickly" in {
    val chessBoardBuilder: ChessBoardBuilder = ChessBoardBuilder.configure {
      p | p | * | * | p | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      p | p | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      * | * | * | * | * | * | * | *
      P | P | P | * | * | * | * | *
    }
    // Note: semicolons are actually required BEFORE ** if it is used in a line by itself (** is infix for scala)
    val otherChessBoardBuilder: ChessBoardBuilder = ChessBoardBuilder.configure {
      p | p | * | * | p | **;
      **;
      **;
      p | p | **;
      **;
      **;
      **;
      P | P | P | **;
    }
    // Note: this is more compact and does not require any semicolon
    val yetAnotherChessBoardBuilder: ChessBoardBuilder = ChessBoardBuilder.configure {
      p | p | * | * | p | ** { 3 }
      p | p | ** { 4 }
      P | P | P | **
    }
    chessBoardBuilder.build shouldEqual otherChessBoardBuilder.build
    chessBoardBuilder.build shouldEqual yetAnotherChessBoardBuilder.build
  }
