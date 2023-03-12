/*
 * MIT License
 * Copyright (c) 2023 Cesario Jahrim Gabriele & Derevyanchenko Maxim & Felice Mirko & Kentpayeva Madina
 *
 * Full license description available at: https://github.com/jahrim/PPS-22-chess/blob/master/LICENSE
 */
package io.github.chess.util.vertx

import io.github.chess.util.scala.id.Id
import io.github.chess.util.vertx.VerticleExecutor.*
import io.vertx.core.Vertx
import io.vertx.core.eventbus.Message

import scala.collection.concurrent.{Map, TrieMap}
import scala.concurrent.{Future, Promise}
import scala.util.Try

/**
 * An executor that executes in the event-loop of a local verticle.
 * When a [[VerticleExecutor]] is created during the deployment of a verticle,
 * the executor automatically binds to the event loop of that verticle.
 * When a [[VerticleExecutor]] is created outside the deployment of a verticle,
 * the executor automatically binds to an event-loop thread of the vertx instance.
 * @param vertx the vertx instance of the verticle bound to this executor
 */
class VerticleExecutor(private val vertx: Vertx):
  private val address: String = Id()
  private val pendingTasks: Map[TaskId, PendingTask[_]] = TrieMap.empty

  this.vertx.eventBus().localConsumer(this.address, this.runPendingTask)

  /**
   * Executes the specified task in the event-loop of this verticle.
   * @param task the specified task
   * @tparam T the return type of the specified task
   * @return a future containing the result of the specified task.
   */
  def runLater[T](task: => T): Future[T] =
    val (taskId, pendingTask) = Id() -> PendingTask(task)
    this.pendingTasks.update(taskId, pendingTask)
    this.vertx.eventBus().publish(this.address, taskId)
    pendingTask.future

  /**
   * Message handler that executes a pending task given its id.
   * @param message a message containing the id of the task to execute
   */
  private def runPendingTask(message: Message[TaskId]): Unit =
    this.pendingTasks.get(message.body()).foreach(_.execute())
    this.pendingTasks.remove(message.body())

/** Companion object of [[VerticleExecutor]]. */
object VerticleExecutor:
  /** An identifier for a task. */
  private type TaskId = String

  /** A task that will be executed in the future. */
  private class PendingTask[T](task: => T):
    private val promise: Promise[T] = Promise()

    /** Executes this task if it has not been executed yet. */
    def execute(): Unit = if !this.promise.isCompleted then this.promise.complete(Try(task))

    /** @return a future containing the result of this task. */
    def future: Future[T] = this.promise.future
