/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.game

import io.github.chess.engine.events.{
  BoardChangedEvent,
  CheckNotificationEvent,
  Event,
  GameOverEvent,
  PromotingPawnEvent,
  TimePassedEvent,
  TurnChangedEvent
}
import io.github.chess.engine.model.game.exceptions.*
import io.github.chess.engine.model.board.{Position, Rank}
import io.github.chess.engine.model.configuration.{GameConfiguration, Player, TimeConstraint}
import io.github.chess.engine.model.moves.{CastlingMove, EnPassantMove, Move}
import io.github.chess.engine.model.pieces.{Pawn, Piece, PromotionPiece}
import io.github.chess.engine.ports.ChessPort
import Event.addressOf
import ChessGameAnalyzer.*
import Team.{BLACK, WHITE}
import ChessGame.given
import ChessGame.*
import ChessGameState.*
import io.github.chess.util.scala.debug.Logger
import io.github.chess.util.scala.id.Id
import io.github.chess.util.scala.option.OptionExtension.given
import io.github.chess.util.vertx.VerticleExecutor
import io.vertx.core.Vertx
import io.vertx.core.eventbus.MessageConsumer

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.reflect.ClassTag

/**
 * A game of chess.
 * @param vertx the vertx where this game will be deployed
 */
class ChessGame(private val vertx: Vertx) extends ChessPort:
  private var state: ChessGameState = ChessGameState.NotConfigured()
  private val timerManager: TimerManager = TimerManager()
  private val verticleExecutor: VerticleExecutor = VerticleExecutor(this.vertx)
  private var subscriptions: Map[String, MessageConsumer[_]] = Map.empty

  subscribe[GameOverEvent] { _ => resetGame() }
  subscribe[TurnChangedEvent] { _ => analyzeBoard() }

  override def getState: Future[ChessGameState] =
    runOnVerticle("State retrieval") { this.state }

  override def startGame(gameConfiguration: GameConfiguration): Future[Unit] =
    runOnVerticle("Game initialization") {
      onlyIfNotConfigured {
        this.state = Running(ChessGameStatus(gameConfiguration = gameConfiguration))
        this.timerManager.start(
          gameConfiguration.timeConstraint,
          this.publishTimePassedEvent(),
          this.publishTimeOutGameOverEvent()
        )
      }
    }

  override def findMoves(position: Position): Future[Set[Move]] =
    runOnVerticle("Find move") {
      onlyIfRunning { status =>
        status.chessBoard.pieces(status.currentTurn).get(position) match
          case Some(piece) => piece.rule.findMoves(position, status)
          case None        => Set.empty
      }
    }

  override def applyMove(move: Move): Future[Unit] =
    runOnVerticle("Move execution") {
      onlyIfRunning { currentStatus =>
        // Update the state of the game by applying the specified move
        var status = currentStatus
        status = status.updateChessBoard {
          val chessBoard = status.chessBoard.movePiece(move.from, move.to)
          move match
            case castlingMove: CastlingMove =>
              chessBoard.movePiece(castlingMove.rookFromPosition, castlingMove.rookToPosition)
            case enPassantMove: EnPassantMove =>
              chessBoard.removePiece(enPassantMove.capturedPiecePosition)
            case _ => chessBoard
        }
        status = status.updateHistory {
          status.chessBoard.pieces.get(move.to) match
            case Some(piece) => status.history.save(piece, move)
            case None        => status.history
        }
        this.state = Running(status)
        publishBoardChangedEvent()

        // Check if the current player can promote any pawn
        ChessGameAnalyzer.promotion(status) match
          case Some(promotingPawnPosition) =>
            this.timerManager.stop(status.currentTurn)
            this.state = AwaitingPromotion(status)
            this.publishPromotingPawnEvent(promotingPawnPosition)
          case _ => switchTurn()
      }
    }

  override def promote[P <: Piece](
      pawnPosition: Position,
      promotingPiece: PromotionPiece[P]
  ): Future[Unit] =
    runOnVerticle("Pawn promotion") {
      onlyIfAwaitingPromotion { status =>
        this.state = Running(
          status.updateChessBoard {
            status.chessBoard.setPiece(
              pawnPosition,
              promotingPiece.pieceClass
                .getConstructor(classOf[Team])
                .newInstance(status.currentTurn)
            )
          }
        )
        publishBoardChangedEvent()
        switchTurn()
      }
    }

  override def surrender(p: Player): Future[Unit] =
    runOnVerticle("Player surrender") {
      onlyIfRunning { status =>
        this.publish(
          GameOverEvent(
            cause = GameOverCause.Surrender,
            winner = status.gameConfiguration.player(p.team.oppositeTeam)
          )
        )
      }
    }

  /** Change the current player in this chess game. */
  private def switchTurn(): Unit =
    logActivity("Switching turn") {
      onlyIfRunning { status =>
        this.timerManager.restart(status.currentTurn)
        this.state = Running(status.changeTurn())
        publishTurnChangedEvent()
      }
    }

  /** Analyzes the board searching for end game situations. */
  private def analyzeBoard(): Unit =
    logActivity("Board analysis") {
      onlyIfRunning { status =>
        ChessGameAnalyzer.situationOf(status) match
          case Some(CheckMate) =>
            this.publish(
              GameOverEvent(
                cause = GameOverCause.Checkmate,
                winner = status.gameConfiguration.player(status.currentTurn.oppositeTeam)
              )
            )
          case Some(Stale) =>
            this.publish(GameOverEvent(GameOverCause.Stale))
          case Some(Check) =>
            this.publish(
              CheckNotificationEvent(status.gameConfiguration.player(status.currentTurn))
            )
          case _ =>
      }
    }

  /** Resets the game to its initial state. */
  private def resetGame(): Unit =
    logActivity("Game reset") {
      onlyIfRunning { status =>
        this.timerManager.stop(status.currentTurn)
        this.state = ChessGameState.NotConfigured()
      }
    }

  override def subscribe[T <: Event: ClassTag](handler: T => Unit): Future[String] =
    val subscriptionId: String = Id()
    runOnVerticle(s"Subscription to ${addressOf[T]} {#$subscriptionId}") {
      this.subscriptions +=
        subscriptionId ->
          this.vertx
            .eventBus()
            .consumer[T](addressOf[T], message => handler(message.body))
      subscriptionId
    }

  override def unsubscribe(subscriptionIds: String*): Future[Unit] =
    runOnVerticle(s"Cancelling subscriptions {#${subscriptionIds.mkString(",#")}}") {
      subscriptionIds.foreach { subscriptionId =>
        this.subscriptions.get(subscriptionId).foreach { consumer =>
          consumer.unregister()
          this.subscriptions -= subscriptionId
        }
      }
    }

  /**
   * Publish the specified event notifying all subscribers.
   * @param event the specified event
   * @tparam T the type of the specified event
   */
  private def publish[T <: Event: ClassTag](event: T): Unit =
    logActivity(s"Publication to ${addressOf[T]}") {
      this.vertx.eventBus().publish(addressOf[T], event)
    }

  private def publishBoardChangedEvent(): Unit =
    onlyIfConfigured { status =>
      status.history.all.lastOption.foreach { latestMove =>
        this.publish(
          BoardChangedEvent(
            status.gameConfiguration.player(status.currentTurn),
            status.chessBoard.pieces,
            latestMove
          )
        )
      }
    }

  private def publishTurnChangedEvent(): Unit =
    onlyIfConfigured { status =>
      this.publish(TurnChangedEvent(status.gameConfiguration.player(status.currentTurn)))
    }

  private def publishTimePassedEvent(): Unit =
    onlyIfConfigured { status =>
      this.timerManager.currentTimer(status.currentTurn) match
        case Some(timer) => this.publish(TimePassedEvent(timer.timeRemaining))
        case None        =>
    }

  private def publishTimeOutGameOverEvent(): Unit =
    onlyIfConfigured { status =>
      this.publish(
        GameOverEvent(
          cause = GameOverCause.Timeout,
          winner = status.gameConfiguration.player(status.currentTurn.oppositeTeam)
        )
      )
    }

  private def publishPromotingPawnEvent(pawnPosition: Position): Unit =
    onlyIfConfigured { _ =>
      this.publish(PromotingPawnEvent(pawnPosition, PromotionPiece.values))
    }

  /**
   * Runs the specified activity in the event-loop.
   * @param activityName the name of the specified activity
   * @param activity the specified activity
   * @tparam T the return type of the specified activity
   * @return the result of the specified activity
   */
  private def runOnVerticle[T](activityName: String)(activity: => T): Future[T] =
    this.verticleExecutor.runLater { logActivity(activityName) { activity } }

  /**
   * Executes the specified activity only if the game has not been configured yet.
   * @throws GameAlreadyConfiguredException if the game has already been configured.
   */
  private def onlyIfNotConfigured[T](activity: => T): T = this.state match
    case NotConfigured() => activity
    case _               => throw GameAlreadyConfiguredException()

  /**
   * Executes the specified activity only if the game has already started.
   * @throws GameNotConfiguredException if the game hasn't started yet.
   */
  private def onlyIfConfigured[T](activity: ChessGameStatus => T): T = this.state match
    case Running(status)           => activity(status)
    case AwaitingPromotion(status) => activity(status)
    case _                         => throw GameNotConfiguredException()

  /**
   * Executes the specified activity only if the game is running.
   * @throws GameNotRunningException if the game is not running.
   */
  private def onlyIfRunning[T](activity: ChessGameStatus => T): T = this.state match
    case Running(status) => activity(status)
    case _               => throw GameNotRunningException()

  /**
   * Executes the specified activity only if the game is awaiting for a promotion to happen.
   * @throws GameNotAwaitingPromotionException if the game is not awaiting for a promotion to happen.
   */
  private def onlyIfAwaitingPromotion[T](activity: ChessGameStatus => T): T = this.state match
    case AwaitingPromotion(status) => activity(status)
    case _                         => throw GameNotAwaitingPromotionException()

/** Companion object of [[ChessGame]]. */
object ChessGame:
  private def logActivity[T](activityName: String)(activity: => T): T =
    Logger.logActivity("INFO", "engine")(activityName)(activity)
