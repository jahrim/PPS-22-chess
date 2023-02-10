/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller.components.controllers

import io.github.chess.viewcontroller.ChessGameInterface.given
import io.github.chess.viewcontroller.components.controllers.template.FXMLController
import io.github.chess.viewcontroller.components.pages.MainMenuPage
import javafx.scene.control.Button
import scalafx.stage.Stage
import java.net.URL
import java.util.ResourceBundle

/**
 * Controller of the game page of the application.
 * @param stage the stage where the application is displayed.
 */
class GamePageController()(using override protected val stage: Stage) extends FXMLController:
  @FXML @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var surrenderButton: Button = _

  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    this.surrenderButton.onMouseClicked = _ => MainMenuPage()
