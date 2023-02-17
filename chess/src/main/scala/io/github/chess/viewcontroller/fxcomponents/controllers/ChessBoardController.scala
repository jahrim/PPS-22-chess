/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller.fxcomponents.controllers

import io.github.chess.model.moves.Move
import io.github.chess.model.pieces.Piece
import io.github.chess.model.{ChessBoard, Position, moves}
import io.github.chess.util.stateful.StatefulSystem
import io.github.chess.viewcontroller.{ChessApplicationComponent, ChessApplicationContext}
import io.github.chess.viewcontroller.fxutils.FXUtils.*
import io.github.chess.viewcontroller.fxcomponents.controllers.template.Controller
import io.github.chess.viewcontroller.fxcomponents.controllers.ChessBoardController.State
import io.github.chess.viewcontroller.fxcomponents.controllers.ChessBoardController.State.*
import io.github.chess.viewcontroller.fxcomponents.pages.components.{CellView, PieceView}
import io.vertx.core.Future
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
)(override protected val stage: Stage)(using
    override protected val context: ChessApplicationContext
) extends Controller
    with StatefulSystem(State.NoneSelected)
    with ChessApplicationComponent:
  private var chessBoardBelief: Map[Position, Piece] = Map.empty
  this.cells.values.foreach(cell => cell.setOnMouseClicked { onCellClicked(_, cell) })
  repaint()

  /** Retrieves the state of the chess engine service and shows it. */
  def repaint(): Unit =
    this.context.chessEngineProxy.getState.onSuccess { state =>
      this.repaint(state.chessBoard.pieces)
    }

  /**
   * Shows the specified state of the chess board.
   * @param chessBoard the specified state of the chess board
   */
  def repaint(chessBoard: Map[Position, Piece]): Unit =
    Platform.runLater {
      // TODO: retrieve the team of the pieces somehow from the model
      this.clearPieces()
      this.chessBoardBelief = chessBoard
      this.chessBoardBelief.foreach { (position, piece) =>
        this.cells.get(position).foreach { _.setPiece(PieceView(piece)) }
      }
    }

  /** Removes all pieces from the chess board view. */
  private def clearPieces(): Unit =
    ChessBoard.Positions.foreach { position =>
      this.cells.get(position).foreach {
        _.removePiece()
      }
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
            this.context.chessEngineProxy.applyMove(selectedMove)
            enter(NoneSelected)
          case None => this.selectCell(clickedCell)
    // TODO: temporary. Repaint will be called in the subscription to the state changes
    repaint()

  /**
   * Select the specified cell, checking if any piece was selected.
   * @param cell the specified cell
   */
  private def selectCell(cell: CellView): Unit =
    this.chessBoardBelief.get(cell.position).map(_ => cell) match
      case Some(selectedCell) =>
        getAvailableMoves(selectedCell).onSuccess { availableMoves =>
          enter(PieceSelected(selectedCell, availableMoves))
        }
      case None =>
        enter(NoneSelected)

  /**
   * @param selectedCell the specified cell
   * @return a future containing the available moves of the piece in the specified cell
   */
  private def getAvailableMoves(selectedCell: CellView): Future[Map[Position, Move]] =
    this.context.chessEngineProxy
      .findMoves(selectedCell.position)
      .map(moves => moves.map(move => move.to -> move).toMap[Position, Move])

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
  def fromGridPane(
      grid: GridPane
  )(stage: Stage)(using context: ChessApplicationContext): ChessBoardController =
    ChessBoardController(
      grid.cells
        .collect { case cell: GridCell[Pane] => CellView(cell); }
        .map { c => c.position -> c }
        .toMap[Position, CellView]
    )(stage)(using context)
