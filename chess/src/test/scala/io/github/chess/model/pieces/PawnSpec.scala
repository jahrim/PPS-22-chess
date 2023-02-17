/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.pieces

import io.github.chess.AbstractSpec
import io.github.chess.model.pieces.Pawn
import io.github.chess.model.{ChessBoard, ChessGameStatus, File, Position, Rank, Team}
import org.scalatest.flatspec.AnyFlatSpec

/** Test suite for [[Pawn]]. */
class PawnSpec extends AbstractSpec:

  private val pawn = Pawn(Team.WHITE)
  private val chessBoard: ChessBoard = ChessBoard.empty
  private val chessGameStatus = ChessGameStatus(chessBoard)

  "A Pawn" should "always give a set of positions not empty, regardless of the chess board" in {
    val position = Position(File.A, Rank._1)
    pawn.rule.findMoves(position, chessGameStatus) shouldNot be(empty)
  }
