/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.engine.events

import scala.reflect.{ClassTag, classTag}

/** Represents every event happened in the model. */
trait Event

/** Companion object of [[Event]]. */
object Event:
  /**
   * @tparam T the specified type of events
   * @return the address of the specified type of events
   */
  def addressOf[T <: Event: ClassTag]: String = classTag[T].runtimeClass.getSimpleName
