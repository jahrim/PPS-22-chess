/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller.components.pages.template

import io.github.chess.viewcontroller.components.FXComponent
import scalafx.scene.Scene

/**
 * A page of the application.
 * There should be one page for each screen of the application.
 */
trait Page extends FXComponent:
  /** @return a new scene with the content of this page. */
  def getScene: Scene
