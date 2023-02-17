/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.util.option.OptionExtension.given
import io.github.chess.model.ChessBoard
import io.github.chess.util.exception.Require

import scala.annotation.targetName

/** Builder of a chess board. */
class ChessBoardBuilder:
  private val chessBoard: ChessBoard = ChessBoard.empty
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
    this.chessBoard.update(this.nextPosition -> piece)
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
  export io.github.chess.model.ChessBoardBuilder.DSL.*

  /** The starting index of a [[ChessBoardBuilder]]. */
  private val MinIndex = 0

  /** The last index of a [[ChessBoardBuilder]]. */
  private val MaxIndex = ChessBoard.NumberOfPositions - 1

  /**
   * @param configuration the context of the specified configuration
   * @return a builder configured with the specified configuration
   */
  def configure(configuration: ChessBoardBuilder ?=> ChessBoardBuilder): ChessBoardBuilder =
    given newBuilder: ChessBoardBuilder = ChessBoardBuilder()
    configuration

  /** A DSL definition for a [[ChessBoardBuilder]]. */
  object DSL:
    // TODO: white pieces
    /** A white pawn. */
    def P(using b: ChessBoardBuilder): ChessBoardBuilder = b + Pawn()

    /** A white knight. */
    def N(using b: ChessBoardBuilder): ChessBoardBuilder = b + Pawn()

    /** A white bishop. */
    def B(using b: ChessBoardBuilder): ChessBoardBuilder = b + Pawn()

    /** A white rook. */
    def R(using b: ChessBoardBuilder): ChessBoardBuilder = b + Pawn()

    /** A white queen. */
    def Q(using b: ChessBoardBuilder): ChessBoardBuilder = b + Pawn()

    /** A white king. */
    def K(using b: ChessBoardBuilder): ChessBoardBuilder = b + Pawn()

    // TODO: black pieces
    /** A black pawn. */
    def p(using b: ChessBoardBuilder): ChessBoardBuilder = b + Pawn()

    /** A black knight. */
    def n(using b: ChessBoardBuilder): ChessBoardBuilder = b + Pawn()

    /** A black bishop. */
    def b(using b: ChessBoardBuilder): ChessBoardBuilder = b + Pawn()

    /** A black rook. */
    def r(using b: ChessBoardBuilder): ChessBoardBuilder = b + Pawn()

    /** A black queen. */
    def q(using b: ChessBoardBuilder): ChessBoardBuilder = b + Pawn()

    /** A black king. */
    def k(using b: ChessBoardBuilder): ChessBoardBuilder = b + Pawn()

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
