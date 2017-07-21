package lyen.akka

/**
  * Created by lyen on 17-5-25.
  */


import akka.actor.Actor.Receive
import akka.actor._
import com.typesafe.config.ConfigFactory

class ClientActor extends Actor with ActorLogging {


  val path = "akka.tcp://remote-system@10.0.1.56:2555/user/remoteActor"
  // 远程Actor的路径，通过该路径能够获取到远程Actor的一个引用
  val remoteServerRef = context.actorSelection(path)
  // 获取到远程Actor的一个引用，通过该引用可以向远程Actor发送消息
  @volatile var connected = false
  @volatile var stop = false

  override def receive: Receive = {

    case Start => {
      // 发送Start消息表示要与远程Actor进行后续业务逻辑处理的通信，可以指示远程Actor初始化一些满足业务处理的操作或数据
      send(Start)
      if (!connected) {
        connected = true
        log.info("Actor connected: " + this)
      }
    }
    case Stop => {
      send(Stop)
      stop = true
      connected = false
    }
    case header: Header => send(header)
    case hb: Heartbeat => sendWithCheck(hb)
    case pkt: Packet => sendWithCheck(pkt)
    case cmd: Shutdown => send(cmd)

    case (seq, result) => log.info("RESULT: seq=" + seq + ", result=" + result) // 用于接收远程Actor处理一个Packet消息的结果
    case m => log.info("Unknown message: " + m)

  }

  private def sendWithCheck(cmd: Serializable): Unit = {
    while (!connected) {
      Thread.sleep(100)
      log.info("Wait to be connected...")
    }
    if (!stop) {
      send(cmd)
    } else {
      log.warning("Actor has stopped!")
    }
  }

  private def send(cmd: Serializable): Unit = {
    log.info("Send command to server: " + cmd)
    try {
      remoteServerRef ! cmd // 发送一个消息到远程Actor，消息必须是可序列化的，因为消息对象要经过网络传输
    } catch {
      case e: Exception => {
        connected = false
        log.info("Try to connect by sending Start command...")
        send(Start)
      }
    }
  }
}