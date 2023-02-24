/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller.fxcomponents.controllers

import io.github.chess.model.configuration.{GameConfiguration, GameMode, Player, TimeConstraint}
import io.github.chess.model.Team
import io.github.chess.viewcontroller.ChessApplication.given
import io.github.chess.viewcontroller.{ChessApplicationComponent, ChessApplicationContext}
import io.github.chess.viewcontroller.fxcomponents.controllers.template.FXMLController
import io.github.chess.viewcontroller.fxcomponents.pages.{GamePage, MainMenuPage}
import javafx.collections.{FXCollections, ObservableList}
import javafx.scene.control.{Button, ChoiceBox, Spinner, SpinnerValueFactory, TextField}
import scalafx.application.Platform
import scalafx.stage.Stage
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}
import java.net.URL
import java.util.ResourceBundle

/**
 * Controller of the game configuration page of the application.
 * @param stage the stage where the application is displayed.
 */
class GameConfigurationPageController(override protected val stage: Stage)(using
    override protected val context: ChessApplicationContext
) extends FXMLController
    with ChessApplicationComponent:
  @FXML @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var startGameButton: Button = _
  @FXML @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var backButton: Button = _
  @FXML @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var timeConstraint: ChoiceBox[TimeConstraint] = _
  @FXML @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var time: Spinner[Integer] = _
  @FXML @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var gameMode: ChoiceBox[GameMode] = _
  @FXML @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var whitePlayer: TextField = _
  @FXML @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var blackPlayer: TextField = _

  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    this.backButton.onMouseClicked = _ => MainMenuPage(stage)

    this.stage.setResizable(false)
    this.timeConstraint.setItems(FXCollections.observableArrayList(TimeConstraint.values*))
    this.timeConstraint.setValue(TimeConstraint.NoLimit)
    this.timeConstraint.onAction = _ =>
      this.time.setDisable(this.timeConstraint.getValue == TimeConstraint.NoLimit)
    this.time.setDisable(true)
    this.time.setValueFactory(
      SpinnerValueFactory.IntegerSpinnerValueFactory(
        TimeConstraint.MinMoveLimit,
        TimeConstraint.MaxMoveLimit,
        TimeConstraint.MoveLimit.minutes
      )
    )

    this.gameMode.setItems(FXCollections.observableArrayList(GameMode.PVP))
    this.gameMode.setValue(GameMode.PVP)
    this.startGameButton.onMouseClicked = _ =>
      val timeConstraint = this.timeConstraint.getValue
      timeConstraint.minutes = this.time.getValue
      val gameConfiguration = GameConfiguration(
        timeConstraint,
        this.gameMode.getValue,
        if this.whitePlayer.getText.equals("")
        then Player.noNameWhitePlayer
        else Player(this.whitePlayer.getText, Team.WHITE),
        if this.blackPlayer.getText.equals("")
        then Player.noNameBlackPlayer
        else Player(this.blackPlayer.getText, Team.BLACK)
      )
      context.chessEngineProxy
        .startGame(gameConfiguration)
        .onComplete {
          case Success(_)         => Platform.runLater { GamePage(stage) }
          case Failure(exception) => throw exception
        }
