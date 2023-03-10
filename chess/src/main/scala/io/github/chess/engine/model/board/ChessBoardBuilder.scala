/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.board

import io.github.chess.engine.model.game.Team
import io.github.chess.engine.model.pieces.{Bishop, King, Knight, Pawn, Piece, Queen, Rook}
import io.github.chess.util.scala.givens.GivenExtension.within
import io.github.chess.util.scala.exception.Require
import io.github.chess.util.scala.option.OptionExtension.given

import scala.annotation.targetName

/** Builder of a chess board. */
class ChessBoardBuilder:
  private var chessBoard: ChessBoard = ChessBoard.empty
  private var indexOfNextCell = ChessBoardBuilder.MinIndex

  /**
   * Place the specified piece in the next cell of the chess board. <br\>
   * The chess board is built progressively from the top-left corner to the bottom-right corner,
   * first from left to right and then from top to bottom. <br\>
   * Subsequent calls to this method will automatically consider the next cell to fill.
   * @param piece the specified piece. If the piece is an empty optional, the cell will remain empty
   * @return this
   * @throws IllegalStateException if this method is called when all the cells of the chess board have already been set
   */
  def setNextCell(piece: Option[Piece]): this.type =
    Require.state(
      this.indexOfNextCell <= ChessBoardBuilder.MaxIndex,
      s"Tried to set the ${this.indexOfNextCell + 1}th position out of ${ChessBoard.NumberOfPositions} positions. "
    )
    this.chessBoard = this.chessBoard.update(this.nextPosition -> piece)
    this.indexOfNextCell += 1
    this

  /** Alias for [[ChessBoardBuilder.setNextCell]]. */
  @targetName("setNextCellAlias")
  def +(piece: Option[Piece]): this.type = this.setNextCell(piece)

  /**
   * Skip the next cells of the current row. Can be repeated multiple times.
   * @param repeats the number of rows to skip, including the current row
   * @return this
   */
  def nextRow(repeats: Int = 1): this.type = repeats match
    case 0 => this
    case n =>
      this.indexOfNextCell += ChessBoard.Size - this.indexOfNextCell % ChessBoard.Size
      nextRow(n - 1)

  /** Alias for [[ChessBoardBuilder.nextRow]]. */
  @targetName("nextRowAlias")
  def -(repeats: Int = 1): this.type = nextRow(repeats)

  /** @return the position of the next cell to fill */
  private def nextPosition: Position = (
    this.indexOfNextCell % ChessBoard.Size,
    ChessBoard.Size - 1 - this.indexOfNextCell / ChessBoard.Size
  )

  /**
   * @return the chess board initialized by this builder
   * @note all the cells that are not filled before the call to this method will be considered
   *       to be empty
   */
  def build: ChessBoard = this.chessBoard

/** Companion object of [[ChessBoardBuilder]]. */
object ChessBoardBuilder:
  export ChessBoardBuilder.DSL.*

  /** The starting index of a [[ChessBoardBuilder]]. */
  private val MinIndex = 0

  /** The last index of a [[ChessBoardBuilder]]. */
  private val MaxIndex = ChessBoard.NumberOfPositions - 1

  /**
   * @param configuration the context of the specified configuration
   * @return a builder configured with the specified configuration
   */
  def configure(configuration: ChessBoardBuilder ?=> ChessBoardBuilder): ChessBoardBuilder =
    within(ChessBoardBuilder()) { configuration }

  /** A DSL definition for a [[ChessBoardBuilder]]. */
  object DSL:
    /** A white piece placeholder. */
    def X(using b: ChessBoardBuilder): ChessBoardBuilder = b + Piece(Team.WHITE)

    /** A white pawn. */
    def P(using b: ChessBoardBuilder): ChessBoardBuilder = b + Pawn(Team.WHITE)

    /** A white knight. */
    def N(using b: ChessBoardBuilder): ChessBoardBuilder = b + Knight(Team.WHITE)

    /** A white bishop. */
    def B(using b: ChessBoardBuilder): ChessBoardBuilder = b + Bishop(Team.WHITE)

    /** A white rook. */
    def R(using b: ChessBoardBuilder): ChessBoardBuilder = b + Rook(Team.WHITE)

    /** A white queen. */
    def Q(using b: ChessBoardBuilder): ChessBoardBuilder = b + Queen(Team.WHITE)

    /** A white king. */
    def K(using b: ChessBoardBuilder): ChessBoardBuilder = b + King(Team.WHITE)

    /** A black piece placeholder. */
    def x(using b: ChessBoardBuilder): ChessBoardBuilder = b + Piece(Team.BLACK)

    /** A black pawn. */
    def p(using b: ChessBoardBuilder): ChessBoardBuilder = b + Pawn(Team.BLACK)

    /** A black knight. */
    def n(using b: ChessBoardBuilder): ChessBoardBuilder = b + Knight(Team.BLACK)

    /** A black bishop. */
    def b(using b: ChessBoardBuilder): ChessBoardBuilder = b + Bishop(Team.BLACK)

    /** A black rook. */
    def r(using b: ChessBoardBuilder): ChessBoardBuilder = b + Rook(Team.BLACK)

    /** A black queen. */
    def q(using b: ChessBoardBuilder): ChessBoardBuilder = b + Queen(Team.BLACK)

    /** A black king. */
    def k(using b: ChessBoardBuilder): ChessBoardBuilder = b + King(Team.BLACK)

    /** An empty cell in the chess board. */
    @targetName("emptyCell")
    def *(using b: ChessBoardBuilder): ChessBoardBuilder = b + None

    /** Skip the next cells of the row, making them empty. */
    @targetName("nextRow")
    def **(using b: ChessBoardBuilder): ChessBoardBuilder = **()

    /**
     * Skip the next cells of the row. Can be repeated multiple times.
     * @param repeats the number of rows to skip, including this row
     */
    @targetName("nextRowRepeated")
    def **(repeats: Int = 1)(using b: ChessBoardBuilder): ChessBoardBuilder = b - repeats

    extension (self: ChessBoardBuilder)
      /** DSL separator for chess pieces. */
      @targetName("separator") def |(b: ChessBoardBuilder): ChessBoardBuilder = b
