/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.model

import io.github.chess.AbstractSpec
import org.scalatest.flatspec.AnyFlatSpec

/** Test suite for [[Position]]. */
class PositionSpec extends AbstractSpec:

  private final val StartingFile = File.A
  private final val StartingRank = Rank._1
  private val position: Position = Position(StartingFile, StartingRank)

  "A Position" should "have the same File and Rank initialized with" in {
    position.file shouldEqual StartingFile
    position.rank shouldEqual StartingRank
  }

  it should "be equal to another Position with same File and Rank" in {
    val samePosition = Position(StartingFile, StartingRank)
    position shouldEqual samePosition
  }

  it should "be different to another Position with different File or Rank" in {
    val diffPosition = Position(File.B, StartingRank)
    position should not equal diffPosition
  }

  it should "have the same file and the rank upped by one, when ranked up by one" in {
    val newPosition = position.rankUp()
    newPosition.rank shouldEqual StartingRank.up()
    newPosition.file shouldEqual StartingFile
  }

  it should "have the same file and a rank downed by one, when ranked down by one" in {
    val initialRank = Rank._5
    val newPosition = Position(StartingFile, initialRank).rankDown()
    newPosition.rank shouldEqual initialRank.down()
    newPosition.file shouldEqual StartingFile
  }

  it should "have the same rank and the file forwarded by one, when forwarded by one" in {
    val newPosition = position.fileForward()
    newPosition.rank shouldEqual StartingRank
    newPosition.file shouldEqual StartingFile.forward()
  }

  it should "have the same rank and the file backed off by one, when backed off by one" in {
    val initialFile = File.D
    val newPosition = Position(initialFile, StartingRank).fileBackward()
    newPosition.rank shouldEqual StartingRank
    newPosition.file shouldEqual initialFile.backward()
  }
