package com.example

import akka.actor.{Actor, ActorLogging}

case class WorkOnTask(task: Task)
case class TaskFinished(task: Task)

class Developer extends Actor with ActorLogging {
  def receive: Receive = {
    case WorkOnTask(task) =>
      log.info(s"Start working on $task")
      val doneTask = task.copy(done = true)
      sender() ! TaskFinished(doneTask)
  }
}
