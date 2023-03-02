/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.general

import io.github.chess.AbstractSpec
import org.awaitility.Awaitility.*

import java.time.Duration
import java.util.concurrent.TimeUnit
import scala.jdk.DurationConverters.JavaDurationOps

/** Test suite for [[Timer]]. */
class TimerSpec extends AbstractSpec:

  private val oneSecond = Duration.ofSeconds(1)
  private val time = Duration.ofSeconds(3)
  private var timer: Timer = initTimer()

  private def initTimer() = Timer(() => {}, () => {}, time.getSeconds.toInt, TimeUnit.SECONDS)

  before {
    timer = initTimer()
  }

  "A timer" should "start and end correctly" in {
    timer.start()
    await pollDelay oneSecond atMost time.plusSeconds(1) until timer.ended
  }

  it should "be stopped after calling stop" in {
    timer.start()
    timer.stop()
    timer.stopped shouldBe true
  }

  it should "have correct data before starting it" in {
    timer.timeRemaining shouldEqual time.toScala
    timer.stopped shouldBe false
    timer.ended shouldBe false
  }

  it should "continue correctly after stopping it" in {
    timer.start()
    timer.stop()
    timer.continue()
    timer.stopped shouldBe false
  }

  it should "restart correctly after N seconds" in {
    timer.start()
    await pollDelay oneSecond until timer.timeRemaining == oneSecond.toScala
    timer.restart()
    await pollDelay oneSecond until timer.ended
  }

  it should "be resettable if stopped" in {
    timer.start()
    await pollDelay oneSecond until true
    timer.stop()
    timer.reset()
    timer.timeRemaining shouldEqual time.toScala
  }

  it should "not be resettable if not stopped" in {
    timer.start()
    await pollDelay oneSecond until true
    timer.reset()
    timer.timeRemaining should not equal time.toScala
  }

  it should "not be runnable if stopped" in {
    timer.start()
    timer.stop()
    an[IllegalStateException] should be thrownBy timer.start()
  }

  "A fake timer" should "be already ended without starting" in {
    val timer = Timer.fake
    timer.ended shouldBe true
    timer.timeRemaining shouldEqual Duration.ZERO.toScala
  }

  it should "not be started" in {
    val timer = Timer.fake
    an[IllegalStateException] should be thrownBy timer.start()
  }
