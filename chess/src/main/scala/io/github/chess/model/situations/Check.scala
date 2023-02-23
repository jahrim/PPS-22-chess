package io.github.chess.model.situations
import io.github.chess.model.ChessGameStatus

/**
 * The "check" situation in a chess game is when the king can be captured
 * by the opponent in the next turn.
 */
class Check extends ChessBoardSituation:
  override def checkOn(state: ChessGameStatus): Boolean = ???
