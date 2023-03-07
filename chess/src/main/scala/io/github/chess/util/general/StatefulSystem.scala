/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.general

/**
 * A system with a state.
 * @tparam S the type of the state of the system
 */
trait StatefulSystem[S](protected val initialState: S):
  private var state: S = this.initialState

  /** @return the state of the system */
  def getState: S = this.state

  /**
   * Exits the current state and enters the specified state.
   * @param state the specified state
   * @return this
   */
  def enter(state: S): StatefulSystem[S] =
    this.exitBehavior(this.state)
    this.state = state
    this.enterBehavior(this.state)
    this

  /**
   * The behavior of this system when it enters a state.
   * @param state the state entered by the system
   */
  protected def enterBehavior(state: S): Unit

  /**
   * The behavior of this system when it exits a state.
   * @param state the state exited by the system
   */
  protected def exitBehavior(state: S): Unit
