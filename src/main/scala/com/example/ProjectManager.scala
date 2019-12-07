package com.example

import akka.actor.{Actor, ActorLogging}

class ProjectManager extends Actor with ActorLogging {
  def receive: Receive = Actor.emptyBehavior
}
