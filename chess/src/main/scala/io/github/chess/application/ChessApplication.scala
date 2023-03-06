/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.application

import io.github.chess.application.ChessApplicationContext.ChessApplicationContextBuilder
import io.github.chess.application.viewcontroller.configuration.InterfaceConfiguration.Images
import io.github.chess.application.viewcontroller.pages.MainMenuPage
import io.github.chess.engine.ports.ChessPort
import scalafx.application.JFXApp3
import scalafx.application.JFXApp3.PrimaryStage

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

/** Graphical user interface of Chess Game. */
object ChessApplication extends JFXApp3:
  private val contextBuilder: ChessApplicationContextBuilder = ChessApplicationContext.builder

  /** @return the context of this application as a given instance. */
  given applicationContext: ChessApplicationContext = this.contextBuilder.build

  /**
   * Launch the graphical user interface with the specified arguments.
   *
   * @param args the specified arguments
   */
  def launch(chessEngineProxy: ChessPort)(args: Array[String]): Unit =
    Future {
      this.contextBuilder.setChessEngineProxy(chessEngineProxy)
      this.main(args)
    }

  override def start(): Unit =
    stageConfiguration()
    MainMenuPage(stage)

  override def stopApp(): Unit = System.exit(0)

  /** Configure the stage of the application. */
  private def stageConfiguration(): Unit =
    this.stage = new PrimaryStage():
      title = "Chess Game"
      resizable = false
      icons.insert(0, Images.Icons.GameIcon)
    this.contextBuilder.setPrimaryStage(this.stage)
