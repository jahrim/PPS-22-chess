/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.vertx

import io.github.chess.AbstractSpec
import io.vertx.core.Vertx

import scala.concurrent.Await
import scala.concurrent.duration.{Duration, SECONDS}

/** Test suit for the [[VerticleExecutor]]. */
class VerticleExecutorSpec extends AbstractSpec:
  private val verticleExecutor: VerticleExecutor = VerticleExecutor(Vertx.vertx())
  private val numberOfTasks: Long = 1000
  private val maxDuration: Duration = Duration(5, SECONDS)

  "A verticle executor" should "execute all pending tasks in the same event-loop thread" in {
    java.util.stream.Stream
      .iterate(0, _ + 1)
      .limit(numberOfTasks)
      .parallel()
      .map(_ => verticleExecutor.runLater { Thread.currentThread().getId })
      .map(Await.result(_, maxDuration))
      .distinct()
      .count() shouldBe 1
  }
