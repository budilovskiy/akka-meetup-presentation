package com.example

import akka.actor.{Actor, ActorLogging, Props}

case class StartProject(projectName: String)
object Finish

case class Task(id: Int, description: String, done: Boolean = false, succeeded: Boolean = false)

class ProjectManager(name: String) extends Actor with ActorLogging {
  def Idle: Receive = {
    case StartProject(projectName) =>
      log.info(s"Starting project $projectName.")

      val projectTasks = List(
        Task(1, "Initialize project."),
        Task(2, "Create backend."),
        Task(3, "Crate frontend."),
        Task(4, "Deploy application")
      )

      val techLead = context.actorOf(Props(new TechnicalLeader("Bob")), "tech_lead_actor")
      techLead ! Develop(projectName, projectTasks)

      context.become(Working(projectName))
  }

  def Working(projectName: String): Receive = {
    case Finish =>
      log.info(s"Finish current project $projectName.")
      context.become(Idle)
  }

  def receive: Receive = Idle
}
