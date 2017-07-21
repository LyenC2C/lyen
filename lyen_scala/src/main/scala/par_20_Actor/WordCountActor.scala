package par_20_Actor

import akka.actor.{Actor, ActorRef, ActorSystem, Props}

/**
  * Created by lyen on 16-11-1.
  */


case class StartProcessFileMsg()
class  WordCountActor(fileName: String) extends Actor{
  private var running = false
  private var totalLines = 0
  private var linesProcessed = 0
  private var result = 0
  private var fileSender: Option[ActorRef] = None

  override def receive: Receive = {
    case StartProcessFileMsg() =>{
      if(running) println("Warning: duplicate start message received")
      else {
        running =true
        fileSender = Some(sender) //save reference to process invoker(保存调用程序的引用)
        import scala.io.Source._
        fromFile(fileName).getLines().foreach{line =>
          context.actorOf(Props[StringCounterActor]) ! ProcessStringMsg(line)
          totalLines += 1
        }
      }
    }
    case StringProcessedMsg(words) => {
      result += words
      linesProcessed += 1
      if(linesProcessed == totalLines) fileSender.map(x => x ! result)
    }
    case _ => println("message not recognized!")

  }

}

object Main extends App {
  import akka.util.Timeout
  import scala.concurrent.duration._
  import akka.pattern.ask
  import akka.dispatch.ExecutionContexts._
  implicit val ec = global
  val system = ActorSystem("System")
  val actor = system.actorOf(Props(new WordCountActor("/home/lyen/222.txt")))
  implicit val timeout = Timeout(25 seconds)
  val future = actor ? StartProcessFileMsg()
  future.map { result =>
    println("Total number of words " + result)
    system.shutdown
  }
}