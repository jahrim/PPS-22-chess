/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.events

import io.github.chess.events.Event
import io.github.chess.model.Team

// TODO quando si fa la subscribe si deve istanziare l'evento per sapere il suo indirizzo
/** Represents the end of the turn. */
case class EndTurnEvent(nextTeam: Team = Team.WHITE, nextPlayer: String = "") extends Event:

  override protected val specificAddress: String = "endTurn"
