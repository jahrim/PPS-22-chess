/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller.fxcomponents.pages

import io.github.chess.viewcontroller.ChessGameInterface.given
import io.github.chess.viewcontroller.fxcomponents.controllers.GamePageController
import io.github.chess.viewcontroller.fxcomponents.controllers.template.Controller
import io.github.chess.viewcontroller.fxcomponents.pages.template.{ApplicablePage, FXMLPage}
import scalafx.stage.Stage

/**
 * The page that shows the game developing in time.
 * @param stage the stage where the application is displayed
 */
case class GamePage()(using override protected val stage: Stage)
    extends FXMLPage(GamePageController(), "pages/game-page")
    with ApplicablePage
