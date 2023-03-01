/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.board

import io.github.chess.util.exception.OutsideBoardException

/** Represents a row of the [[ChessBoard]]. */
enum Rank:

  /** Represents the first row. */
  case _1

  /** Represents the second row. */
  case _2

  /** Represents the third row. */
  case _3

  /** Represents the fourth row. */
  case _4

  /** Represents the fifth row. */
  case _5

  /** Represents the sixth row. */
  case _6

  /** Represents the seventh row. */
  case _7

  /** Represents the eighth row. */
  case _8

  /**
   * Returns the rank upped by one.
   * @return the new rank
   */
  def up(): Rank = this match
    case last: Rank if last.ordinal == Rank.values.length - 1 => throw OutsideBoardException()
    case _                                                    => Rank.fromOrdinal(this.ordinal + 1)

  /**
   * Returns the rank downed by one.
   * @return the new rank
   */
  def down(): Rank = this match
    case first: Rank if first.ordinal == 0 => throw OutsideBoardException()
    case _                                 => Rank.fromOrdinal(this.ordinal - 1)

  override def toString: String = (this.ordinal + 1).toString
