/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.prolog

import io.github.chess.model.PrologEngine

/** Represents the prolog rule to find all the coordinates in the south east direction from a starting position. */
class SouthEastRule extends PrologRule("south_east") with InsideBoardRule()
