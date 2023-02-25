/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.knight

import io.github.chess.model.rules.chess.AvoidAlliesRule

/** Represent the movement rule for the [[Knight]]. */
class KnightRule extends LRule with AvoidAlliesRule
