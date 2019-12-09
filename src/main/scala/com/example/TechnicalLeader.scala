package com.example

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

case class Develop(projectName: String, tasks: Seq[Task])

class TechnicalLeader(name: String) extends Actor with ActorLogging {
  def receive: Receive = {
    case Develop(projectName, tasks) =>
      log.info(s"Starting to develop $projectName.")

      val developers: Seq[ActorRef] = List(
        context.actorOf(Props(new Developer()), "developer_1"),
        context.actorOf(Props(new Developer()), "developer_2")
      )

      tasks.filterNot(_.done).foreach { task: Task =>
        val randomDeveloper: ActorRef = developers(scala.util.Random.nextInt(developers.length))
        randomDeveloper ! WorkOnTask(task: Task)
      }
  }
}
