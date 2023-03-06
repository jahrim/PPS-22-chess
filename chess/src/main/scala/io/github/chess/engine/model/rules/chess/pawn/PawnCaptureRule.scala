/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.rules.chess.pawn

import io.github.chess.engine.model.rules.chess.OnlyCaptureEnemies

/** The rule that analyzes Pawn base capture rule to say if it can perform it or not. */
class PawnCaptureRule extends PawnCaptureMoves with OnlyCaptureEnemies
