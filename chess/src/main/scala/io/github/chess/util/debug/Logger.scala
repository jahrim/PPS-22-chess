/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.debug

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/** A simple logger. */
object Logger:
  // Set this to false to ensure that no logs remains in any reachable code (default: true)
  private val enabled: Boolean = true
  // Set this to true to hide all logs (default: false)
  private var hidden: Boolean = false

  /** The type of a message. */
  type Tag = String

  /** The topic of a message. */
  type Category = String

  /** The content of a message. */
  type Message = Any

  /** Show all logs. */
  def showLogs(): Unit = this.hidden = false

  /** Hide all logs. */
  def hideLogs(): Unit = this.hidden = true

  /**
   * Prints the specified message without any tags or categories.
   * @param message the specified message
   */
  def put(message: Message): Unit = log("", "", message)

  /**
   * Logs the start and the end of the specified activity.
   * @param tag the type of message
   * @param category the topic of the message
   * @param activityName the name of the specified activity
   * @param activity the specified activity
   * @tparam T the return type of the specified activity
   * @return the result of the specified activity
   */
  def logActivity[T](tag: Tag, category: Category)(activityName: String)(activity: => T): T =
    Logger.log(tag, category, s"$activityName : started")
    val result = activity
    Logger.log(tag, category, s"$activityName : ended")
    result

  /**
   * Prints the specified message as an information.
   * @param category the category of the message
   * @param message the specified message
   */
  def info(category: Category, message: Message): Unit = log("INFO", category, message)

  /**
   * Prints the specified message as a warning.
   * @param category the category of the message
   * @param message  the specified message
   */
  def warning(category: Category, message: Message): Unit = log("WARNING", category, message)

  /**
   * Prints the specified message as an error.
   * @param category the category of the message
   * @param message  the specified message
   */
  def error(category: Category, message: Message): Unit = log("ERROR", category, message)

  /**
   * Prints the specified message as a debug message.
   * @param category the category of the message
   * @param message  the specified message
   */
  def debug(category: Category, message: Message): Unit = log("DEBUG", category, message)

  /**
   * Prints the specified message.
   * @param tag the type of message
   * @param category the topic of the message
   * @param message the content of the message
   */
  protected def log(tag: Tag, category: Category, message: Message): Unit =
    require(enabled, "Cannot use logger when logging it's disabled.")
    if !this.hidden then
      println(s"[${currentTime}][${currentActor}]: [$tag][$category] - ${message.toString}")

  /** @return the current time formatted as a string */
  private def currentTime: String =
    LocalDateTime.now().format(DateTimeFormatter.ISO_TIME).substring(0, 12)

  /** @return the name of the actor that is currently logging */
  private def currentActor: String = Thread.currentThread().getName
