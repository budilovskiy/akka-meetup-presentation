package com.example

import akka.actor.{Actor, ActorLogging, Props}

case class StartProject(projectName: String)
object Finish
object CheckTasks

case class Task(id: Int, description: String, done: Boolean = false, succeeded: Boolean = false)

class ProjectManager(name: String) extends Actor with ActorLogging {
  private val techLead = context.actorOf(Props(new TechnicalLeader("Bob")), "tech_lead_actor")
  private val qaLead = context.actorOf(Props(new QaLeader("Jon")), "QA_lead_actor")

  def Idle: Receive = {
    case StartProject(projectName) =>
      log.info(s"Starting project $projectName.")

      val projectTasks = List(
        Task(1, "Initialize project."),
        Task(2, "Create backend."),
        Task(3, "Crate frontend."),
        Task(4, "Deploy application")
      )

      techLead ! Develop(projectName, projectTasks)

      context.become(Working(projectName, projectTasks))
  }

  def Working(projectName: String, tasks: Seq[Task]): Receive = {

    case command: TaskForTest =>
      qaLead ! command

    case TestedTask(task) if task.succeeded =>
      val newPullTasks = tasks.filterNot(_.id == task.id) :+ task
      context.become(
        Working(projectName, newPullTasks)
      )
      self ! CheckTasks

    case TestedTask(task) if !task.succeeded =>
      techLead ! FailedTask(task.copy(done = false))

    case CheckTasks =>
      if (tasks.forall(t => t.succeeded && t.done))
        context.self ! Finish

    case Finish =>
      log.info(s"Finish current project $projectName.")
      context.become(Idle)
  }

  def receive: Receive = Idle
}