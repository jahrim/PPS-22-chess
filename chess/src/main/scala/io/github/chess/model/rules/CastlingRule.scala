/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules

import io.github.chess.model.Team.{BLACK, WHITE}
import io.github.chess.model.{
  Bishop,
  ChessGameStatus,
  File,
  King,
  Knight,
  Move,
  Pawn,
  Piece,
  Position,
  Rank,
  Team
}

/**
 * Represents the chess rule that can find the two possible moves involving the [[King]] and the [[Rook]]:
 * available only if they are on their starting positions and no other piece is present between them.
 */
class CastlingRule extends ChessRule:

  // TODO refactor thank to team in piece
  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    var set: Set[Move] = Set.empty
    val currentTeam = status.currentTurn
    val kingPosition = this.kingPosition(currentTeam)
    if position == kingPosition && isKing(position, status) then
      val leftRookPosition = this.leftRookPosition(currentTeam)
      val leftRook = status.chessBoard.pieces(leftRookPosition)
      if checkFirstMoveAndNoOtherPieceBetween(leftRookPosition, leftRook, status, kingPosition)
      then set = set + Move(position, Position(File.C, rankFromTeam(currentTeam)))

      val rightRookPosition = this.rightRookPosition(currentTeam)
      val rightRook = status.chessBoard.pieces(rightRookPosition)
      if checkFirstMoveAndNoOtherPieceBetween(rightRookPosition, rightRook, status, kingPosition)
      then set = set + Move(position, Position(File.G, rankFromTeam(currentTeam)))
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
    status.chessBoard.pieces(position) match
      case _: King => true
      case _       => false

  private def isRook(position: Position, status: ChessGameStatus) =
    status.chessBoard.pieces(position) match
      case _ => true

  private def rankFromTeam(team: Team): Rank = team match
    case WHITE => Rank._1
    case BLACK => Rank._8

  private def checkFirstMoveAndNoOtherPieceBetween(
      position: Position,
      piece: Piece,
      status: ChessGameStatus,
      kingPosition: Position
  ): Boolean =
    isRook(position, status) && status.history.ofPiece(piece).isEmpty && Position
      .findHorizontalBetween(kingPosition, position)
      .forall(!status.chessBoard.pieces.contains(_))
