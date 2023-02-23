/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.pawn

/** The rule that analyzes Pawn "en passant" capture rule to say if it can perform it or not. */
class EnPassantRule extends PawnCaptureMoves with EnPassantFilterRule
