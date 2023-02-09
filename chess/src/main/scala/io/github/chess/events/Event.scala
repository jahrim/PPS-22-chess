/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.events

/** Represents every event happened in the model. */
trait Event:

  private final val EventStartAddress = "events/"

  /** Specific address to override to make event address unique. */
  protected val specificAddress: String

  /** Unique event address to communicate through. */
  final def address: String = EventStartAddress + specificAddress
