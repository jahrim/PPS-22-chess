/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.pieces

/** Represents all the possible pieces that the pawn can be promoted to. */
enum PromotionPiece[P <: Piece](val pieceClass: Class[P]):

  /** Represents the promotion to a [[Queen]]. */
  case QueenPromotion extends PromotionPiece(classOf[Queen])

  /** Represents the promotion to a [[Rook]]. */
  case RookPromotion extends PromotionPiece(classOf[Rook])

  /** Represents the promotion to a [[Bishop]]. */
  case BishopPromotion extends PromotionPiece(classOf[Bishop])

  /** Represents the promotion to a [[Knight]]. */
  case KnightPromotion extends PromotionPiece(classOf[Knight])

  override def toString: String = pieceClass.getSimpleName
