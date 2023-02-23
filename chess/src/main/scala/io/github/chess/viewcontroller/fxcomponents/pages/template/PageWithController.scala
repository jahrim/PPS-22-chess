/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller.fxcomponents.pages.template

import io.github.chess.viewcontroller.fxcomponents.controllers.template.Controller
import io.github.chess.viewcontroller.fxcomponents.pages.template.Page

/**
 * A mixin that binds a page to a controller.
 * @tparam C the type of the controller of this page
 */
trait PageWithController[C <: Controller] extends Page:

  /** @return the controller of this page. */
  protected def controller: C
