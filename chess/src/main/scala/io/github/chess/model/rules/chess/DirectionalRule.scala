/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess

import io.github.chess.model.{ChessGameStatus, Position}
import io.github.chess.model.moves.{CaptureMove, Move}
import io.github.chess.model.pieces.Piece
import io.github.chess.model.rules.prolog.PrologRule
import io.github.chess.util.general.GivenExtension.within

/** A rule that performs an analysis towards a certain direction. */
trait DirectionalRule extends RuleShorthands:
  /**
   * Analyzes the specified direction from the current position in the current state of the game.
   *
   * @param currentPosition the current position
   * @param status          the current state of the game
   * @param direction       the specified direction
   * @return a set of all the possible moves from the current position
   */
  protected def analyseDirection(
      currentPosition: Position,
      status: ChessGameStatus,
      direction: PrologRule
  ): Set[Move] =
    within(status) {
      within(currentPosition) {
        val (freePositions, obstructedPositions) =
          direction.findPositions(currentPosition).span(!occupied(_, _))
        var moves = freePositions.map(Move(currentPosition, _))
        obstructedPositions.headOption foreach { obstructingPosition =>
          pieceAt(obstructingPosition) foreach { obstructingPiece =>
            pieceAt(currentPosition) foreach { currentPiece =>
              if currentPiece.team != obstructingPiece.team then
                moves = moves :+ CaptureMove(currentPosition, obstructingPosition, obstructingPiece)
            }
          }
        }
        moves.toSet
      }
    }
    // TODO: search for a better solution that considers captures like the one above,
    //       but much cleaner like the one below?
    // rules.flatMap(
    //  _.findPositions(position)
    //    .takeWhile((x, y) => !status.chessBoard.pieces.contains((x, y)))
    //    .map(moves.Move(position, _))
    //    .toSet
    // )
