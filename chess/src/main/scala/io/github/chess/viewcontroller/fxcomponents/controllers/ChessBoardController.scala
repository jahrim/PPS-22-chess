/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller.fxcomponents.controllers

import io.github.chess.model.{ChessBoard, Move, Piece, Position}
import io.github.chess.util.position.PositionExtension.*
import io.github.chess.util.stateful.StatefulSystem
import io.github.chess.viewcontroller.fxutils.FXUtils.*
import io.github.chess.viewcontroller.fxcomponents.controllers.template.Controller
import io.github.chess.viewcontroller.fxcomponents.controllers.ChessBoardController.State
import io.github.chess.viewcontroller.fxcomponents.controllers.ChessBoardController.State.*
import io.github.chess.viewcontroller.fxcomponents.pages.components.{CellView, PieceView}
import io.vertx.core.Vertx
import javafx.scene.input.MouseEvent
import javafx.scene.layout.GridPane
import javafx.scene.layout.Pane
import scalafx.Includes.*
import scalafx.application.Platform
import scalafx.stage.Stage

/**
 * Controller of the chess board view.
 * @param cells the cell of the chess board view
 * @param stage the stage where the application is displayed
 */
case class ChessBoardController private (
    private val cells: Map[Position, CellView]
)(using override protected val stage: Stage)
    extends Controller
    with StatefulSystem(State.NoneSelected):
  // TODO: temporary. Instead of the chessboard, here there should be the chess engine api
  private val chessBoard: ChessBoard = ChessBoard(Vertx.vertx())
  this.cells.values.foreach(cell => cell.setOnMouseClicked { onCellClicked(_, cell) })
  Platform.runLater { repaint(this.chessBoard.pieces) }

  /**
   * Shows the specified state of the chess board.
   * @param pieces the specified state of the chess board
   */
  def repaint(pieces: Map[Position, Piece]): Unit =
    // TODO: retrieve the team of the pieces somehow from the model
    pieces.foreach { (position, piece) =>
      this.cells.get(position).foreach { _.setPiece(PieceView(piece)) }
    }

  /**
   * Called when a cell of the chess board is clicked.
   * @param event the mouse event of the click
   * @param clickedCell the cell that was clicked
   */
  private def onCellClicked(event: MouseEvent, clickedCell: CellView): Unit =
    this.getState match
      case NoneSelected => this.selectCell(clickedCell)
      case PieceSelected(selectedCell, availableMoves) =>
        availableMoves.get(clickedCell.position) match
          case Some(selectedMove) =>
            // TODO: temporary. Piece removal will be handled by the repaint through the subscription to the state changes
            selectedCell.removePiece()
            this.chessBoard.move(selectedMove)
            enter(NoneSelected)
          case None => this.selectCell(clickedCell)
    // TODO: temporary. Repaint will be called in the subscription to the state changes
    Platform.runLater { repaint(this.chessBoard.pieces) }

  /**
   * Select the specified cell, checking if any piece was selected.
   * @param cell the specified cell
   */
  private def selectCell(cell: CellView): Unit =
    this.chessBoard.pieces.get(cell.position).map(_ => cell) match
      case Some(selectedCell) =>
        enter(PieceSelected(selectedCell, getAvailableMoves(selectedCell)))
      case None =>
        enter(NoneSelected)

  /**
   * @param selectedCell the specified cell
   * @return the available moves of the piece in the specified cell
   */
  private def getAvailableMoves(selectedCell: CellView): Map[Position, Move] =
    this.chessBoard
      .findMoves(selectedCell.position)
      .map(move => move.to -> move)
      .toMap[Position, Move]

  override protected def enterBehavior(state: State): Unit = state match
    case NoneSelected =>
    case PieceSelected(selectedCell, availableMoves) =>
      selectedCell.emphasize()
      availableMoves.keys.foreach { this.cells.get(_).foreach(_.setMoveAvailableEffect()) }

  override protected def exitBehavior(state: State): Unit = state match
    case NoneSelected =>
    case PieceSelected(selectedCell, availableMoves) =>
      selectedCell.deemphasize()
      availableMoves.keys.foreach { this.cells.get(_).foreach(_.removeMoveAvailableEffect()) }

/** Companion object of [[ChessBoardController]]. */
object ChessBoardController:
  /** States of the chess board controller. */
  enum State:
    /** When no piece is selected. */
    case NoneSelected

    /**
     * When a piece is selected and its available moves are known.
     * @param selectedCell   the cell containing the selected piece
     * @param availableMoves the known available moves of the selected piece
     */
    case PieceSelected(selectedCell: CellView, availableMoves: Map[Position, Move])

  /**
   * Creates a chess board controller from the specified grid pane.
   * @param grid the specified grid pane
   * @param stage the stage where the application is displayed
   * @return a new chess board controller
   * @note the specified grid pane should be 8 by 8 and each cell should contain a [[javafx.scene.layout.Pane]].
   */
  def fromGridPane(grid: GridPane)(using stage: Stage): ChessBoardController =
    ChessBoardController(
      grid.cells
        .collect { case cell: GridCell[Pane] => CellView(cell); }
        .map { c => c.position -> c }
        .toMap[Position, CellView]
    )(using stage)
