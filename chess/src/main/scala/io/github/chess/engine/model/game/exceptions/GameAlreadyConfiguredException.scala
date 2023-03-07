/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.game.exceptions

/**
 * Exception thrown when a user tries to configure a new game
 * when one game is already configured.
 */
class GameAlreadyConfiguredException
    extends IllegalStateException(
      "Cannot configure a new game before the previous one has ended."
    )
