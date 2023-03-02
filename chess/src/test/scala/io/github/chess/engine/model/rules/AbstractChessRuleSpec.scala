/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules

import io.github.chess.AbstractSpec
import io.github.chess.engine.model.board.{ChessBoard, Position}
import io.github.chess.engine.model.game.{ChessGameStatus, Team}
import io.github.chess.engine.model.moves.CaptureMove
import io.github.chess.engine.model.pieces.Piece
import io.github.chess.engine.model.rules.chess.ChessRule

/** A test for a chess rule. */
abstract class AbstractChessRuleSpec extends AbstractSpec:
  /**
   * @param position the specified position
   * @param status   the specified state of the game
   * @param marker   the specified marker (default: white piece placeholder)
   * @return a new chess board where the moves of the piece at the specified position
   *         in the specified state of the game are marked with the specified marker.
   */
  def getChessBoardFromMoves(
      position: Position,
      status: ChessGameStatus,
      marker: Piece = Piece()
  ): ChessBoard =
    status.chessBoard.pieces.get(position).map { piece =>
      ChessBoard.fromMap {
        piece.rule
          .findMoves(position, status)
          .map { _.to -> marker }
          .toMap[Position, Piece]
      }
    } getOrElse ChessBoard.empty
