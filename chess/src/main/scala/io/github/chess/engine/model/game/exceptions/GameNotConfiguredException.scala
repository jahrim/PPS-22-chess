/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.game.exceptions

/**
 * Exception thrown when a user requests an invalid operation
 * when the game has not started yet.
 */
class GameNotConfiguredException
    extends IllegalStateException(
      "Can only perform this operation when the game has already started."
    )
