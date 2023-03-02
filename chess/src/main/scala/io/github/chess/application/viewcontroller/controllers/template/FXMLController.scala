/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.application.viewcontroller.controllers.template

import javafx.fxml.{FXML, Initializable}
import io.github.chess.application.viewcontroller.controllers.template.Controller

/** A controller of the application for fxml scenes. */
trait FXMLController extends Initializable, Controller:
  export javafx.fxml.FXML
