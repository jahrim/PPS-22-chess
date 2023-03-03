/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.board

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

  /** Ordering position by [[File]]. */
  given orderingByFile: Ordering[Position] = Ordering.by(_.file.ordinal)

  /** Ordering position by [[Rank]]. */
  given orderingByRank: Ordering[Position] = Ordering.by(_.rank.ordinal)

  /**
   * Finds all the horizontal positions between 2 specific positions.
   * @param p1 position 1
   * @param p2 position 2
   * @return [[Seq]] of all the horizontal position between them
   */
  def findHorizontalBetween(p1: Position, p2: Position): Seq[Position] =
    if p1.rank == p2.rank then
      val bigger = orderingByFile.max(p1, p2).file.ordinal
      val smaller = orderingByFile.min(p1, p2).file.ordinal
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

  /**
   * Returns the position associated to a rook executing the west castling move.
   * @param rank [[Rank]] to use
   * @return a new [[Position]]
   */
  def westCastlingRookPosition(rank: Rank): Position =
    Position(File.D, rank)

  /**
   * Returns the position associated to a king executing the west castling move.
   * @param rank [[Rank]] to use
   * @return a new [[Position]]
   */
  def westCastlingKingPosition(rank: Rank): Position =
    Position(File.C, rank)

  /**
   * Returns the position associated to a rook executing the east castling move.
   * @param rank [[Rank]] to use
   * @return a new [[Position]]
   */
  def eastCastlingRookPosition(rank: Rank): Position =
    Position(File.F, rank)

  /**
   * Returns the position associated to a king executing the east castling move.
   * @param rank [[Rank]] to use
   * @return a new [[Position]]
   */
  def eastCastlingKingPosition(rank: Rank): Position =
    Position(File.G, rank)

  /** Constant of the white king position. */
  final val whiteKingPosition: Position = Position(File.E, Rank._1)

  /** Constant of the black king position. */
  final val blackKingPosition: Position = Position(File.E, Rank._8)

  /** Constant of the west white rook position. */
  final val westWhiteRookPosition: Position = Position(File.A, Rank._1)

  /** Constant of the west black rook position. */
  final val westBlackRookPosition: Position = Position(File.A, Rank._8)

  /** Constant of the east white rook position. */
  final val eastWhiteRookPosition: Position = Position(File.H, Rank._1)

  /** Constant of the east black rook position. */
  final val eastBlackRookPosition: Position = Position(File.H, Rank._8)

  private case class SimplePosition(override val file: File, override val rank: Rank)
      extends Position:

    override def rankUp(): Position = Position(file, rank.up())

    override def rankDown(): Position = Position(file, rank.down())

    override def fileForward(): Position = Position(file.forward(), rank)

    override def fileBackward(): Position = Position(file.backward(), rank)

    override def toString: String = s"${this.file}${this.rank}"
