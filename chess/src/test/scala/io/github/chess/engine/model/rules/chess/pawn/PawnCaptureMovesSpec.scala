/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.pawn

import io.github.chess.engine.model.board.{File, Position}
import io.github.chess.engine.model.game.Team
import io.github.chess.engine.model.pieces.Pawn

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
      val capturingPositions = getCapturingPositions(pawn, pawnPosition)

      val foundMoves = PawnCaptureMoves().findMoves(pawnPosition, status)
      foundMoves shouldNot be(empty)
      foundMoves.map(_.to) should contain theSameElementsAs capturingPositions

      cleanBoard()
    })

  private def getCapturingPositions(pawn: Pawn, position: Position): Set[Position] =
    val followingPosition = pawn.team match
      case Team.WHITE => position.rankUp()
      case Team.BLACK => position.rankDown()

    var capturingPositions: Set[Position] = Set.empty

    if followingPosition.file != File.A
    then capturingPositions = capturingPositions + followingPosition.fileBackward()
    if followingPosition.file != File.H
    then capturingPositions = capturingPositions + followingPosition.fileForward()

    capturingPositions
