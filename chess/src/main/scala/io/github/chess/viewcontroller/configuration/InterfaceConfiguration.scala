/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.viewcontroller.configuration

import scalafx.Includes.*
import scalafx.scene.paint.Color
import scalafx.scene.image.Image

/** Configuration of the ChessGame interface. */
object InterfaceConfiguration:
  /** Palette of the ChessGame interface. */
  object Colors:
    val LightBrownCell: Color = Color.web("#F9D19D")
    val DarkBrownCell: Color = Color.web("#D18C47")
    val SelectedCell: Color = Color.web("#D5E8D4")

  /** Images of the ChessGame interface. */
  object Images:
    object Pieces:
      val WhitePawn: Image = Image("/images/chess-pieces/white-pawn.png")
      val BlackPawn: Image = Image("/images/chess-pieces/black-pawn.png")
      val WhiteKnight: Image = Image("/images/chess-pieces/white-knight.png")
      val BlackKnight: Image = Image("/images/chess-pieces/black-knight.png")
      val WhiteBishop: Image = Image("/images/chess-pieces/white-bishop.png")
      val BlackBishop: Image = Image("/images/chess-pieces/black-bishop.png")
      val WhiteRook: Image = Image("/images/chess-pieces/white-rook.png")
      val BlackRook: Image = Image("/images/chess-pieces/black-rook.png")
      val WhiteQueen: Image = Image("/images/chess-pieces/white-queen.png")
      val BlackQueen: Image = Image("/images/chess-pieces/black-queen.png")
      val WhiteKing: Image = Image("/images/chess-pieces/white-king.png")
      val BlackKing: Image = Image("/images/chess-pieces/black-king.png")
    object Effects:
      val AvailableMove: Image = Image("/images/effects/available-move.png")
    object Icons:
      val GameIcon: Image = Image("/images/icons/game-icon.png")
