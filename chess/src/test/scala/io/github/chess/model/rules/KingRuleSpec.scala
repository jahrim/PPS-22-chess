/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model.rules

import io.github.chess.AbstractSpec
import io.github.chess.model.{ChessBoard, ChessGameStatus, Position}
import io.vertx.core.Vertx

/** Test suite for [[King]]. */
class KingRuleSpec extends AbstractSpec:

  private val initialPosition: Position = (1, 2)
  private val exactPositions: Set[Position] =
    Set((0, 2), (0, 3), (1, 1), (1, 3), (2, 1), (2, 2), (2, 3))
  private val rule = KingMovementRule()
  private val chessGameStatus = ChessGameStatus(ChessBoard(Vertx.vertx()))
  private val moves = rule.findMoves(initialPosition, chessGameStatus).map(_.to)

  "The AllDirectionsOneStepRule rule" should "let move the king in all the directions stepped by one" in {
    moves shouldEqual exactPositions
  }
