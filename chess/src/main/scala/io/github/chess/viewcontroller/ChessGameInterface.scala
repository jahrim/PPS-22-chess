/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller

import io.github.chess.viewcontroller.components.pages.MainMenuPage
import scalafx.Includes.*
import scalafx.application.{JFXApp3, Platform}
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.stage.Stage

/** Graphical user interface of Chess Game. */
object ChessGameInterface extends JFXApp3:

  /** @return the primary stage of this application as a given instance. */
  given mainStage: Stage = this.mainStageSupplier()
  private val mainStageSupplier: () => Stage = () => this.stage

  /**
   * Launch the graphical user interface with the specified arguments.
   * @param args the specified arguments
   */
  def launch(args: Array[String]): Unit = this.main(args)

  override def start(): Unit =
    stageConfiguration()
    MainMenuPage()

  override def stopApp(): Unit = System.exit(0)

  /** Configure the stage of the application. */
  private def stageConfiguration(): Unit =
    this.stage = new PrimaryStage():
      title = "Chess Game"
      width = 640
      height = 440
