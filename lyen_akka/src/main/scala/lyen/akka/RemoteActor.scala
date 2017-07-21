package lyen.akka

/**
  * Created by lyen on 17-5-25.
  */

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.typesafe.config.ConfigFactory



class RemoteActor extends Actor with ActorLogging {

  val SUCCESS = "SUCCESS"
  val FAILURE = "FAILURE"

  override def receive: Receive = {
    case Start => {
      // 处理Start消息
      log.info("receive event: " + Start)
    }
    case Stop => {
      // 处理Stop消息
      log.info("receive event: " + Stop)
    }
    case Shutdown(waitSecs) => {
      // 处理Shutdown消息
      log.info("Wait to shutdown: waitSecs=" + waitSecs)
      Thread.sleep(waitSecs)
      log.info("Shutdown this system")
      context.system.shutdown // 停止当前ActorSystem系统
    }
    case Heartbeat(id, magic) => log.info("receive heartbeat: " + (id, magic)) // 处理Heartbeat消息
    case Header(id, len, encrypted) => log.info("receive header: " + (id, len, encrypted)) // 处理Header消息
    case Packet(id, seq, content) => {
      // 处理Packet消息
      val originalSender = sender // 获取到当前发送方的Actor引用
      log.info("receive packet: " + (id, seq, content))
      originalSender ! (seq, SUCCESS) // 响应给发送方消息处理结果，类似发送一个ACK
    }
    case _ =>
  }
}


