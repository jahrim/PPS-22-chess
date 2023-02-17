/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.prolog

import io.github.chess.model.rules.prolog.PrologRule

/** Class representing the prolog rule that finds all capture moves of a black pawn. */
class BlackPawnCaptureRule extends PrologRule("black_pawn_capture") with InsideBoardRule()
