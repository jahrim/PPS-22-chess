/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.pawn

import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.game.Team
import io.github.chess.engine.model.pieces.Pawn

class AvoidAllPiecesSpec extends AbstractPawnSpec:

  "AvoidAllPieces filtering" should "make the base move of the white pawn unavailable, " +
    "if there is any piece on the following cell" in {
      checkIfAvoidsBothPieces(whitePawn)
    }

  it should "make the base move of the black pawn unavailable, " +
    "if there is any piece on the following cell" in {
      checkIfAvoidsBothPieces(blackPawn)
    }

  private def checkIfAvoidsBothPieces(pawnToCheck: Pawn): Unit =
    val pawnInitialPosition: Position = pawnToCheck.team match
      case Team.WHITE => (4, 1)
      case Team.BLACK => (4, 6)
    addPiece(pawnInitialPosition, pawnToCheck)
    for pawn <- Set(whitePawn, blackPawn)
    do
      val nextPosition = pawnToCheck.team match
        case Team.WHITE => pawnInitialPosition.rankUp()
        case Team.BLACK => pawnInitialPosition.rankDown()
      addPiece(nextPosition, pawn)
      PawnMovementRule().findMoves(pawnInitialPosition, status) should have size 0
