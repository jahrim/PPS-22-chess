/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.util.option.OptionExtension.anyToOptionOfAny
import io.github.chess.ports.ChessPort
import io.github.chess.events.{Event, PieceMovedEvent}
import io.github.chess.model.Team
import io.github.chess.model.moves.{CastlingMove, Move}
import io.github.chess.model.pieces.Piece
import io.vertx.core.eventbus.Message
import io.vertx.core.{Future, Handler, Vertx}

/**
 * A game of chess.
 * @param vertx the vertx where this game will be deployed
 */
class ChessGame(private val vertx: Vertx) extends ChessPort:
  private val state: ChessGameStatus = ChessGameStatus()

  override def getState: Future[ChessGameStatus] =
    Future.succeededFuture(this.state)

  override def findMoves(position: Position): Future[Set[Move]] =
    Future.succeededFuture(
      findPieceOfCurrentTeam(position) match
        case Some(piece) => piece.rule.findMoves(position, this.state)
        case None        => Set.empty
    )

  override def applyMove(move: Move): Future[Unit] =
    Future.succeededFuture(
      {
        state.chessBoard.movePiece(move.from, move.to)
        move match
          case castlingMove: CastlingMove =>
            this.state.chessBoard
              .movePiece(castlingMove.rookFromPosition, castlingMove.rookToPosition)
          // TODO: add other move types before this clause (i.e. CaptureMove, PromotionMove...)
          case _ =>
        state.chessBoard.pieces.get(move.from) match
          case Some(piece) => state.history.save(piece, move)
          case None        =>

        state.changeTeam()
        publishPieceMovedEvent(move)
      }
    )

  override def subscribe[T <: Event](address: String, handler: Handler[Message[T]]): Future[Unit] =
    Future.succeededFuture(vertx.eventBus().consumer(address, handler))

  private def findPieceOfCurrentTeam(pos: Position): Option[Piece] = playingTeam.get(pos)

  private def playingTeam: Map[Position, Piece] = this.state.currentTurn match
    case Team.WHITE => this.state.chessBoard.whitePieces
    case Team.BLACK => this.state.chessBoard.blackPieces

  private def publishPieceMovedEvent(lastMove: Move): Unit =
    vertx
      .eventBus()
      .publish(
        PieceMovedEvent.address(),
        createPieceMovedEvent(lastMove)
      )

  private def createPieceMovedEvent(lastMove: Move): PieceMovedEvent =
    PieceMovedEvent(
      state.currentTurn,
      state.chessBoard.pieces,
      lastMove
    )
