package com.example

import akka.actor.ActorSystem

import scala.concurrent.ExecutionContextExecutor

object Main extends App {
  // Actors needs an ActorSystem
  val system: ActorSystem = ActorSystem("HelloSystem")

  // Using dispatcher as execution context in whole application
  // This dispatcher is used for all actors in the actor system by default
  implicit val executionContext: ExecutionContextExecutor = system.dispatcher

  while (System.in.read() != -1) {}
  // Terminates the actor system
  system.terminate().foreach { _ =>
    System.exit(0)
  }
}
