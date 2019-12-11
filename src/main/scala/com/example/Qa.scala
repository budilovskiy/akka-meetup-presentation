package com.example

import akka.actor.{Actor, ActorLogging}
import akka.pattern.pipe

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._
import scala.util.Random

case class TaskForTest(task: Task)

class Qa extends Actor with ActorLogging {
  implicit val ec: ExecutionContext = context.dispatcher

  def receive: Receive = {
    case task: Task =>
      log.info(s"Start testing $task")
      val testedTask = task.copy(testSucceeded = Random.nextBoolean())

      val eventualTaskFinished =
        akka.pattern.after(2.seconds, context.system.scheduler) {
          Future.successful(TestedTask(testedTask))
        }

      pipe(eventualTaskFinished) to sender

  }
}