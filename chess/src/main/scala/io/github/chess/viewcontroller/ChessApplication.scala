/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller

import io.github.chess.ports.ChessPort
import io.github.chess.viewcontroller.ChessApplicationContext.ChessApplicationContextBuilder
import io.github.chess.viewcontroller.fxcomponents.pages.MainMenuPage
import scalafx.Includes.*
import scalafx.application.{JFXApp3, Platform}
import scalafx.application.JFXApp3.PrimaryStage
import scalafx.stage.Stage

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/** Graphical user interface of Chess Game. */
object ChessApplication extends JFXApp3:
  private val contextBuilder: ChessApplicationContextBuilder = ChessApplicationContext.builder

  /** @return the context of this application as a given instance. */
  given applicationContext: ChessApplicationContext = this.contextBuilder.build

  /**
   * Launch the graphical user interface with the specified arguments.
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
    this.contextBuilder.setPrimaryStage(this.stage)
