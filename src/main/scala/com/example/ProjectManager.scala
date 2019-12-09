package com.example

import akka.actor.{Actor, ActorLogging}

case class StartProject(projectName: String)
object Finish

class ProjectManager(name: String) extends Actor with ActorLogging {
  def Idle: Receive = {
    case StartProject(projectName) =>
      log.info(s"Starting project $projectName.")
      context.become(Working(projectName))
  }

  def Working(projectName: String): Receive = {
    case Finish =>
      log.info(s"Finish current project $projectName.")
      context.become(Idle)
  }

  def receive: Receive = Idle
}
