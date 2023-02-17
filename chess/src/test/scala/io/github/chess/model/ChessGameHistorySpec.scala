/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.{AbstractSpec, model}
import io.github.chess.model.ChessGameHistory
import io.github.chess.model.Position
import io.github.chess.model.Position.given
import io.github.chess.model.moves.Move
import io.github.chess.model.pieces.{Pawn, Piece}

/** Test suite for the [[ChessGameHistory]]. */
class ChessGameHistorySpec extends AbstractSpec:
  private val piece: Piece = Pawn()
  private val moves: Seq[Move] =
    Seq(
      model.moves.Move((0, 0), (0, 1)),
      model.moves.Move((0, 1), (0, 2)),
      model.moves.Move((0, 2), (0, 3))
    )
  private val otherPiece: Piece = Pawn()
  private val otherMoves: Seq[Move] =
    Seq(
      model.moves.Move((4, 0), (4, 1)),
      model.moves.Move((4, 1), (4, 2)),
      model.moves.Move((4, 2), (4, 3))
    )

  "The history of moves in a chess game" should "be initially empty" in {
    val gameHistory: ChessGameHistory = ChessGameHistory()
    gameHistory.all shouldBe empty
  }

  it should "remember the moves after they have been saved in it" in {
    val gameHistory: ChessGameHistory = ChessGameHistory()
    moves.foreach { move => gameHistory.save(piece, move) }
    gameHistory.all shouldEqual moves
  }

  it should "remember the moves of different pieces after they have been saved in it" in {
    val gameHistory: ChessGameHistory = ChessGameHistory()
    moves.foreach { move => gameHistory.save(piece, move) }
    otherMoves.foreach { move => gameHistory.save(otherPiece, move) }
    gameHistory.all shouldEqual moves :++ otherMoves
  }

  it should "allow to query the moves of a specific piece after they have been saved in it" in {
    val gameHistory: ChessGameHistory = ChessGameHistory()
    moves.foreach { move => gameHistory.save(piece, move) }
    otherMoves.foreach { move => gameHistory.save(otherPiece, move) }
    gameHistory.ofPiece(otherPiece) shouldEqual otherMoves
  }
