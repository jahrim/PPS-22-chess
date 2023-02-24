/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller.fxcomponents.controllers

import io.github.chess.events.{PieceMovedEvent, TimeEndedEvent, TimePassedEvent}
import io.github.chess.viewcontroller.ChessApplication.{start, given}
import io.github.chess.viewcontroller.{ChessApplicationComponent, ChessApplicationContext}
import io.github.chess.viewcontroller.fxcomponents.controllers.ChessBoardController
import io.github.chess.viewcontroller.fxcomponents.controllers.template.FXMLController
import io.github.chess.viewcontroller.fxcomponents.pages.MainMenuPage
import io.vertx.core.eventbus.Message
import javafx.scene.control.{Button, TextField}
import javafx.scene.layout.GridPane

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
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

  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    chessBoardController = ChessBoardController.fromGridPane(this.chessBoardGridPane)(stage)
    this.surrenderButton.onMouseClicked = _ => MainMenuPage(stage)
    initView()
    context.chessEngineProxy.subscribe(PieceMovedEvent.address(), onPieceMoved)
    context.chessEngineProxy.subscribe(TimePassedEvent.address(), onTimePassed)
    context.chessEngineProxy.subscribe(TimeEndedEvent.address(), onTimeEnded)

  private def initView(): Unit =
    context.chessEngineProxy.getState.onComplete {
      case Success(status) =>
        Platform.runLater {
          chessBoardController.repaint(status.chessBoard.pieces)
          currentTurnText.setText(
            s"${status.gameConfiguration.whitePlayer.name} -> ${status.currentTurn.toString}"
          )
          timeRemainingText.setText("-:-:-")
          lastMoveText.setText("N/A")
        }
      case Failure(exception) => throw exception
    }

  private def onPieceMoved(message: Message[PieceMovedEvent]): Unit =
    Platform.runLater(() =>
      val event: PieceMovedEvent = message.body()
      currentTurnText.setText(s"${event.currentPlayer.name} -> ${event.currentPlayer.team}")
      lastMoveText.setText(s"${event.lastMove.from} -> ${event.lastMove.to}")
      chessBoardController.repaint(event.boardDisposition)
    )

  private def onTimePassed(message: Message[TimePassedEvent]): Unit =
    Platform.runLater(() =>
      val timeRemaining = message.body().timeRemaining
      this.timeRemainingText.setText(
        s"${timeRemaining.toMinutes}m:${timeRemaining.toSeconds % 60}s"
      )
    )

  private def onTimeEnded(message: Message[TimeEndedEvent]): Unit = ???

// TODO: get access to the proxy for the chess engine service (as a given constructor parameter?)
// TODO: handle surrender logic
