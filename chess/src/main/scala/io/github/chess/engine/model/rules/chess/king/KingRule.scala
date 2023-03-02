/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.king

import io.github.chess.engine.model.rules.chess.AvoidSelfCheckRule

/** Represents the chess rule that can find the moves of the [[King]]. */
class KingRule extends KingRuleCollection with AvoidSelfCheckRule
