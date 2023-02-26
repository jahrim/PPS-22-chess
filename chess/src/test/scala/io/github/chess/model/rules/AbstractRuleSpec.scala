/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules

import io.github.chess.AbstractSpec
import io.github.chess.model.moves.CaptureMove
import io.github.chess.model.pieces.Piece
import io.github.chess.model.rules.chess.ChessRule
import io.github.chess.model.{ChessBoard, ChessGameStatus, Position, Team}

/** A test for a chess rule. */
abstract class AbstractRuleSpec extends AbstractSpec:
  /**
   * @param rule the specified rule
   * @param position the specified position
   * @param status the specified state of the game
   * @return a new chess board where the moves found by the specified rule at the specified position
   *         in the specified state of the game are marked with white piece placeholders.
   */
  def getChessBoardFromMoves(
      rule: ChessRule,
      position: Position,
      status: ChessGameStatus
  ): ChessBoard =
    ChessBoard.fromMap {
      rule
        .findMoves(position, status)
        .map { _.to -> Piece(Team.WHITE) }
        .toMap[Position, Piece]
    }

  /**
   * @param position the specified position
   * @param status   the specified state of the game
   * @return a new chess board where the moves of the piece at the specified position
   *         in the specified state of the game are marked with white piece placeholders.
   */
  def getChessBoardFromMoves(position: Position, status: ChessGameStatus): ChessBoard =
    status.chessBoard.pieces.get(position).map { piece =>
      getChessBoardFromMoves(piece.rule, position, status)
    } getOrElse ChessBoard.empty
