package par_20_Actor

import akka.actor.{Actor, ActorSystem, Props}

/**
  * Created by lyen on 16-11-1.
  */

case class Basic_info(name: String, age: Int)

case class Worker_info(name: String, age: Int)

class MyActor extends Actor {
  override def receive() = {
      case Basic_info(name, age) => println("basic information:" + name + age)
      case Worker_info(name, age) => println("worker information:" + name + age)
  }
}
object MyActor extends App{
  val system = ActorSystem("ActorSystem")
  val actor = system.actorOf(Props[MyActor],name = "myActor")
  actor ! Basic_info("lyen",22)
  actor ! Worker_info("hzt",23)
}
