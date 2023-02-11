/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller.fxcomponents

import scalafx.stage.Stage

/** A component of the application bound to a certain stage, either logical or graphical. */
trait StageComponent extends FXComponent:
  /** The stage where the application is displayed. */
  protected def stage: Stage
