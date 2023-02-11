/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.position

import io.github.chess.model.Position
import io.github.chess.model.File
import io.github.chess.model.Rank

/** Extension for the Position class. */
object PositionExtension:
  extension (self: Position)
    /**
     * Convert this position into a pair of integer coordinates, considering
     * the top-left corner of the chess board as (0,0) and the bottom-right
     * corner of the chess board as (7,7).
     * @return a pair of integer coordinates representing this position in
     *         the chess board
     */
    def toIntCoords: (Int, Int) = (Rank.values.length - self.rank.ordinal - 1, self.file.ordinal)

  extension (self: (Int, Int))
    /** Reverse of [[Position.toIntPair]]. */
    def toPosition: Position =
      Position(File.fromOrdinal(self._1), Rank.fromOrdinal(Rank.values.length - self._2 - 1))
