package io.github.chess.model.situations

import io.github.chess.model.{ChessBoard, ChessGameStatus}

/**
 * The "stalemate" situation is when the current player cannot move any
 * pieces, but there's no checkmate.
 */
class Stalemate extends ChessBoardSituation:
  override def checkOn(state: ChessGameStatus): Boolean = ???
