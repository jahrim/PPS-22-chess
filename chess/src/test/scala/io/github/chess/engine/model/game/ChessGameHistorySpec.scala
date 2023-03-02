/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.game

import io.github.chess.AbstractSpec
import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.board.Position.given
import io.github.chess.engine.model.game.ChessGameHistory
import io.github.chess.engine.model.moves.Move
import io.github.chess.engine.model.pieces.{Pawn, Piece}

/** Test suite for the [[ChessGameHistory]]. */
class ChessGameHistorySpec extends AbstractSpec:
  private val piece: Piece = Pawn(Team.WHITE)
  private val moves: Seq[Move] =
    Seq(Move((0, 0), (0, 1)), Move((0, 1), (0, 2)), Move((0, 2), (0, 3)))
  private val otherPiece: Piece = Pawn(Team.WHITE)
  private val otherMoves: Seq[Move] =
    Seq(Move((4, 0), (4, 1)), Move((4, 1), (4, 2)), Move((4, 2), (4, 3)))

  "The history of moves in a chess game" should "be initially empty" in {
    ChessGameHistory().all shouldBe empty
  }

  it should "remember the moves after they have been saved in it" in {
    ChessGameHistory().saveAll(moves.map { (piece, _) }).all shouldEqual moves
  }

  it should "remember the moves of different pieces after they have been saved in it" in {
    ChessGameHistory()
      .saveAll(moves.map { (piece, _) })
      .saveAll(otherMoves.map { (otherPiece, _) })
      .all shouldEqual moves :++ otherMoves
  }

  it should "allow to query the moves of a specific piece after they have been saved in it" in {
    ChessGameHistory()
      .saveAll(moves.map { (piece, _) })
      .saveAll(otherMoves.map { (otherPiece, _) })
      .ofPiece(otherPiece) shouldEqual otherMoves
  }
