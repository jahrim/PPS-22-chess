/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.pawn

import io.github.chess.model.rules.chess.BaseCaptureRule

/** The rule that analyzes Pawn specific capture rules to say if it can perform them or not. */
class PawnCaptureRule extends PawnCaptureMoves with BaseCaptureRule
