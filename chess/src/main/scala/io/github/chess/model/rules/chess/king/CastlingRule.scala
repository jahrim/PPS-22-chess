/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.king

import io.github.chess.model.Position
import io.github.chess.model.Team.{BLACK, WHITE}
import io.github.chess.model.moves.CastlingMove
import io.github.chess.model.pieces.{King, Piece}
import io.github.chess.model.rules.chess.{ChessRule, RuleShorthands}
import io.github.chess.model.moves.Move
import io.github.chess.model.{ChessGameStatus, Team, Rank, File}

/**
 * Represents the chess rule that can find the two possible moves involving the [[King]] and the [[Rook]]:
 * available only if they are on their starting positions and no other piece is present between them.
 */
class CastlingRule extends ChessRule with RuleShorthands:

  // TODO refactor thank to team in piece
  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    var set: Set[Move] = Set.empty
    if isFirstMovementOf(position)(using status) then
      val currentTeam = status.currentTurn
      val kingPosition = this.kingPosition(currentTeam)
      if position == kingPosition && isKing(position, status)
      then
        val rank = rankFromTeam(currentTeam)

        val leftRookPosition = this.leftRookPosition(currentTeam)
        if checkFirstMoveAndNoOtherPieceBetween(leftRookPosition, status, kingPosition)
        then
          set = set + CastlingMove(
            position,
            Position(File.C, rank),
            leftRookPosition,
            Position(File.D, rank)
          )

        val rightRookPosition = this.rightRookPosition(currentTeam)
        if checkFirstMoveAndNoOtherPieceBetween(rightRookPosition, status, kingPosition)
        then
          set = set + CastlingMove(
            position,
            Position(File.G, rank),
            rightRookPosition,
            Position(File.F, rank)
          )
    end if
    set

  private def kingPosition(team: Team): Position = team match
    case WHITE => Position(File.E, Rank._1)
    case BLACK => Position(File.E, Rank._8)

  private def leftRookPosition(team: Team): Position = team match
    case WHITE => Position(File.A, Rank._1)
    case BLACK => Position(File.A, Rank._8)

  private def rightRookPosition(team: Team): Position = team match
    case WHITE => Position(File.H, Rank._1)
    case BLACK => Position(File.H, Rank._8)

  private def isKing(position: Position, status: ChessGameStatus) =
    status.chessBoard.pieces.get(position) match
      case Some(_: King) => true
      case _             => false

  private def isRook(position: Position, status: ChessGameStatus) =
    status.chessBoard.pieces.get(position) match
//      case Some(_: Rook) => true
      case Some(_: Piece) => true
      case _              => false

  private def rankFromTeam(team: Team): Rank = team match
    case WHITE => Rank._1
    case BLACK => Rank._8

  private def checkFirstMoveAndNoOtherPieceBetween(
      position: Position,
      status: ChessGameStatus,
      kingPosition: Position
  ): Boolean =
    val piece = status.chessBoard.pieces.get(position)
    piece match
      case Some(value) =>
        isRook(position, status) && status.history.ofPiece(value).isEmpty && Position
          .findHorizontalBetween(kingPosition, position)
          .forall(!status.chessBoard.pieces.contains(_))
      case None => false
