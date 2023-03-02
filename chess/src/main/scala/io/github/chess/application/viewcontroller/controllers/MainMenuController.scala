/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.application.viewcontroller.controllers

import io.github.chess.application.ChessApplication.given
import io.github.chess.application.{ChessApplicationComponent, ChessApplicationContext}
import io.github.chess.application.viewcontroller.controllers.template.FXMLController
import io.github.chess.application.viewcontroller.pages.GameConfigurationPage
import javafx.scene.control.Button
import scalafx.application.Platform
import scalafx.stage.Stage
import java.net.URL
import java.util.ResourceBundle

/**
 * Controller of the main menu of the application.
 * @param stage the stage where the application is displayed.
 */
class MainMenuController(override protected val stage: Stage)(using
    override protected val context: ChessApplicationContext
) extends FXMLController
    with ChessApplicationComponent:
  @FXML @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var newGameButton: Button = _
  @FXML @SuppressWarnings(Array("org.wartremover.warts.Null"))
  private var exitButton: Button = _

  override def initialize(url: URL, resourceBundle: ResourceBundle): Unit =
    this.newGameButton.onMouseClicked = _ => GameConfigurationPage(stage)
    this.exitButton.onMouseClicked = _ => Platform.exit()
