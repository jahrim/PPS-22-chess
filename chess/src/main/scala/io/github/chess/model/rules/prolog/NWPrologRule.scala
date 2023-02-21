/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.prolog

/** Class representing the prolog rule that finds all moves in the North-West direction. */
class NWPrologRule() extends PrologRule("nw_move") with InsideBoardRule()