/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.situations

import io.github.chess.model.{ChessBoard, ChessGameStatus}

/**
 * The "stalemate" situation is when the current player cannot move any
 * pieces, but there's no checkmate.
 */
class Stalemate extends ChessBoardSituation:
  override def checkOn(state: ChessGameStatus): Boolean = ???
