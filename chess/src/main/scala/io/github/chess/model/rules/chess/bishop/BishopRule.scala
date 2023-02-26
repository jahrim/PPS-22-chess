/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.bishop

import io.github.chess.model.rules.chess.{AvoidAlliesRule, AvoidSelfCheckRule}

/** Represent the movement rule for the [[Bishop]]. */
class BishopRule extends DiagonalRule with AvoidAlliesRule with AvoidSelfCheckRule
