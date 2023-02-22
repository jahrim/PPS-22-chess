/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.configuration

/** Represents the possible time constraints available to the chess game. */
enum TimeConstraint(var minutes: Int = TimeConstraint.NoMinutesPerMove):

  /** Represents a no limit time constraint. */
  case NoLimit extends TimeConstraint

  /** Represents a time limit per move, defaulting to 5 minutes per move. */
  case MoveLimit extends TimeConstraint(TimeConstraint.DefaultMinutesPerMove)

object TimeConstraint:

  private final val NoMinutesPerMove = -1

  private final val DefaultMinutesPerMove = 5

  /** Minimum time limit per move. */
  final val MinMoveLimit: Int = 1

  /** Maximum time limit per move. */
  final val MaxMoveLimit: Int = 30
