/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.util.option.OptionExtension.anyToOptionOfAny
import io.github.chess.ports.ChessPort
import io.github.chess.events.{
  Event,
  PieceMovedEvent,
  PromotingPawnEvent,
  TimeEndedEvent,
  TimePassedEvent
}
import io.github.chess.events.Event.addressOf
import io.github.chess.model.Team.{BLACK, WHITE}
import io.github.chess.model.configuration.{GameConfiguration, Player, TimeConstraint}
import io.github.chess.model.moves.{CastlingMove, EnPassantMove, Move}
import io.github.chess.model.pieces.{Pawn, Piece}
import io.github.chess.util.exception.GameNotInitializedException
import io.github.chess.util.general.Timer
import io.vertx.core.Vertx

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.reflect.ClassTag

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
      this.state = this.state.updateChessBoard {
        val chessBoard = this.state.chessBoard.movePiece(move.from, move.to)
        move match
          case castlingMove: CastlingMove =>
            chessBoard.movePiece(castlingMove.rookFromPosition, castlingMove.rookToPosition)
          case enPassantMove: EnPassantMove =>
            chessBoard.removePiece(enPassantMove.capturedPiecePosition)
          case _ => chessBoard
      }
      this.state = this.state.updateHistory {
        this.state.chessBoard.pieces.get(move.to) match
          case Some(piece) => this.state.history.save(piece, move)
          case None        => this.state.history
      }

      this.findPieceOfCurrentTeam(move.to) match
        case Some(_: Pawn) if move.to.rank == enemyBaseRank() =>
          if this.state.gameConfiguration.timeConstraint == TimeConstraint.MoveLimit then
            this.timer.stop()
          this.publishPromotingPawnEvent(move.to)
        case _ =>
          this.switchTurn()

      publishPieceMovedEvent(move)
    }

  override def promote[P <: Piece](
      pawnPosition: Position,
      promotingPiece: PromotionPiece[P]
  ): Future[Unit] =
    Future {
      this.findPieceOfCurrentTeam(pawnPosition) match
        case Some(_: Pawn) =>
          this.state = this.state.updateChessBoard {
            this.state.chessBoard.setPiece(
              pawnPosition,
              promotingPiece.pieceClass
                .getConstructor(classOf[Team])
                .newInstance(this.state.currentTurn)
            )
          }
          this.switchTurn()
          this.state.history.all.lastOption.foreach { publishPieceMovedEvent }
        case _ =>
    }

  override def subscribe[T <: Event: ClassTag](handler: T => Unit): Future[Unit] =
    Future { vertx.eventBus().consumer[T](addressOf[T], message => handler(message.body)) }

  private def publish[T <: Event: ClassTag](event: T): Unit =
    vertx.eventBus().publish(addressOf[T], event)

  private def publishPieceMovedEvent(lastMove: Move): Unit =
    this.publish(PieceMovedEvent(this.currentPlayer, state.chessBoard.pieces, lastMove))

  private def publishTimePassedEvent(): Unit =
    this.publish(TimePassedEvent(this.timer.timeRemaining))

  private def publishTimeEndedEvent(): Unit =
    this.publish(TimeEndedEvent(this.currentPlayer))

  private def publishPromotingPawnEvent(pawnPosition: Position): Unit =
    this.publish(PromotingPawnEvent(pawnPosition, PromotionPiece.values))

  private def findPieceOfCurrentTeam(pos: Position): Option[Piece] = playingTeam.get(pos)

  private def playingTeam: Map[Position, Piece] =
    this.state.chessBoard.pieces(this.state.currentTurn)

  private def currentPlayer: Player = this.state.currentTurn match
    case WHITE => this.state.gameConfiguration.whitePlayer
    case BLACK => this.state.gameConfiguration.blackPlayer

  private def switchTurn(): Unit =
    this.state = this.state.changeTeam()
    if this.state.gameConfiguration.timeConstraint == TimeConstraint.MoveLimit then
      this.timer.restart()

  private def enemyBaseRank(): Rank =
    this.state.currentTurn match
      case WHITE => Rank._8
      case BLACK => Rank._1
