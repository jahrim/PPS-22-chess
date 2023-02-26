/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.pawn

import io.github.chess.model.moves.{CaptureMove, Move}
import io.github.chess.model.rules.chess.{ChessRule, RuleShorthands}
import io.github.chess.model.rules.prolog.{BlackPawnCaptureRule, WhitePawnCaptureRule}
import io.github.chess.model.{ChessGameStatus, Position, Team}
import PawnCaptureMoves.*

/** Finds all moves with which a pawn can capture an adversary piece. */
class PawnCaptureMoves extends ChessRule with RuleShorthands:
  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    status.chessBoard.pieces.get(position) match
      case Some(piece) =>
        (piece.team match
          case Team.WHITE => whitePawnCaptureRule
          case Team.BLACK => blackPawnCaptureRule
        ).findPositions(position)
          .map(toPosition =>
            status.chessBoard.pieces.get(toPosition) match
              case Some(capturedPiece) => CaptureMove(position, toPosition, capturedPiece)
              case None                => Move(position, toPosition)
          )
          .toSet
      case None => Set.empty

/**
 * Object for the Pawn Capture Moves Rule that creates and stores a single instance of the prolog engine
 *  for the two versions of the rule (white and black).
 */
object PawnCaptureMoves:
  private val whitePawnCaptureRule = WhitePawnCaptureRule()
  private val blackPawnCaptureRule = BlackPawnCaptureRule()
