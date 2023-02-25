/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.queen

import io.github.chess.model.moves.Move
import io.github.chess.model.rules.chess.AvoidAlliesRule
import io.github.chess.model.{ChessGameStatus, Position}

/** Represent the movement rule for the [[Queen]]. */
class QueenRule extends AllDirectionsRule with AvoidAlliesRule
