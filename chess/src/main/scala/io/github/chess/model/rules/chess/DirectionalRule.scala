/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess

import io.github.chess.util.general.GivenExtension.within
import io.github.chess.model.{ChessGameStatus, Position}
import io.github.chess.model.Position.given
import io.github.chess.model.moves.Move
import io.github.chess.model.rules.prolog.PrologRule
import ChessRule.*

/** A rule that performs an analysis towards a certain direction. */
trait DirectionalRule extends ChessRule with RuleShorthands:
  export DirectionalRule.*
  export DirectionalRule.given

  override def findMoves(position: Position, status: ChessGameStatus): Set[Move] =
    this.directions.flatMap { limitDirection(position, status, _) }

  /** @return all the possible directions of the rule */
  def directions: Set[Direction]

  /**
   * Analyzes the specified direction and removes all the moves that cannot be performed due to an obstacle.
   * The move that end up on an occupied cell is kept.
   *
   * @param startingPosition the starting position
   * @param status          the current state of the game
   * @param direction       the specified direction
   * @return the set of the moves found, without the moves that would end up behind an obstacle,
   *         if there is one in the path
   */
  private def limitDirection(
      startingPosition: Position,
      status: ChessGameStatus,
      direction: Direction
  ): Set[Move] =
    within(status) {
      val (availablePositions, obstructedPositions) = direction(startingPosition).span(!occupied(_))
      (availablePositions ++ obstructedPositions.take(1)).map(Move(startingPosition, _)).toSet
    }

/** Companion object of [[DirectionalRule]]. */
object DirectionalRule:
  /** A direction is a function that takes a position and returns an ordered sequence of positions. */
  type Direction = Position => Seq[Position]

  /** Conversion from a prolog rule to a direction. */
  given prologRuleToDirection: Conversion[PrologRule, Direction] =
    rule => position => rule.findPositions(position).map(coordsToPosition)
