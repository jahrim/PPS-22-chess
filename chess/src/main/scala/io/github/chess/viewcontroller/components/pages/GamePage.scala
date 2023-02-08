/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller.components.pages

import io.github.chess.viewcontroller.components.controllers.GamePageController
import io.github.chess.viewcontroller.components.controllers.template.Controller
import io.github.chess.viewcontroller.components.pages.template.{ApplicablePage, FXMLPage}
import scalafx.stage.Stage

/**
 * The page that shows the game developing in time.
 * @param stage the stage where the application is displayed
 */
case class GamePage(override protected val stage: Stage)
    extends FXMLPage(GamePageController(stage), "pages/game-page")
    with ApplicablePage
