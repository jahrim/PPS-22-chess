/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.game.exceptions

/**
 * Exception throws when a user requests an invalid operation
 * when the game is not awaiting for him to promote a piece.
 */
class GameNotAwaitingPromotionException
    extends IllegalStateException(
      "Can only perform this operation while the game is awaiting promotion."
    )
