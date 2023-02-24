/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.util.option.OptionExtension.anyToOptionOfAny
import io.github.chess.ports.ChessPort
import io.github.chess.events.{Event, PieceMovedEvent, TimeEndedEvent, TimePassedEvent}
import io.github.chess.model.Team.{BLACK, WHITE}
import io.github.chess.model.configuration.{GameConfiguration, Player, TimeConstraint}
import io.github.chess.model.moves.{CastlingMove, EnPassantMove, Move}
import io.github.chess.model.pieces.Piece
import io.github.chess.util.exception.GameNotInitializedException
import io.github.chess.util.general.Timer
import io.vertx.core.eventbus.Message
import io.vertx.core.{Handler, Vertx}

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * A game of chess.
 * @param vertx the vertx where this game will be deployed
 */
class ChessGame(private val vertx: Vertx) extends ChessPort:
  @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var state: ChessGameStatus = _
  @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var timer: Timer = _

  override def getState: Future[ChessGameStatus] =
    if this.state.isDefined
    then Future { this.state }
    else Future.failed(GameNotInitializedException())

  override def startGame(gameConfiguration: GameConfiguration): Future[Unit] =
    Future {
      this.state = ChessGameStatus(gameConfiguration = gameConfiguration)
      gameConfiguration.timeConstraint match
        case TimeConstraint.NoLimit =>
        case TimeConstraint.MoveLimit =>
          this.timer = Timer(
            gameConfiguration.timeConstraint.minutes,
            () =>
              this.publishTimePassedEvent()
              if this.timer.ended
              then
                this.timer.stop()
                this.publishTimeEndedEvent()
          )
          this.timer.start()
    }

  override def findMoves(position: Position): Future[Set[Move]] =
    Future {
      findPieceOfCurrentTeam(position) match
        case Some(piece) => piece.rule.findMoves(position, this.state)
        case None        => Set.empty
    }

  override def applyMove(move: Move): Future[Unit] =
    Future {
      state.chessBoard.movePiece(move.from, move.to)
      move match
        case castlingMove: CastlingMove =>
          state.chessBoard
            .movePiece(castlingMove.rookFromPosition, castlingMove.rookToPosition)
        case enPassantMove: EnPassantMove =>
          state.chessBoard.removePiece(enPassantMove.capturedPiecePosition)
        // TODO: add other move types before this clause (i.e. CaptureMove, PromotionMove...)
        case _ =>
      state.chessBoard.pieces.get(move.to) match
        case Some(piece) => state.history.save(piece, move)
        case None        =>

      state.changeTeam()
      publishPieceMovedEvent(move)
      if this.state.gameConfiguration.timeConstraint == TimeConstraint.MoveLimit then
        this.timer.restart()
    }

  override def subscribe[T <: Event](address: String, handler: Handler[Message[T]]): Future[Unit] =
    Future { vertx.eventBus().consumer(address, handler) }

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
      this.currentPlayer,
      state.chessBoard.pieces,
      lastMove
    )

  private def publishTimePassedEvent(): Unit =
    this.vertx
      .eventBus()
      .publish(TimePassedEvent.address(), TimePassedEvent(this.timer.timeRemaining))

  private def publishTimeEndedEvent(): Unit =
    this.vertx
      .eventBus()
      .publish(TimeEndedEvent.address(), TimeEndedEvent(this.currentPlayer))

  private def currentPlayer: Player = this.state.currentTurn match
    case WHITE => this.state.gameConfiguration.whitePlayer
    case BLACK => this.state.gameConfiguration.blackPlayer
