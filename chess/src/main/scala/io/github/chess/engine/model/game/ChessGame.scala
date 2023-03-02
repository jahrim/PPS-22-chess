/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.game

import io.github.chess.engine.events.{
  Event,
  GameOverEvent,
  PieceMovedEvent,
  PromotingPawnEvent,
  TimePassedEvent
}
import io.github.chess.engine.model.board.{Position, Rank}
import io.github.chess.engine.model.configuration.{GameConfiguration, Player, TimeConstraint}
import io.github.chess.engine.model.moves.{CastlingMove, EnPassantMove, Move}
import io.github.chess.engine.model.pieces.{Pawn, Piece, PromotionPiece}
import io.github.chess.engine.ports.ChessPort
import Event.addressOf
import io.github.chess.events.*
import ChessGameAnalyzer.*
import Team.{BLACK, WHITE}
import io.github.chess.model.*
import io.github.chess.util.debug.Logger
import io.github.chess.util.exception.{GameNotInitializedException, Require}
import io.github.chess.util.option.OptionExtension.anyToOptionOfAny
import io.vertx.core.Vertx

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.reflect.ClassTag

/**
 * A game of chess.
 * @param vertx the vertx where this game will be deployed
 */
class ChessGame(private val vertx: Vertx) extends ChessPort:
  // TODO: use options instead of null?
  @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var state: ChessGameStatus = _
  private val timerManager = TimerManager()

  subscribe[GameOverEvent] { _ => resetGame() }

  override def getState: Future[ChessGameStatus] =
    Future {
      requireInitialization()
      this.state
    }

  override def startGame(gameConfiguration: GameConfiguration): Future[Unit] =
    Future {
      logActivity("Game initialization") {
        this.state = ChessGameStatus(gameConfiguration = gameConfiguration)
        this.timerManager.start(
          gameConfiguration.timeConstraint,
          this.publishTimePassedEvent(),
          this.publishGameOverEvent()
        )
      }
    }

  override def findMoves(position: Position): Future[Set[Move]] =
    Future {
      logActivity("Search move") {
        requireInitialization()
        findPieceOfCurrentTeam(position) match
          case Some(piece) => piece.rule.findMoves(position, this.state)
          case None        => Set.empty
      }
    }

  override def applyMove(move: Move): Future[Unit] =
    Future {
      logActivity("Move execution") {
        requireInitialization()
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

        if isPromotion(move.to)
        then
          this.timerManager.stop(this.state.currentTurn)
          this.publishPromotingPawnEvent(move.to)
        else
          this.timerManager.restart(this.state.currentTurn)
          this.state = this.state.changeTeam()

        publishPieceMovedEvent(move)
        analyzeBoard()
      }
    }

  override def promote[P <: Piece](
      pawnPosition: Position,
      promotingPiece: PromotionPiece[P]
  ): Future[Unit] =
    Future {
      logActivity("Pawn promotion") {
        requireInitialization()
        if isPromotion(pawnPosition)
        then
          this.state = this.state.updateChessBoard {
            this.state.chessBoard.setPiece(
              pawnPosition,
              promotingPiece.pieceClass
                .getConstructor(classOf[Team])
                .newInstance(this.state.currentTurn)
            )
          }
          this.timerManager.restart(this.state.currentTurn)
          this.state = this.state.changeTeam()
          this.state.history.all.lastOption.foreach { publishPieceMovedEvent }
          analyzeBoard()
      }
    }

  override def surrender(p: Player): Future[Unit] =
    Future {
      logActivity("Player surrender") {
        requireInitialization()
        this.publish(
          GameOverEvent(
            cause = GameOverCause.Surrender,
            winner = this.state.gameConfiguration.player(p.team.oppositeTeam)
          )
        )
      }
    }

  override def subscribe[T <: Event: ClassTag](handler: T => Unit): Future[Unit] =
    Future {
      logActivity(s"Subscription to ${addressOf[T]}") {
        vertx.eventBus().consumer[T](addressOf[T], message => handler(message.body))
      }
    }

  private def publish[T <: Event: ClassTag](event: T): Unit =
    logActivity(s"Publication to ${addressOf[T]}") {
      vertx.eventBus().publish(addressOf[T], event)
    }

  private def publishPieceMovedEvent(lastMove: Move): Unit =
    val currentPlayer = this.state.currentTurn match
      case WHITE => this.state.gameConfiguration.whitePlayer
      case BLACK => this.state.gameConfiguration.blackPlayer
    this.publish(PieceMovedEvent(currentPlayer, state.chessBoard.pieces, lastMove))

  private def publishTimePassedEvent(): Unit =
    this.timerManager.currentTimer(this.state.currentTurn) match
      case Some(timer) => this.publish(TimePassedEvent(timer.timeRemaining))
      case None        =>

  private def publishGameOverEvent(): Unit =
    this.publish(
      GameOverEvent(
        cause = GameOverCause.Timeout,
        winner = this.state.gameConfiguration.player(this.state.currentTurn.oppositeTeam)
      )
    )

  private def publishPromotingPawnEvent(pawnPosition: Position): Unit =
    this.publish(PromotingPawnEvent(pawnPosition, PromotionPiece.values))

  private def findPieceOfCurrentTeam(pos: Position): Option[Piece] = playingTeam.get(pos)

  private def playingTeam: Map[Position, Piece] =
    this.state.chessBoard.pieces(this.state.currentTurn)

  private def enemyBaseRank(): Rank =
    this.state.currentTurn match
      case WHITE => Rank._8
      case BLACK => Rank._1

  private def analyzeBoard(): Unit =
    logActivity("Board analysis") {
      ChessGameAnalyzer.situationOf(this.state) match
        case Some(CheckMate) =>
          this.publish(
            GameOverEvent(
              cause = GameOverCause.Checkmate,
              winner = this.state.gameConfiguration.player(this.state.currentTurn.oppositeTeam)
            )
          )
        case Some(Stale) => this.publish(GameOverEvent(GameOverCause.Stale))
        case _           =>
    }

  @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private def resetGame(): Unit =
    logActivity("Game reset") {
      this.timerManager.stop(this.state.currentTurn)
      this.state = null
    }

  private def requireInitialization(): Unit =
    Require.state(this.state.isDefined, "This functionality requires the game to be initialized.")

  private def logActivity[T](activityName: String)(activity: => T): T =
    Logger.logActivity("INFO", "engine")(activityName)(activity)

  private def isPawn(position: Position) =
    this.findPieceOfCurrentTeam(position) match
      case Some(_: Pawn) => true
      case _             => false

  private def isPromotion(position: Position): Boolean =
    isPawn(position) && position.rank == enemyBaseRank()
