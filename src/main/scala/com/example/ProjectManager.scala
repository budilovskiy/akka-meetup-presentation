package com.example

import akka.actor.{Actor, ActorLogging}

case class StartProject(projectName: String)

class ProjectManager(name: String) extends Actor with ActorLogging {
  def Idle: Receive = {
    case StartProject(projectName) =>
      log.info(s"Starting project $projectName.")
      context.become(Working)
  }

  def Working: Receive = Actor.ignoringBehavior

  def receive: Receive = Idle
}
