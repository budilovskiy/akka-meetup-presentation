package com.example

import akka.actor.{Actor, ActorLogging}

case class StartProject(projectName: String)

class ProjectManager(name: String) extends Actor with ActorLogging {
  // Actor always starts new project on receiving StartProject message
  def receive: Receive = {
    case StartProject(projectName) =>
      log.info(s"Starting project $projectName.")
  }
}
