package com.example

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

case class TestedTask(task: Task)

class QaLeader(name: String)  extends Actor with ActorLogging {

  val testers: Seq[ActorRef] = List(
    context.actorOf(Props(new Qa()), "QA_1"),
    context.actorOf(Props(new Qa()), "QA_2")
  )

  def receive: Receive = {

    case command: TaskForTest =>
      val randomTester: ActorRef = testers(scala.util.Random.nextInt(testers.length))
      randomTester ! command

    case command: TestedTask =>
      context.parent ! command
  }

}
