/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

/**
 * Represents the position contained in the chess board,
 * identified by the the combination of a [[File]] and a [[Rank]].
 */
trait Position:

  /**
   * Returns the [[File]] of the position.
   * @return the [[File]]
   */
  def file: File

  /**
   * Returns the [[Rank]] of the position.
   * @return the [[Rank]]
   */
  def rank: Rank

  /**
   * Returns a position advanced by one [[Rank]] regard to this position.
   * @return a new Position
   */
  def rankUp(): Position

  /**
   * Returns a position advanced by one [[Rank]] regard to this position.
   * @return a new Position
   */
  def rankDown(): Position

  /**
   * Returns a position forwarded by one [[File]] regard to this position.
   * @return a new Position
   */
  def fileForward(): Position

  /**
   * Returns a position backed off by one [[File]] regard to this position.
   * @return a new Position
   */
  def fileBackward(): Position

/** Factory for [[Position]] instances. */
object Position:

  /**
   * Convert this position into a pair of integer coordinates, considering
   * the top-left corner of the chess board as (0,0) and the bottom-right
   * corner of the chess board as (7,7).
   *
   * @return a pair of integer coordinates representing this position in
   *         the chess board
   */
  given positionToCoords: Conversion[Position, (Int, Int)] = position =>
    (position.file.ordinal, position.rank.ordinal)

  /** Reverse of [[given_Conversion_Position_Int_Int]]. */
  given coordsToPosition: Conversion[(Int, Int), Position] = chessCoords =>
    Position(
      File.fromOrdinal(chessCoords._1),
      Rank.fromOrdinal(chessCoords._2)
    )

  /**
   * Finds all the horizontal positions between 2 specific positions.
   * @param p1 position 1
   * @param p2 position 2
   * @return [[Seq]] of all the horizontal position between them
   */
  def findHorizontalBetween(p1: Position, p2: Position): Seq[Position] =
    if p1.rank == p2.rank then
      val (bigger, smaller) =
        if (p1.file.ordinal > p2.file.ordinal)
          (p1.file.ordinal, p2.file.ordinal)
        else
          (p2.file.ordinal, p1.file.ordinal)
      for (value <- (smaller + 1) until bigger)
        yield Position(File.fromOrdinal(value), p1.rank)
    else Seq.empty

  /**
   * Creates a position, using its file and its rank.
   * @param file [[File]] of the position
   * @param rank [[Rank]] of the position
   * @return a new [[Position]]
   */
  def apply(file: File, rank: Rank): Position = SimplePosition(file, rank)
  private case class SimplePosition(override val file: File, override val rank: Rank)
      extends Position:

    override def rankUp(): Position = Position(file, rank.up())

    override def rankDown(): Position = Position(file, rank.down())

    override def fileForward(): Position = Position(file.forward(), rank)

    override def fileBackward(): Position = Position(file.backward(), rank)

    override def toString: String = s"${this.file}${this.rank}"
