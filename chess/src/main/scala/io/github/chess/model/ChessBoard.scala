/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.model.Team.{BLACK, WHITE}

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
  def apply(): ChessBoard = ChessBoardImpl()

  private case class ChessBoardImpl() extends ChessBoard:
    import scala.collection.immutable.HashMap

    private var whitePieces: Map[Position, Piece] = HashMap()
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
      val newTeam = this.applyMove(move)
      this.currentlyPlayingTeam match
        case WHITE => this.whitePieces = newTeam
        case BLACK => this.blackPieces = newTeam
      changePlayingTeam()

    private def playingTeam: Map[Position, Piece] = this.currentlyPlayingTeam match
      case WHITE => this.whitePieces
      case BLACK => this.blackPieces

    private def changePlayingTeam(): Unit = this.currentlyPlayingTeam =
      this.currentlyPlayingTeam.oppositeTeam

    private def applyMove(move: Move): Map[Position, Piece] =
      val team = playingTeam
      val pieceToMove = team.get(move.from)
      pieceToMove match
        case Some(piece) => team - move.from + ((move.to, piece))
        case None        => team
