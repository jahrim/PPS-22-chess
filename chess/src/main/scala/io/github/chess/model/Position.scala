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
  given Conversion[Position, (Int, Int)] = position =>
    (Rank.values.length - position.rank.ordinal - 1, position.file.ordinal)

  /** Reverse of [[given_Conversion_Position_Int_Int]]. */
  given Conversion[(Int, Int), Position] = chessCoords =>
    Position(
      File.fromOrdinal(chessCoords._1),
      Rank.fromOrdinal(Rank.values.length - chessCoords._2 - 1)
    )

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
