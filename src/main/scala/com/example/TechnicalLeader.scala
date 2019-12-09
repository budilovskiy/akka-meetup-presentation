package com.example

import akka.actor.{Actor, ActorLogging}

case class Develop(projectName: String, tasks: Seq[Task])

class TechnicalLeader(name: String) extends Actor with ActorLogging {
  def receive: Receive = {
    case Develop(projectName, tasks) =>
      log.info(s"Starting to develop $projectName.")
  }
}
