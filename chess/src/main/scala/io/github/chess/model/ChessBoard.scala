/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

trait ChessBoard:

  def pieces: Map[Position, Piece]

  def findMoves(position: Position): Set[Move]

  def move(move: Move): Unit
