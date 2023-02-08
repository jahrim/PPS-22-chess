/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller.components

/**
 * A component of the application, either logical or graphical.
 * @note a class extending this interface gets access to most of the ScalaFX implicits, used to interoperate with
 *       JavaFX.
 */
trait FXComponent:
  export scalafx.Includes.*
