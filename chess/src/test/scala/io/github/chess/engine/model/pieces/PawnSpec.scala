/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.pieces

import io.github.chess.AbstractSpec
import io.github.chess.engine.model.board.{ChessBoard, File, Position, Rank}
import io.github.chess.engine.model.game.{ChessGameStatus, Team}
import io.github.chess.engine.model.pieces.Pawn

/** Test suite for [[Pawn]]. */
class PawnSpec extends AbstractSpec:

  private val position = Position(File.A, Rank._2)
  private val pawn = Pawn(Team.WHITE)
  private val chessBoard: ChessBoard = ChessBoard.empty.setPiece(position, pawn)
  private val chessGameStatus = ChessGameStatus(chessBoard)

  "A Pawn" should "always give a set of positions not empty, within an empty board" in {
    pawn.rule.findMoves(position, chessGameStatus) shouldNot be(empty)
  }
