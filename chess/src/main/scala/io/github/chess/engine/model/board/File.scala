/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.board

import io.github.chess.util.exception.OutsideBoardException

/** Represents a column of the [[ChessBoard]]. */
enum File:

  /** Represents the first column. */
  case A

  /** Represents the second column. */
  case B

  /** Represents the third column. */
  case C

  /** Represents the fourth column. */
  case D

  /** Represents the fifth column. */
  case E

  /** Represents the sixth column. */
  case F

  /** Represents the seventh column. */
  case G

  /** Represents the eighth column. */
  case H

  /**
   * Returns the file forwarded by one.
   * @return the new file
   */
  def forward(): File = this match
    case H => throw OutsideBoardException()
    case _ => File.fromOrdinal(this.ordinal + 1)

  /**
   * Returns the file backed off by one.
   * @return the new file
   */
  def backward(): File = this match
    case A => throw OutsideBoardException()
    case _ => File.fromOrdinal(this.ordinal - 1)

  override def toString: String = this.productPrefix.toLowerCase
