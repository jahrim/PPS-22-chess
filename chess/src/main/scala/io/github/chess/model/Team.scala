/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.model.Team.{BLACK, WHITE}

/** Enumeration representing the Team concept of a chess game. */
enum Team:

  /** Value representing the White team. */
  case WHITE

  /** Value representing the Black team */
  case BLACK

  /**
   * Gives the opposite team of this.
   * @return The team that is the opposite of this
   */
  def oppositeTeam: Team = this match
    case WHITE => BLACK
    case BLACK => WHITE
