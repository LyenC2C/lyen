package par_20_Actor

import akka.actor.Actor

/**
  * Created by lyen on 16-11-1.
  */
case class ProcessStringMsg(string: String)
case class StringProcessedMsg(words: Integer)

class StringCounterActor extends Actor {
  def receive = {
    case ProcessStringMsg(string) => {
      val wordsInLine = string.split(" ").length
      sender ! StringProcessedMsg(wordsInLine)
    }
    case _ => println("Error: message not recognized")
  }
}
