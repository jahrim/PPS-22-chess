/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller.fxcomponents.controllers

import io.github.chess.viewcontroller.ChessApplication.given
import io.github.chess.viewcontroller.{ChessApplicationComponent, ChessApplicationContext}
import io.github.chess.viewcontroller.fxcomponents.controllers.ChessBoardController
import io.github.chess.viewcontroller.fxcomponents.controllers.template.FXMLController
import io.github.chess.viewcontroller.fxcomponents.pages.MainMenuPage
import javafx.scene.control.{Button, TextField}
import javafx.scene.layout.GridPane
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

  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    val chessBoardController = ChessBoardController.fromGridPane(this.chessBoardGridPane)(stage)
    this.surrenderButton.onMouseClicked = _ => MainMenuPage(stage)

    // TODO: get access to the proxy for the chess engine service (as a given constructor parameter?)
    // TODO: subscribe to game state changes, calling the repaint of the chess board controller
    //       and updating the information texts to the left
    // TODO: handle surrender logic
