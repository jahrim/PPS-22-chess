/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules.chess.pawn

import io.github.chess.model.rules.chess.AvoidAllPiecesRule

/** Implementation of the rule that makes move a piece two positions forward in the column, if there is no other piece there. */
class DoubleMoveRule extends TwoStepRule with AvoidAllPiecesRule
