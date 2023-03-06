/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.king

import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.game.{ChessGameStatus, Team}
import io.github.chess.engine.model.moves.{CastlingMove, Move}
import io.github.chess.engine.model.game.Team.{BLACK, WHITE}
import io.github.chess.engine.model.pieces.{King, Piece, Rook}
import io.github.chess.engine.model.rules.chess.{ChessRule, RuleShorthands}

/**
 * Represents the chess rule that can find the two possible moves involving the [[King]] and the [[Rook]]:
 * available only if they are on their starting positions and no other piece is present between them.
 */
class CastlingRule extends ChessRule with RuleShorthands:

  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    val currentTeam = status.currentTurn
    val kingPosition = this.kingPosition(currentTeam)
    if position == kingPosition && isFirstMoveOf(classOf[King], position, status)
    then
      val positionsBetweenKingAnd = Position.horizontalPositionsBetween(kingPosition)

      val westRookPosition = this.westRookPosition(currentTeam)
      val eastRookPosition = this.eastRookPosition(currentTeam)
      checkAndCreateMove(kingPosition, positionsBetweenKingAnd, westRookPosition, status) ++
        checkAndCreateMove(kingPosition, positionsBetweenKingAnd, eastRookPosition, status)
    else Set.empty

  private def kingPosition(team: Team): Position = team match
    case WHITE => Position.whiteKingPosition
    case BLACK => Position.blackKingPosition

  private def westRookPosition(team: Team): Position = team match
    case WHITE => Position.westWhiteRookPosition
    case BLACK => Position.westBlackRookPosition

  private def eastRookPosition(team: Team): Position = team match
    case WHITE => Position.eastWhiteRookPosition
    case BLACK => Position.eastBlackRookPosition

  private def isFirstMoveOf[P <: Piece](
      pieceClass: Class[P],
      position: Position,
      status: ChessGameStatus
  ): Boolean =
    status.chessBoard.pieces.get(position) match
      case Some(piece) if piece.getClass == pieceClass && status.history.ofPiece(piece).isEmpty =>
        true
      case _ => false

  private def canCastling(
      betweenKingAnd: Position => Seq[Position],
      rookPosition: Position,
      status: ChessGameStatus
  ): Boolean =
    isFirstMoveOf(classOf[Rook], rookPosition, status) &&
      betweenKingAnd(rookPosition).forall(!status.chessBoard.pieces.contains(_))

  private def castlingPositions(rookPosition: Position): (Position, Position) =
    rookPosition match
      case Position.westWhiteRookPosition | Position.westBlackRookPosition =>
        (
          Position.westCastlingKingPosition(rookPosition.rank),
          Position.westCastlingRookPosition(rookPosition.rank)
        )
      case Position.eastWhiteRookPosition | Position.eastBlackRookPosition =>
        (
          Position.eastCastlingKingPosition(rookPosition.rank),
          Position.eastCastlingRookPosition(rookPosition.rank)
        )

  private def createCastlingMove(rookPosition: Position, kingPosition: Position): CastlingMove =
    val (castlingKingPosition, castlingRookPosition) = castlingPositions(rookPosition)
    CastlingMove(
      kingPosition,
      castlingKingPosition,
      rookPosition,
      castlingRookPosition
    )

  private def checkAndCreateMove(
      kingPosition: Position,
      positionsBetweenKingAnd: Position => Seq[Position],
      westRookPosition: Position,
      status: ChessGameStatus
  ): Set[CastlingMove] =
    if canCastling(positionsBetweenKingAnd, westRookPosition, status)
    then Set(createCastlingMove(westRookPosition, kingPosition))
    else Set.empty
