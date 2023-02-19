/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller.fxcomponents.pages.components

import io.github.chess.model.Team.WHITE
import io.github.chess.model.pieces.{Bishop, King, Knight, Pawn, Piece, Queen, Rook}
import io.github.chess.model.Team
import io.github.chess.viewcontroller.fxcomponents.pages.components.PieceView
import io.github.chess.viewcontroller.configuration.InterfaceConfiguration.Images
import scalafx.scene.image.Image

/** Companion object of [[PieceView]]. */
object PieceView:
  /**
   * @param piece the specified piece
   * @param team the specified team
   * @return a new view for the specified piece in the specified team
   */
  def apply(piece: Piece, team: Team = Team.WHITE): PieceView =
    val unboundImage: (Image, Image) = piece match
      case _: Pawn   => (Images.Pieces.WhitePawn, Images.Pieces.BlackPawn)
      case _: Knight => (Images.Pieces.WhiteKnight, Images.Pieces.BlackKnight)
      case _: Bishop => (Images.Pieces.WhiteBishop, Images.Pieces.BlackBishop)
      case _: Rook   => (Images.Pieces.WhiteRook, Images.Pieces.BlackRook)
      case _: Queen  => (Images.Pieces.WhiteQueen, Images.Pieces.BlackQueen)
      case _: King   => (Images.Pieces.WhiteKing, Images.Pieces.BlackKing)
      case _ => throw IllegalArgumentException("Unknown images for the specified chess piece.")
    PieceView(if (team == WHITE) unboundImage._1 else unboundImage._2)

/**
 * Handles the view of a chess piece.
 * @param image the image representing this chess piece
 */
case class PieceView private (image: Image)
