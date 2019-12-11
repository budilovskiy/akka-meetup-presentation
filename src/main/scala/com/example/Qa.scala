package com.example

import akka.actor.{Actor, ActorLogging}
import scala.util.Random

case class TaskForTest(task: Task)

class Qa extends Actor with ActorLogging {
  def receive: Receive = {
    case TaskForTest(task) =>
      log.info(s"Start testing $task")
      val testedTask = task.copy(succeeded = Random.nextBoolean())
      sender() ! TestedTask(testedTask)
  }
}