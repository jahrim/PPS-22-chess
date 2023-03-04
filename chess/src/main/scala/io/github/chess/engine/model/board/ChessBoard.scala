/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.board

import io.github.chess.engine.model.pieces.{Bishop, King, Knight, Pawn, Piece, Queen, Rook}
import io.github.chess.engine.model.game.Team.{BLACK, WHITE}
import ChessBoardBuilder.DSL.*
import io.github.chess.engine.events.PieceMovedEvent
import io.github.chess.engine.model.game.Team
import io.github.chess.engine.model.moves.Move
import io.vertx.core.Vertx

/** The trait representing the concept of a Chess Board. */
trait ChessBoard:
  /**
   * Gives access to all the [[Piece]]s that are present on the board.
   * @return the map containing both white and black [[Piece]]s of the board
   */
  def pieces: Map[Position, Piece]

  /**
   * Gives access to all the [[Piece]]s of the specified team that are present on the board.
   * @param team the specified team
   * @return the map containing only the [[Piece]]s of the board of the specified team
   */
  def pieces(team: Team): Map[Position, Piece] = pieces.filter(_._2.team == team)

  /**
   * Updates the chess board assigning the specified piece to the specified position.
   * @param position the specified position
   * @param piece the specified piece
   * @return a new updated chess board
   */
  def setPiece(position: Position, piece: Piece): ChessBoard

  /**
   * Updates the chess board removing the piece at the specified position.
   * @param position the specified position
   * @return a new updated chess board
   */
  def removePiece(position: Position): ChessBoard

  /**
   * Updates the chess board applying the specified updates.
   * @param updates a list of updates, each mapping a certain position to a new piece,
   *                or an empty optional if the piece has to be removed from that position
   * @return a new updated chess board
   * @example
   * {{{
   *   chessBoard.update(
   *     Position(A, _5) -> Pawn(),
   *     Position(B, _8) -> None,
   *     Position(E, _3) -> Knight(),
   *   )
   * }}}
   */
  def update(updates: (Position, Option[Piece])*): ChessBoard =
    updates.foldLeft(this) {
      case (chessBoard, (position, Some(piece))) => chessBoard.setPiece(position, piece)
      case (chessBoard, (position, _))           => chessBoard.removePiece(position)
    }

  /**
   * Moves a piece if present in from position to a target position.
   * @param from starting [[Position]]
   * @param to target [[Position]]
   * @return a new updated chess board
   */
  def movePiece(from: Position, to: Position): ChessBoard =
    this.pieces.get(from) match
      case Some(value) =>
        this.removePiece(from).setPiece(to, value)
      case None => this

/** Companion object of [[ChessBoard]]. */
object ChessBoard:
  export ChessBoardBuilder.*

  /** The length of the edges of the chess board. */
  val Size: Int = 8

  /** The total number of positions in the chess board. */
  val NumberOfPositions: Int = ChessBoard.Size * ChessBoard.Size

  /** All the possible positions in the chess board. */
  lazy val Positions: Iterable[Position] =
    for
      j <- 0 until ChessBoard.Size
      i <- 0 until ChessBoard.Size
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
  private case class BasicChessBoard(private val _pieces: Map[Position, Piece] = Map.empty)
      extends ChessBoard:
    override def pieces: Map[Position, Piece] = this._pieces

    override def setPiece(position: Position, piece: Piece): ChessBoard =
      BasicChessBoard(this._pieces + (position -> piece))

    override def removePiece(position: Position): ChessBoard =
      BasicChessBoard(this._pieces - position)

    override def toString: String =
      ChessBoard.Positions
        .map(this.pieces.get(_))
        .map(_.map(pieceToString).getOrElse("*"))
        .grouped(ChessBoard.Size)
        .toList
        .reverse
        .map(line => line.mkString(" | "))
        .mkString("\n", "\n", "\n")

    private def pieceToString(piece: Piece): String =
      val unboundRepresentation: (String, String) =
        piece match
          case _: King   => ("K", "k")
          case _: Queen  => ("Q", "q")
          case _: Rook   => ("R", "r")
          case _: Bishop => ("B", "b")
          case _: Knight => ("N", "n")
          case _: Pawn   => ("P", "p")
          case _         => ("X", "x")
      if piece.team == Team.WHITE then unboundRepresentation._1 else unboundRepresentation._2
