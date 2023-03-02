/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.application.viewcontroller.fxutils

import io.github.chess.engine.model.board.{File, Position, Rank}
import javafx.scene.Node
import scalafx.Includes.*
import scalafx.geometry.Insets
import scalafx.stage.{Screen, Stage}
import scalafx.scene.layout.{Background, BackgroundFill, CornerRadii, GridPane, Region}
import scalafx.scene.paint.Color

/** Utility class for ScalaFX. */
object FXUtils:

  /**
   * @return a position in the chess board, given a pair of coordinates considering the
   *         view reference system.
   */
  given convertViewReferenceSystemToPosition: Conversion[(Int, Int), Position] = (x, y) =>
    Position(File.fromOrdinal(x), Rank.fromOrdinal(Rank.values.length - y - 1))

  /**
   * Change the color of the specified region to the specified color.
   * @param region the specified region
   * @param color the specified color
   */
  def changeColor(region: Region, color: Color): Unit =
    region.background = Background(Array(BackgroundFill(color, CornerRadii.Empty, Insets.Empty)))

  extension (self: Stage)
    /**
     * Center this stage on the specified screen.
     * @param screen the specified screen
     */
    def centerOnScreen(screen: Screen = Screen.primary): Unit =
      self.x = (screen.visualBounds.getWidth - self.getWidth) / 2
      self.y = (screen.visualBounds.getHeight - self.getHeight) / 2

  /**
   * A cell in a [[javafx.scene.layout.GridPane]].
   * @param x the horizontal coordinate of this cell
   * @param y the vertical coordinate of this cell
   * @param content the graphical content of this cell
   * @tparam N the type of graphical content of this cell
   * @note the cell with coordinates (0,0) is at the top-left corner of the grid.
   *       Coordinates increase to the right and to the bottom of the grid.
   */
  case class GridCell[N <: Node](x: Int, y: Int, content: N)
  extension (self: GridPane)
    /** @return an iterator of the cells of the grid. */
    def cells: Iterator[GridCell[_]] =
      self.getChildren.map { n =>
        GridCell(GridPane.getColumnIndex(n).toInt, GridPane.getRowIndex(n).toInt, n)
      }.iterator
