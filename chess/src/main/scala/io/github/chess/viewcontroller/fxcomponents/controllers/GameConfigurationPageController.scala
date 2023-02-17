/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller.fxcomponents.controllers

import io.github.chess.viewcontroller.ChessApplication.given
import io.github.chess.viewcontroller.{ChessApplicationComponent, ChessApplicationContext}
import io.github.chess.viewcontroller.fxcomponents.controllers.template.FXMLController
import io.github.chess.viewcontroller.fxcomponents.pages.{GamePage, MainMenuPage}
import javafx.scene.control.Button
import scalafx.stage.Stage
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

  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    this.startGameButton.onMouseClicked = _ => GamePage(stage)
    this.backButton.onMouseClicked = _ => MainMenuPage(stage)
