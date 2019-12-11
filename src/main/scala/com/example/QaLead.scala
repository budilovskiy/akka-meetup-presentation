package com.example

import akka.actor.{Actor, ActorLogging}

case class TestedTask(task: Task)

class QaLeader(name: String)  extends Actor with ActorLogging {

  def receive: Receive = Actor.emptyBehavior

}
