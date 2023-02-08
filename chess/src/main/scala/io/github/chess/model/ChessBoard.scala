/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.model.Team.{BLACK, WHITE}

trait ChessBoard:

  def pieces: Map[Position, Piece]

  def findMoves(position: Position): Set[Move]

  def move(move: Move): Unit

case class ChessBoardImpl() extends ChessBoard:
  import scala.collection.immutable.HashMap

  private var whitePieces: Map[Position, Piece] = HashMap()
  private var blackPieces: Map[Position, Piece] = HashMap()
  private var currentlyPlayingTeam = Team.WHITE

  override def pieces: Map[Position, Piece] = this.whitePieces ++ this.blackPieces

  override def findMoves(position: Position): Set[Move] =
    val team = playingTeam
    val selectedPiece = team.get(position)
    selectedPiece match
      case Some(piece) =>
        val destinations = piece.findMoves(position)
      // destinations.map(_ => new Move(position, _))
      // Or map it in a different way, the destination positions to the actual move of the piece
    Set.empty

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
