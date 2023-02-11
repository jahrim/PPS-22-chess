/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.events.EndTurnEvent
import io.github.chess.model.Team.{BLACK, WHITE}
import io.vertx.core.Vertx

/** The trait representing the concept of a Chess Board. */
trait ChessBoard:

  /**
   * Gives access to all the [[Piece]]s that are present on the board.
   * @return the map containing both white and black [[Piece]]s of the board
   */
  def pieces: Map[Position, Piece]

  /**
   * Gives all the available moves for a pieces placed in a specified position.
   * @param position the [[Position]] where the piece to be moved is placed
   * @return all the available moves that could be performed by the piece
   */
  def findMoves(position: Position): Set[Move]

  /**
   * Performs the move by a piece on the board.
   * @param move The [[Move]] to be executed
   */
  def move(move: Move): Unit

/** Factory for [[ChessBoard]] instances. */
object ChessBoard:

  /**
   * Creates a new Chess Board.
   * @return a new [[ChessBoard]]
   */
  def apply(vertx: Vertx): ChessBoard = ChessBoardImpl(vertx)

  private case class ChessBoardImpl(private val vertx: Vertx) extends ChessBoard:
    import scala.collection.immutable.HashMap

    private var whitePieces: Map[Position, Piece] =
      Map.empty + ((Position(File.A, Rank._2), Pawn()))
    private var blackPieces: Map[Position, Piece] = HashMap()
    private var currentlyPlayingTeam = Team.WHITE

    override def pieces: Map[Position, Piece] = this.whitePieces ++ this.blackPieces

    override def findMoves(position: Position): Set[Move] =
      val team = playingTeam
      val selectedPiece = team.get(position)
      selectedPiece match
        case Some(piece) => piece.findMoves(position).map(dest => Move(position, dest))
        case None        => Set.empty

    override def move(move: Move): Unit =
      if findPiece(move.from).isDefined then
        val newTeam = this.applyMove(move)
        this.currentlyPlayingTeam match
          case WHITE => this.whitePieces = newTeam
          case BLACK => this.blackPieces = newTeam
        // TODO: changePlayingTeam()

    private def playingTeam: Map[Position, Piece] = this.currentlyPlayingTeam match
      case WHITE => this.whitePieces
      case BLACK => this.blackPieces

    private def changePlayingTeam(): Unit =
      this.currentlyPlayingTeam = this.currentlyPlayingTeam.oppositeTeam
      val endTurnEvent = EndTurnEvent(this.currentlyPlayingTeam)
      vertx.eventBus().publish(endTurnEvent.address, endTurnEvent)

    private def findPiece(pos: Position): Option[Piece] = playingTeam.get(pos)

    private def applyMove(move: Move): Map[Position, Piece] =
      val team = playingTeam
      val pieceToMove = findPiece(move.from)
      pieceToMove match
        case Some(piece) => team - move.from + ((move.to, piece))
        case None        => team
