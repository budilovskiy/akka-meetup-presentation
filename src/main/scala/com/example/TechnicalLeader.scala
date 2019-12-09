package com.example

import akka.actor.{Actor, ActorLogging}

case class Develop(projectName: String, details: String)

class TechnicalLeader(name: String) extends Actor with ActorLogging {
  def receive: Receive = Actor.emptyBehavior
}
