package com.example

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

case class TestedTask(task: Task)

class QaLeader(name: String)  extends Actor with ActorLogging {

  val testers: Seq[ActorRef] = List(
    context.actorOf(Props(new Qa()), "QA_1"),
    context.actorOf(Props(new Qa()), "QA_2")
  )

  def receive: Receive = {

    case TaskForTest(task) =>
      val randomTester: ActorRef = testers(scala.util.Random.nextInt(testers.length))
      randomTester ! task

    case command: TestedTask =>
      context.parent ! command
  }

}
