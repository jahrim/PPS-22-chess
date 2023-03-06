/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.pawn

import io.github.chess.engine.model.board.{Position, Rank}
import io.github.chess.engine.model.game.{ChessGameStatus, Team}
import io.github.chess.engine.model.moves.Move
import io.github.chess.engine.model.rules.chess.{ChessRule, RuleShorthands}

/** Implementation of a chess rule that makes move a piece one step forward in the column. */
class SingleStepRule extends ChessRule with RuleShorthands:

  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    pieceAt(position)(using status) match
      case Some(piece) =>
        piece.team match
          case Team.WHITE if position.rank != Rank._8 => Set(Move(position, position.rankUp()))
          case Team.BLACK if position.rank != Rank._1 => Set(Move(position, position.rankDown()))
          case _                                      => Set.empty
      case None => Set.empty
