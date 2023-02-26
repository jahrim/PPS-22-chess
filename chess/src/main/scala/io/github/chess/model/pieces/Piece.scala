/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.pieces

import io.github.chess.model.moves.Move
import io.github.chess.model.rules.chess.ChessRule
import io.github.chess.model.{ChessBoard, ChessGameStatus, Position, Team}

/** Represents every piece of the [[ChessBoard]]. */
trait Piece:

  /** @return the team who owns this piece */
  def team: Team

  /** Returns the [[ChessRule]] able to give all the possible moves from a starting position. */
  def rule: ChessRule

/** Companion object of [[Piece]]. */
object Piece:
  /**
   * @param team the specified team
   * @return a placeholder of a [[Piece]] of the specified team
   */
  def apply(team: Team = Team.WHITE): Piece = PiecePlaceholder(team)

  /** A placeholder of a [[Piece]]. */
  private case class PiecePlaceholder(override val team: Team) extends Piece:
    override def rule: ChessRule = (_: Position, _: ChessGameStatus) => Set.empty[Move]
