/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.events.EndTurnEvent
import io.github.chess.model.ChessBoardBuilder.DSL.*
import io.github.chess.model.Team.{BLACK, WHITE}
import io.github.chess.model.moves.Move
import io.github.chess.model.pieces.{Pawn, Piece}
import io.vertx.core.Vertx

/** The trait representing the concept of a Chess Board. */
trait ChessBoard:
  /**
   * Gives access to all the [[Piece]]s that are present on the board.
   * @return the map containing both white and black [[Piece]]s of the board
   */
  def pieces: Map[Position, Piece]

  /**
   * Updates the chess board assigning the specified piece to the specified position.
   * @param position the specified position
   * @param piece the specified piece
   */
  def setPiece(position: Position, piece: Piece): Unit

  /**
   * Updates the chess board removing the piece at the specified position.
   * @param position the specified position
   */
  def removePiece(position: Position): Unit

  /**
   * Updates the chess board applying the specified updates.
   * @param updates a list of updates, each mapping a certain position to a new piece,
   *                or an empty optional if the piece has to be removed from that position
   * @example
   * {{{
   *   chessBoard.update(
   *     Position(A, _5) -> Pawn(),
   *     Position(B, _8) -> None,
   *     Position(E, _3) -> Knight(),
   *   )
   * }}}
   */
  def update(updates: (Position, Option[Piece])*): Unit =
    updates.foreach {
      case (position, Some(piece)) => setPiece(position, piece)
      case (position, _)           => removePiece(position)
    }

  /**
   * Moves a piece if present in from position to a target position.
   * @param from starting [[Position]]
   * @param to target [[Position]]
   */
  def movePiece(from: Position, to: Position): Unit =
    this.pieces.get(from) match
      case Some(value) =>
        removePiece(from)
        setPiece(to, value)
      case None =>

/** Companion object of [[ChessBoard]]. */
object ChessBoard:
  export io.github.chess.model.ChessBoardBuilder.*

  /** The length of the edges of the chess board. */
  val Size: Int = 8

  /** The total number of positions in the chess board. */
  val NumberOfPositions: Int = ChessBoard.Size * ChessBoard.Size

  /** All the possible positions in the chess board. */
  lazy val Positions: Iterable[Position] =
    for
      i <- 0 until ChessBoard.Size
      j <- 0 until ChessBoard.Size
    yield (i, j)

  /** Alias for [[ChessBoard.empty]]. */
  def apply(): ChessBoard = ChessBoard.empty

  /**
   * @param builderConfiguration the context of the specified configuration
   * @return a custom chess board initialized using a builder with the specified configuration
   */
  def apply(builderConfiguration: ChessBoardBuilder ?=> ChessBoardBuilder): ChessBoard =
    ChessBoardBuilder.configure(builderConfiguration).build

  /** @return an empty chess board with no pieces on top of it */
  def empty: ChessBoard = BasicChessBoard()

  /** @return a chess board initialized for a standard game of chess. */
  def standard: ChessBoard = ChessBoard {
    r | n | b | q | k | b | n | r
    p | p | p | p | p | p | p | p
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    * | * | * | * | * | * | * | *
    P | P | P | P | P | P | P | P
    R | N | B | Q | K | B | N | R
  }

  /**
   * @param pieces a map from positions to pieces in the chess board
   * @return a chess board with the specified pieces at the specified positions
   */
  def fromMap(pieces: Map[Position, Piece]): ChessBoard = BasicChessBoard(pieces)

  /** Basic implementation of a chess board. */
  private case class BasicChessBoard(private var _pieces: Map[Position, Piece] = Map.empty)
      extends ChessBoard:
    override def pieces: Map[Position, Piece] = this._pieces
    override def setPiece(position: Position, piece: Piece): Unit =
      this._pieces += position -> piece
    override def removePiece(position: Position): Unit = this._pieces -= position
