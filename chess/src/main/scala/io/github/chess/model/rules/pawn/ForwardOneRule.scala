/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.pawn

import io.github.chess.model.{Move, Position}
import io.github.chess.model.rules.ChessRule

/** Implementation of the Pawn rule that makes it move one step forward. */
class ForwardOneRule extends ChessRule:

  override def findMoves(position: Position): Set[Move] = Set(Move(position, position.rankUp()))
