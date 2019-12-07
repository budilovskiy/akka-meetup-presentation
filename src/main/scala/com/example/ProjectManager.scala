package com.example

import akka.actor.{Actor, ActorLogging}

case class StartProject(name: String)

class ProjectManager extends Actor with ActorLogging {
  // Actor always starts new project on receiving StartProject message
  def receive: Receive = {
    case StartProject(name) =>
      log.info(s"Starting project $name.")
  }
}
