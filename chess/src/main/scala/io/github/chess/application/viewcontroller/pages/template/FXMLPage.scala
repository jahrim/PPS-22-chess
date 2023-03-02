/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.application.viewcontroller.pages.template

import io.github.chess.util.option.OptionExtension.getOrThrow
import io.github.chess.application.viewcontroller.controllers.template.FXMLController
import io.github.chess.application.viewcontroller.pages.template.{Page, PageWithController}
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import scalafx.scene.Scene

import java.net.URL

/** An identifier for an fxml specification. */
type FXMLSpecification = URL | String

/**
 * A page whose content is defined through an fxml specification.
 * @param controller the controller of this page
 * @param fxmlSpecification the fxml specification of this page
 * @tparam C the type of the controller of this page
 * @example {{{ case class MyPage extends FXMLPage(MyController(), "pages/my-page.fxml") }}}
 */
abstract class FXMLPage[C <: FXMLController](
    override protected val controller: C,
    private val fxmlSpecification: FXMLSpecification
) extends PageWithController[C]:
  private val fxmlLoader: FXMLLoader = FXMLPage.createFXMLLoader(this.fxmlSpecification)
  this.fxmlLoader.setController(this.controller)

  override def getScene: Scene = Scene(this.fxmlLoader.load[Parent])

/** Companion object of [[FXMLPage]]. */
object FXMLPage:
  /**
   * @param fxmlSpecification the specified fxml specification
   * @return a new javafx fxml loader for the specified fxml specification.
   */
  private def createFXMLLoader(fxmlSpecification: FXMLSpecification): FXMLLoader =
    FXMLLoader(
      fxmlSpecification match
        case localPath: String => fxmlURLfromPath(localPath)
        case url: URL          => url
    )

  /**
   * Convert a local path in the filesystem to an url representing an fxml file.
   * @param localPath the local path of the resource from the resource folder, without extension
   * @return an url corresponding to the fxml resource identified by the specified path
   */
  private def fxmlURLfromPath(localPath: String): URL =
    Option(this.getClass.getResource(s"/$localPath.fxml")).getOrThrow {
      IllegalArgumentException(
        "Could not load the specified resource. Try with a different path."
      )
    }
