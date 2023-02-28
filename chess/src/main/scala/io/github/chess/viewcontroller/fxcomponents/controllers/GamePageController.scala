/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller.fxcomponents.controllers

import io.github.chess.events.{GameOverEvent, PieceMovedEvent, PromotingPawnEvent, TimePassedEvent}
import io.github.chess.model.configuration.Player
import io.github.chess.model.PromotionPiece
import io.github.chess.viewcontroller.ChessApplication.{start, given}
import io.github.chess.viewcontroller.{ChessApplicationComponent, ChessApplicationContext}
import io.github.chess.viewcontroller.fxcomponents.controllers.ChessBoardController
import io.github.chess.viewcontroller.fxcomponents.controllers.template.FXMLController
import io.github.chess.viewcontroller.fxcomponents.pages.MainMenuPage
import io.vertx.core.eventbus.Message
import javafx.scene.control.{Button, ChoiceDialog, TextField, ButtonType}
import javafx.scene.layout.GridPane

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import scalafx.scene.control.Alert
import scalafx.scene.control.Alert.AlertType
import scalafx.application.Platform
import scalafx.stage.Stage

import java.net.URL
import java.util.ResourceBundle

/**
 * Controller of the game page of the application.
 * @param stage the stage where the application is displayed.
 */
class GamePageController(override protected val stage: Stage)(using
    override protected val context: ChessApplicationContext
) extends FXMLController
    with ChessApplicationComponent:
  @FXML @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var surrenderButton: Button = _
  @FXML @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var chessBoardGridPane: GridPane = _
  @FXML @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var currentTurnText: TextField = _
  @FXML @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var timeRemainingText: TextField = _
  @FXML @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var lastMoveText: TextField = _
  @FXML @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var chessBoardController: ChessBoardController = _

  private var currentPlayerBelief: Option[Player] = Option.empty

  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    chessBoardController = ChessBoardController.fromGridPane(this.chessBoardGridPane)(stage)
    this.surrenderButton.onMouseClicked = _ =>
      this.currentPlayerBelief.foreach { this.context.chessEngineProxy.surrender(_) }
    initView()
    context.chessEngineProxy.subscribe[PieceMovedEvent](onPieceMoved)
    context.chessEngineProxy.subscribe[TimePassedEvent](onTimePassed)
    context.chessEngineProxy.subscribe[GameOverEvent](onGameOver)
    context.chessEngineProxy.subscribe[PromotingPawnEvent](onPromotingPawn)

  private def initView(): Unit =
    context.chessEngineProxy.getState.onComplete {
      case Success(status) =>
        Platform.runLater {
          this.currentPlayerBelief = Some(status.gameConfiguration.player(status.currentTurn))
          chessBoardController.repaint(status.chessBoard.pieces)
          currentTurnText.setText(
            s"${status.gameConfiguration.whitePlayer.name} -> ${status.currentTurn.toString}"
          )
          timeRemainingText.setText("-:-:-")
          lastMoveText.setText("N/A")
        }
      case Failure(exception) => throw exception
    }

  private def onPieceMoved(event: PieceMovedEvent): Unit =
    Platform.runLater(() =>
      this.currentPlayerBelief = Some(event.currentPlayer)
      currentTurnText.setText(s"${event.currentPlayer.name} -> ${event.currentPlayer.team}")
      lastMoveText.setText(s"${event.lastMove.from} -> ${event.lastMove.to}")
      chessBoardController.repaint(event.boardDisposition)
    )

  private def onTimePassed(event: TimePassedEvent): Unit =
    Platform.runLater(() =>
      val timeRemaining = event.timeRemaining
      this.timeRemainingText.setText(
        s"${timeRemaining.toMinutes}m:${timeRemaining.toSeconds % 60}s"
      )
    )

  private def onGameOver(event: GameOverEvent): Unit =
    Platform.runLater {
      new Alert(AlertType.Information) {
        title = "Game Over Dialog"
        headerText = "Game Over!"
        contentText = event.winner match
          case Some(player) =>
            s"Team ${player.team.toString.toLowerCase} wins! Congratulations ${player.name}!"
          case None => "Stale mate..."
      }.showAndWait()
      // TODO: unsubscribe to events
      MainMenuPage(stage)
    }

  private def onPromotingPawn(event: PromotingPawnEvent): Unit =
    Platform.runLater {
      val values = event.promotionPieces
      values.headOption match
        case Some(default) =>
          val dialog: ChoiceDialog[PromotionPiece[_]] = ChoiceDialog(default, values*)
          dialog.getDialogPane.getButtonTypes.removeIf(_ == ButtonType.CANCEL)
          dialog.getDialogPane.getScene.getWindow match
            case stage: javafx.stage.Stage => stage.setOnCloseRequest(_.consume())
            case _                         =>
          dialog.showAndWait().ifPresent { piece =>
            context.chessEngineProxy.promote(event.pawnPosition, piece).onComplete {
              case Success(_)         => this.chessBoardController.repaint()
              case Failure(exception) => throw exception
            }
          } // TODO add orElse to ifPresent
        case None =>
    }

// TODO: get access to the proxy for the chess engine service (as a given constructor parameter?)
// TODO: handle surrender logic
