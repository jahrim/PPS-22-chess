/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess

import io.github.chess.util.scala.debug.Logger
import org.awaitility.scala.AwaitilitySupport
import org.junit.runner.RunWith
import org.scalatest.BeforeAndAfter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.junit.JUnitRunner

/** Base Spec for all the test suites. */
@RunWith(classOf[JUnitRunner])
abstract class AbstractSpec
    extends AnyFlatSpec
    with Matchers
    with AwaitilitySupport
    with BeforeAndAfter:
  Logger.hideLogs()
