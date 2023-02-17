/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller

import io.github.chess.ports.ChessPort
import scalafx.stage.Stage

/** Component of this chess application, either logical or graphical. */
trait ChessApplicationComponent:
  /** @return the context of this application */
  protected def context: ChessApplicationContext
