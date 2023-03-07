/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.game

import io.github.chess.engine.model.board.{Position, Rank}
import io.github.chess.engine.model.pieces.{King, Pawn}
import io.github.chess.engine.model.game.Team.*

/**
 * Collection of analysis tools for evaluating
 * the situation in a chess game.
 */
object ChessGameAnalyzer:
  export ChessGameSituation.*

  /**
   * @param state           the state of the specified game
   * @param teamPerspective the perspective of the specified team
   * @return the chess board situation of the specified game from the perspective
   *         of the specified team
   */
  def situationOf(
      state: ChessGameStatus,
      teamPerspective: Team
  ): Option[ChessGameSituation] =
    (stale(state, teamPerspective), check(state, teamPerspective)) match
      case (true, false) => Some(Stale)
      case (false, true) => Some(Check)
      case (true, true)  => Some(CheckMate)
      case _ =>
        promotion(state, teamPerspective) match
          case Some(pawnPosition) => Some(Promotion(pawnPosition))
          case _                  => None

  /** As [[situationOf situationOf(state, state.currentTurn)]]. */
  def situationOf(state: ChessGameStatus): Option[ChessGameSituation] =
    situationOf(state, state.currentTurn)

  /**
   * @param state           the state of the specified game
   * @param teamPerspective the perspective of the specified team
   * @return true if the king of the specified team is threatened in the specified game,
   *         false otherwise.
   */
  def check(state: ChessGameStatus, teamPerspective: Team): Boolean =
    state.chessBoard.pieces(teamPerspective.oppositeTeam).exists {
      (opponentPosition, opponentPiece) =>
        opponentPiece.rule
          .findMoves(opponentPosition, state)
          .flatMap { move => state.chessBoard.pieces(teamPerspective).get(move.to) }
          .exists { case _: King => true; case _ => false }
    }

  /** As [[check check(state, state.currentTurn)]]. */
  def check(state: ChessGameStatus): Boolean = check(state, state.currentTurn)

  /**
   * @param state           the state of the specified game
   * @param teamPerspective the perspective of the specified team
   * @return true if the specified team has no available moves in the specified game,
   *         false otherwise.
   */
  def stale(state: ChessGameStatus, teamPerspective: Team): Boolean =
    state.chessBoard
      .pieces(teamPerspective)
      .flatMap((position, piece) => piece.rule.findMoves(position, state))
      .isEmpty

  /** As [[stale stale(state, state.currentTurn)]]. */
  def stale(state: ChessGameStatus): Boolean = stale(state, state.currentTurn)

  /**
   * @param state           the state of the specified game
   * @param teamPerspective the perspective of the specified team
   * @return an optional containing the position of the first pawn that can be promoted
   *         by the specified team, an empty optional if no pawn can be promoted.
   */
  def promotion(state: ChessGameStatus, teamPerspective: Team): Option[Position] =
    val enemyBaseRank = if state.currentTurn == WHITE then Rank._8 else Rank._1
    state.chessBoard
      .pieces(teamPerspective)
      .collectFirst {
        case (position, piece: Pawn) if position.rank == enemyBaseRank => position
      }

  /** As [[promotion promotion(state, state.currentTurn)]]. */
  def promotion(state: ChessGameStatus): Option[Position] = promotion(state, state.currentTurn)

  /** A situation in a chess game. */
  enum ChessGameSituation:
    /** Situation where a king is threatened. */
    case Check

    /** Situation where a player has no available moves. */
    case Stale

    /**
     * Situation where a player has no available moves and
     * his king is threatened.
     */
    case CheckMate

    /** Situation where a player can promote a pawn. */
    case Promotion(pawnPosition: Position)
