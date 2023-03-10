/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.application.viewcontroller.pages

import io.github.chess.application.ChessApplication.given
import io.github.chess.application.{ChessApplicationComponent, ChessApplicationContext}
import io.github.chess.application.viewcontroller.controllers.GameConfigurationController
import io.github.chess.application.viewcontroller.pages.template.{ApplicablePage, FXMLPage}
import scalafx.stage.Stage

/**
 * The page that allows to configure a game before starting it.
 * @param stage the stage where the application is displayed
 */
case class GameConfigurationPage(override protected val stage: Stage)(using
    override protected val context: ChessApplicationContext
) extends FXMLPage(GameConfigurationController(stage), "pages/game-configuration-page")
    with ApplicablePage
    with ChessApplicationComponent
