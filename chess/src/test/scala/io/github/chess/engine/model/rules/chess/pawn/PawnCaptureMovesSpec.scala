/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.pawn

import io.github.chess.model.Team
import io.github.chess.model.pieces.Pawn

/** Test suit for the rule that finds the possible capture moves of a [[io.github.chess.model.pieces.Pawn]]. */
class PawnCaptureMovesSpec extends AbstractPawnSpec:

  "PawnCaptureMoves rule" should "always find at least one move for white pawns" in {
    testAllPositionsCaptureFor(whitePawn)
  }

  it should "always find at least one move for black pawns" in {
    testAllPositionsCaptureFor(blackPawn)
  }

  private def testAllPositionsCaptureFor(pawn: Pawn): Unit =
    pawnPositions.foreach(pawnPosition => {
      addPiece(pawnPosition, pawn)
      val (capturingPositionLeft, capturingPositionRight) = pawn.team match
        case Team.WHITE =>
          (pawnPosition.fileBackward().rankUp(), pawnPosition.fileForward().rankUp())
        case Team.BLACK =>
          (pawnPosition.fileBackward().rankDown(), pawnPosition.fileForward().rankDown())

      val foundMoves = PawnCaptureMoves().findMoves(pawnPosition, status)
      foundMoves should not be empty
      foundMoves.map(_.to) should contain atLeastOneOf (
        capturingPositionLeft,
        capturingPositionRight
      )
      cleanBoard()
    })
