/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.adapters

/**
 * Represents an adapter that allows to interact with a specific port of a service,
 * exposing the port through a specific technology (i.e. HTTP, MQTT...).
 */
trait Adapter[Port]:

  /** @return the port exposed by this adapter */
  protected def port: Port
