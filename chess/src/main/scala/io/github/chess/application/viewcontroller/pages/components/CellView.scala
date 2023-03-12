/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.application.viewcontroller.pages.components

import io.github.chess.application.viewcontroller.FXComponent
import io.github.chess.util.scala.number.NumberExtension.sameParityAs
import io.github.chess.engine.model.board.{Position, Rank}
import io.github.chess.application.viewcontroller.configuration.InterfaceConfiguration.{
  Colors,
  Images
}
import io.github.chess.application.viewcontroller.pages.components.CellView.Layer
import io.github.chess.application.viewcontroller.pages.components.CellView.{
  MOVE_EFFECT_RESCALING,
  PIECE_RESCALING
}
import io.github.chess.application.viewcontroller.fxutils.FXUtils
import io.github.chess.application.viewcontroller.fxutils.FXUtils.*
import io.github.chess.application.viewcontroller.fxutils.FXUtils.given
import javafx.scene.layout.Pane
import scalafx.scene.image.ImageView
import scalafx.scene.paint.Color
import scalafx.stage.Stage

/**
 * Handles the view of a cell of the chess board view.
 * @param cell     the cell of the chess board view
 */
case class CellView(cell: GridCell[Pane]) extends FXComponent:
  export cell.content.setOnMouseClicked

  private var layers: Map[Layer, ImageView] = Map()

  /** @return the position of this cell in the chess board. */
  def position: Position = (cell.x, cell.y)

  /**
   * Add the specified piece to the content of this cell.
   * @param p the view of the specified piece
   */
  def setPiece(p: PieceView): Unit =
    this.updateLayer(
      Layer.Piece,
      new ImageView:
        image = p.image
        fitWidth = cell.content.getWidth * PIECE_RESCALING
        preserveRatio = true
    )

  /** Removes the piece from the content of this cell. */
  def removePiece(): Unit = this.clearLayer(Layer.Piece)

  /**
   * Add a move available effect to the content of this cell, signalling the user that
   * he can move his currently selected piece in this cell.
   */
  def setMoveAvailableEffect(): Unit =
    this.updateLayer(
      Layer.AvailableMoveEffect,
      new ImageView:
        image = Images.Effects.AvailableMove
        opacity = 0.5
        fitWidth = cell.content.getWidth * MOVE_EFFECT_RESCALING
        preserveRatio = true
    )

  /** Remove the move available effect from the content of this cell. */
  def removeMoveAvailableEffect(): Unit = this.clearLayer(Layer.AvailableMoveEffect)

  /** Add an highlighting effect to this cell. */
  def emphasize(): Unit = FXUtils.changeColor(this.cell.content, Colors.SelectedCell)

  /** Add an highlighting effect of capture to this cell. */
  def emphasizeCapture(): Unit = FXUtils.changeColor(this.cell.content, Colors.CapturingCell)

  /** Remove the highlighting effect from this cell. */
  def deemphasize(): Unit = FXUtils.changeColor(this.cell.content, this.defaultColor)

  /** @return the default color of this cell */
  private def defaultColor: Color =
    if (cell.x sameParityAs cell.y) Colors.LightBrownCell else Colors.DarkBrownCell

  /**
   * Updates the specified layer of this cell with the specified image.
   * @param layer the specified layer
   * @param image the specified image
   */
  private def updateLayer(layer: Layer, image: ImageView): Unit =
    this.clearLayer(layer)
    this.layers += layer -> image
    this.layers.get(layer).foreach { this.cell.content.getChildren.add(_) }

  /**
   * Clears the specified layer.
   * @param layer the specified layer
   */
  private def clearLayer(layer: Layer): Unit =
    this.layers.get(layer).foreach { this.cell.content.getChildren.removeAll(_) }
    this.layers -= layer

  /** Removes any content from this cell. */
  private def clear(): Unit = this.cell.content.getChildren.clear()

/** Companion object of [[CellView]]. */
object CellView:
  private val PIECE_RESCALING: Double = 0.8d
  private val MOVE_EFFECT_RESCALING: Double = 0.33d

  /** Possible layers of the view of a cell. */
  private enum Layer:
    /** The layer displaying the piece inside the cell. */
    case Piece

    /** The layer displaying the move available effect inside the cell. */
    case AvailableMoveEffect
