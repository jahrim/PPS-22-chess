/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.model.game

import io.github.chess.AbstractSpec
import io.github.chess.engine.model.board.ChessBoard
import io.github.chess.engine.model.board.ChessBoard.*
import io.github.chess.engine.model.game.ChessGameAnalyzer.*
import io.github.chess.engine.model.game.{ChessGameAnalyzer, ChessGameStatus}

/** Test suit for the [[ChessGameAnalyzer]]. */
class ChessGameAnalyzerSpec extends AbstractSpec:

  "A chess board analyzer" should "verify that in the standard configuration of a chess board there is no particular situation" in {
    ChessGameAnalyzer.situationOf(ChessGameStatus()) shouldNot be(defined)
  }

  it should "be able to recognize a check" in {
    ChessGameAnalyzer.situationOf(
      ChessGameStatus(
        ChessBoard {
          K | * | * | **
          * | * | * | **
          * | * | b | ** { 6 }
        }
      ),
      teamPerspective = Team.WHITE
    ) shouldEqual Some(Check)
  }

  it should "be able to recognize a stale" in {
    ChessGameAnalyzer.situationOf(
      ChessGameStatus(
        ChessBoard {
          K | * | * | **
          * | r | r | ** { 7 }
        }
      ),
      teamPerspective = Team.WHITE
    ) shouldEqual Some(Stale)
  }

  it should "be able to recognize a checkmate" in {
    ChessGameAnalyzer.situationOf(
      ChessGameStatus(
        ChessBoard {
          K | * | q | **
          * | r | * | ** { 7 }
        }
      ),
      teamPerspective = Team.WHITE
    ) shouldEqual Some(CheckMate)
  }
