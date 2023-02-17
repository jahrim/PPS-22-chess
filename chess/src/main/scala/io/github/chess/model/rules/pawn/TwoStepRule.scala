/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.pawn

import io.github.chess.model.rules.ChessRule
import io.github.chess.model.{ChessGameStatus, Move, Position, Team}

/** Implementation of a chess rule that makes move a piece two positions forward in the column. */
class TwoStepRule extends ChessRule:

  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    Set(
      Move(
        position,
        status.currentTurn match
          case Team.WHITE => position.rankUp().rankUp()
          case Team.BLACK => position.rankDown().rankDown()
      )
    )
