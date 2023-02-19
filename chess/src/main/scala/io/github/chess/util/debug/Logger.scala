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
  // Set this to false to ensure that no logs remains in the code (default: true)
  private val enabled: Boolean = true

  /** The type of a message. */
  type Tag = String

  /** The topic of a message. */
  type Category = String

  /** The content of a message. */
  type Message = Any

  /**
   * Prints the specified message without any tags or categories.
   * @param message the specified message
   */
  def put(message: Message): Unit = log("", "", message)

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
    println(s"[${currentTime}][${currentActor}]: [$tag][$category] - ${message.toString}")

  /** @return the current time formatted as a string */
  private def currentTime: String =
    LocalDateTime.now().format(DateTimeFormatter.ISO_TIME).substring(0, 12)

  /** @return the name of the actor that is currently logging */
  private def currentActor: String = Thread.currentThread().getName
