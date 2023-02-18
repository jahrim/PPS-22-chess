/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.util.option.OptionExtension.anyToOptionOfAny
import io.github.chess.ports.ChessPort
import io.github.chess.events.Event
import io.github.chess.model.moves.{CastlingMove, Move}
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
      this.state.chessBoard.pieces(position).rule.findMoves(position, this.state)
    )

  override def applyMove(move: Move): Future[Unit] =
    Future.succeededFuture(
      move match
        case castlingMove: CastlingMove =>
          this.state.chessBoard.movePiece(castlingMove.from, castlingMove.to)
          this.state.chessBoard
            .movePiece(castlingMove.rookFromPosition, castlingMove.rookToPosition)
        // TODO: add other move types before this clause (i.e. CaptureMove, PromotionMove...)
        case m: Move =>
          this.state.chessBoard.movePiece(m.from, m.to)
    )

  override def subscribe[T <: Event](address: String, handler: Handler[Message[T]]): Future[Unit] =
    Future.succeededFuture(vertx.eventBus().consumer(address, handler))

//  TODO consider this logic inside the chess game component
//  private case class ChessBoardImpl(private val vertx: Vertx) extends ChessBoard:
//    import scala.collection.immutable.HashMap
//
//    private var whitePieces: Map[Position, Piece] =
//      Map.empty + ((Position(File.A, Rank._2), Pawn()))
//    private var blackPieces: Map[Position, Piece] = HashMap()
//    private var currentlyPlayingTeam = Team.WHITE
//
//    override def pieces: Map[Position, Piece] = this.whitePieces ++ this.blackPieces
//

//
//    override def move(move: Move): Unit =
//      if findPiece(move.from).isDefined then
//        val newTeam = this.applyMove(move)
//        this.currentlyPlayingTeam match
//          case WHITE => this.whitePieces = newTeam
//          case BLACK => this.blackPieces = newTeam
//        // TODO: changePlayingTeam()
//
//    private def playingTeam: Map[Position, Piece] = this.currentlyPlayingTeam match
//      case WHITE => this.whitePieces
//      case BLACK => this.blackPieces
//
//    private def changePlayingTeam(): Unit =
//      this.currentlyPlayingTeam = this.currentlyPlayingTeam.oppositeTeam
//      val endTurnEvent = EndTurnEvent(this.currentlyPlayingTeam)
//      vertx.eventBus().publish(endTurnEvent.address, endTurnEvent)
//
//    private def findPiece(pos: Position): Option[Piece] = playingTeam.get(pos)
//
//    private def applyMove(move: Move): Map[Position, Piece] =
//      val team = playingTeam
//      val pieceToMove = findPiece(move.from)
//      pieceToMove match
//        case Some(piece) => team - move.from + ((move.to, piece))
//        case None        => team
