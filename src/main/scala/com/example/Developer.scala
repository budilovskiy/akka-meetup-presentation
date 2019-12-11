package com.example

import akka.actor.{Actor, ActorLogging}
import akka.pattern.pipe

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

case class TaskFinished(task: Task)

class Developer extends Actor with ActorLogging {
  implicit val ec: ExecutionContext = context.dispatcher

  def receive: Receive = {
    case task: Task =>
      log.info(s"Start working on $task")
      val doneTask = task.copy(done = true)

      val eventualTaskFinished =
        akka.pattern.after(5.seconds, context.system.scheduler){
          Future.successful(TaskFinished(doneTask))
        }

      pipe(eventualTaskFinished) to sender
  }
}
