/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.game

import io.github.chess.AbstractSpec
import io.github.chess.engine.events.Event
import io.github.chess.engine.model.board.Position
import io.github.chess.engine.model.pieces.PromotionPiece
import io.github.chess.engine.model.configuration.GameConfiguration
import io.github.chess.engine.model.game.exceptions.*
import io.github.chess.engine.model.game.ChessGame
import io.github.chess.engine.model.game.ChessGameState.*
import io.github.chess.engine.model.moves.Move
import io.github.chess.engine.ports.ChessPort
import io.vertx.core.Vertx

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, SECONDS}

class ChessGameSpec extends AbstractSpec:
  val blackPawnPosition: Position = (0, 6)
  val whitePawnPosition: Position = (0, 1)
  val whitePawnMove: Move = Move(whitePawnPosition, (0, 3))
  val maxDuration: Duration = Duration(5, SECONDS)

  "Before starting, a chess game" should "not be configured" in {
    Await.result(notConfiguredGame.getState, maxDuration) shouldBe a[NotConfigured]
  }

  it should "allow the player to start the game" in {
    val chessGame = notConfiguredGame
    noException should be thrownBy {
      Await.result(chessGame.startGame(GameConfiguration.default), maxDuration)
      Await.result(chessGame.getState, maxDuration) shouldBe a[Running]
    }
  }

  it should "forbid the player from finding the moves of any piece" in {
    a[GameNotRunningException] should be thrownBy {
      Await.result(notConfiguredGame.findMoves(whitePawnPosition), maxDuration)
    }
  }

  it should "forbid the player from moving any piece" in {
    a[GameNotRunningException] should be thrownBy {
      Await.result(notConfiguredGame.applyMove(whitePawnMove), maxDuration)
    }
  }

  it should "forbid the player from promoting any pawn" in {
    a[GameNotAwaitingPromotionException] should be thrownBy {
      Await.result(
        notConfiguredGame.promote(whitePawnPosition, PromotionPiece.QueenPromotion),
        maxDuration
      )
    }
  }

  it should "forbid the player from surrendering" in {
    a[GameNotRunningException] should be thrownBy {
      Await.result(
        notConfiguredGame.surrender(GameConfiguration.default.whitePlayer),
        maxDuration
      )
    }
  }

  it should "allow the player to subscribe to the game events preemptively" in {
    noException should be thrownBy {
      Await.result(notConfiguredGame.subscribe[Event](_ => {}), maxDuration)
    }
  }

  it should "allow the player to unsubscribe from the game events" in {
    noException should be thrownBy {
      Await.result(notConfiguredGame.unsubscribe("fakeSubscriptionId"), maxDuration)
    }
  }

  "After starting, a standard chess game" should "be running" in {
    Await.result(configuredGame.getState, maxDuration) shouldBe a[Running]
  }

  it should "forbid the player from starting the game again" in {
    a[GameAlreadyConfiguredException] should be thrownBy {
      Await.result(configuredGame.startGame(GameConfiguration.default), maxDuration)
    }
  }

  it should "allow the player to find the moves of any of his pieces" in {
    noException should be thrownBy {
      Await.result(configuredGame.findMoves(whitePawnPosition), maxDuration) shouldNot be(empty)
    }
  }

  it should "forbid the player to find the moves of any of the opponent's pieces" in {
    noException should be thrownBy {
      Await.result(configuredGame.findMoves(blackPawnPosition), maxDuration) should be(empty)
    }
  }

  it should "allow the player to move any of his pieces" in {
    noException should be thrownBy {
      val chessGame = configuredGame
      Await.result(chessGame.applyMove(whitePawnMove), maxDuration)
      Await.result(chessGame.getState, maxDuration) match
        case Running(status) =>
          status.chessBoard.pieces.get(whitePawnMove.to) should be(defined)
        case _ =>
          fail("The game is not running after applying the move")
    }
  }

  it should "forbid the player from promoting any pawn" in {
    a[GameNotAwaitingPromotionException] should be thrownBy {
      Await.result(
        configuredGame.promote(whitePawnPosition, PromotionPiece.QueenPromotion),
        maxDuration
      )
    }
  }

  it should "allow the player to surrender" in {
    val chessGame = configuredGame
    noException should be thrownBy {
      Await.result(chessGame.surrender(GameConfiguration.default.whitePlayer), maxDuration)
      Await.result(chessGame.getState, maxDuration) shouldBe a[NotConfigured]
    }
  }

  it should "allow the player to subscribe to the game events" in {
    noException should be thrownBy {
      Await.result(configuredGame.subscribe[Event](_ => {}), maxDuration)
    }
  }

  it should "allow the player to unsubscribe from the game events" in {
    noException should be thrownBy {
      Await.result(configuredGame.unsubscribe("fakeSubscriptionId"), maxDuration)
    }
  }

  it should "change the turn after a move is applied" in {
    val chessGame = configuredGame
    Await.result(chessGame.getState, maxDuration) match
      case Running(statusBeforeTheMove) =>
        Await.result(chessGame.applyMove(whitePawnMove), maxDuration)
        Await.result(chessGame.getState, maxDuration) match
          case Running(statusAfterTheMove) =>
            statusAfterTheMove.currentTurn should be(statusBeforeTheMove.currentTurn.oppositeTeam)
          case _ => fail("The game is not running anymore after applying the move")
      case _ => fail("The game is not running before applying the move")
  }

  /** @return a chess game that hasn't been configured yet. */
  private def notConfiguredGame: ChessGame = ChessGame(Vertx.vertx())

  /** @return a chess game that has been configured and started. */
  private def configuredGame: ChessGame =
    val chessGame: ChessGame = notConfiguredGame
    Await.result(chessGame.startGame(GameConfiguration.default), maxDuration)
    chessGame
