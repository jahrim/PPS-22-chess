/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.pawn

import io.github.chess.engine.model.rules.chess.AvoidSelfCheckRule

/** Implementation of the [[ChessRule]] that applies the rules of the [[Pawn]]. */
class PawnRule extends PawnRuleCollection with AvoidSelfCheckRule
