/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.prolog

/** Class representing the prolog rule that finds all capture moves of a white pawn. */
class WhitePawnCaptureRule extends PrologRule("white_pawn_capture") with InsideBoardFilterRule()
