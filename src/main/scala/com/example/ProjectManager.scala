package com.example

import akka.actor.{Actor, ActorLogging}

case class StartProject(projectName: String)
object Finish

class ProjectManager(name: String) extends Actor with ActorLogging {
  def Idle: Receive = {
    case StartProject(projectName) =>
      log.info(s"Starting project $projectName.")
      context.become(Working)
  }

  def Working: Receive = {
    case "Finish" =>
      log.info("Finish current project.")
      context.become(Idle)
    case Finish =>
      log.info("Finish current project.")
      context.become(Idle)
  }

  def receive: Receive = Idle
}
