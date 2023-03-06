/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess

import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.game.ChessGameStatus
import io.github.chess.engine.model.moves.Move

/** Chess rule mixin that checks the presence of a piece in the destination position after finding it. */
trait AvoidAllPiecesRule extends ChessRule with RuleShorthands:

  abstract override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    super.findMoves(position, status).filter(move => !occupied(move.to)(using status))
