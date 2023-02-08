/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller.components.pages.template

import io.github.chess.viewcontroller.components.controllers.template.Controller
import io.github.chess.viewcontroller.components.pages.template.Page
import io.github.chess.viewcontroller.components.StageComponent
import scalafx.stage.Stage

/**
 * A mixin that allows a page to be applied to a stage by simply passing it in the constructor.
 * It requires [[ApplicablePage.stage]] to be overridden in the primary constructor.
 */
trait ApplicablePage extends Page with StageComponent:
  this.stage.scene = this.getScene
