/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess

import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.game.ChessGameStatus
import io.github.chess.engine.model.moves.{CaptureMove, Move}

/**
 * Mixin for [[ChessRule]]s, that removes from the set of [[Move]]s all those that don't capture an adversary piece,
 * so all the simple moves and moves that capture allies.
 */
trait OnlyCaptureEnemies extends AvoidAlliesRule with RuleShorthands:

  abstract override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    super
      .findMoves(position, status)
      .filter(_ match
        case _: CaptureMove => true
        case _              => false
      )
