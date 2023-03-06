/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.pawn

import io.github.chess.util.general.GivenExtension.within
import io.github.chess.engine.model.moves.{CaptureMove, Move}
import io.github.chess.engine.model.rules.chess.{ChessRule, RuleShorthands}
import io.github.chess.engine.model.rules.prolog.{BlackPawnCaptureRule, WhitePawnCaptureRule}
import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.game.{ChessGameStatus, Team}
import PawnCaptureMoves.*

/** Finds all moves with which a pawn can capture an adversary piece. */
class PawnCaptureMoves extends ChessRule with RuleShorthands:
  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    within(status) {
      pieceAt(position) match
        case Some(piece) =>
          (piece.team match
            case Team.WHITE => whitePawnCaptureRule
            case Team.BLACK => blackPawnCaptureRule
          ).findPositions(position)
            .map(toPosition =>
              pieceAt(toPosition) match
                case Some(capturedPiece) => CaptureMove(position, toPosition, capturedPiece)
                case None                => Move(position, toPosition)
            )
            .toSet
        case None => Set.empty
    }

/**
 * Object for the Pawn Capture Moves Rule that creates and stores a single instance of the prolog engine
 *  for the two versions of the rule (white and black).
 */
object PawnCaptureMoves:
  private val whitePawnCaptureRule = WhitePawnCaptureRule()
  private val blackPawnCaptureRule = BlackPawnCaptureRule()
