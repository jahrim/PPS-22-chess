/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess

import io.github.chess.model.moves.Move
import io.github.chess.model.{ChessGameStatus, Position}

trait ChessRule:

  def findMoves(position: Position, status: ChessGameStatus): Set[Move]
