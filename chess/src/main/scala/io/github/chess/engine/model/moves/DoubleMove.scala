/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.moves

import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.game.Team
import io.github.chess.engine.model.pieces.Piece

/** Represents the moving of a pawn by two steps forward. */
trait DoubleMove extends Move:

  /**
   * Gives the position skipped while performing this move.
   *
   * @return position between the starting position and arrival
   */
  def middlePosition: Position

/** Factory for [[DoubleMove]] instances. */
object DoubleMove:

  /**
   * Creates a double move, using the two needed positions.
   *
   * @param from  source [[Position]]
   * @param piece the piece on which the move will be applied
   * @return a new [[DoubleMove]]
   */
  def apply(from: Position, piece: Piece): DoubleMove = PawnDoubleMove(
    from,
    piece.team match
      case Team.WHITE => from.rankUp().rankUp()
      case Team.BLACK => from.rankDown().rankDown()
  )

  private case class PawnDoubleMove(
      override val from: Position,
      override val to: Position
  ) extends DoubleMove:

    override val middlePosition: Position =
      if from._2 > to._2
      then from.rankDown()
      else from.rankUp()
