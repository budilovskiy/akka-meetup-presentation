package com.example

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

case class Develop(projectName: String, tasks: Seq[Task])
case class FailedTask(task: Task)

class TechnicalLeader(name: String) extends Actor with ActorLogging {

  val developers: Seq[ActorRef] = List(
    context.actorOf(Props(new Developer()), "developer_1"),
    context.actorOf(Props(new Developer()), "developer_2")
  )

  private def randomDeveloper: ActorRef = {
    val randomDeveloper: ActorRef = developers(scala.util.Random.nextInt(developers.length))
    randomDeveloper
  }

  def receive: Receive = {
    case Develop(projectName, tasks) =>
      log.info(s"Starting to develop $projectName.")

      tasks.filterNot(_.done).foreach { task: Task => randomDeveloper ! task }

      context.become(projectWork)
  }

  def projectWork: Receive = {
    case TaskFinished(task: Task) =>
      context.parent ! TaskForTest(task)
    case FailedTask(task) =>
      randomDeveloper ! task
  }
}
