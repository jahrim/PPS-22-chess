/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules

import io.github.chess.model.{ChessGameStatus, Move, Position}

trait ChessRule:

  def findMoves(position: Position, status: ChessGameStatus): Set[Move]
