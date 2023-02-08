/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller.components.controllers

import io.github.chess.viewcontroller.components.controllers.template.FXMLController
import io.github.chess.viewcontroller.components.pages.GameConfigurationPage
import javafx.scene.control.Button
import scalafx.application.Platform
import scalafx.stage.Stage
import java.net.URL
import java.util.ResourceBundle

/**
 * Controller of the main menu of the application.
 * @param stage the stage where the application is displayed.
 */
class MainMenuController(override val stage: Stage) extends FXMLController:
  @FXML @SuppressWarnings(Array("org.wartremover.warts.Var", "org.wartremover.warts.Null"))
  private var newGameButton: Button = _
  @FXML @SuppressWarnings(Array("org.wartremover.warts.Var", "org.wartremover.warts.Null"))
  private var exitButton: Button = _

  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    this.newGameButton.onMouseClicked = _ => GameConfigurationPage(this.stage)
    this.exitButton.onMouseClicked = _ => Platform.exit()
