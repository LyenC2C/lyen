package lyen.akka

/**
  * Created by lyen on 17-5-25.
  */

import java.sql.Date
import java.text.SimpleDateFormat
import java.util.concurrent.atomic.AtomicLong

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.typesafe.config.ConfigFactory
import org.codehaus.jettison.json.JSONObject

import scala.util.Random

object AkkaClientApplication extends App {

  val system = ActorSystem("client-system", ConfigFactory.load().getConfig("MyRemoteClientSideActor")) // 通过配置文件application.conf配置创建ActorSystem系统
  val log = system.log
  val clientActor = system.actorOf(Props[ClientActor], "clientActor") // 获取到ClientActor的一个引用
  @volatile var running = true
  val hbInterval = 1000

  lazy val hbWorker = createHBWorker

  /**
    * create heartbeat worker thread
    */
  def createHBWorker: Thread = { // 心跳发送线程
    new Thread("HB-WORKER") {
      override def run(): Unit = {
        while(running) {
          clientActor ! Heartbeat("HB", 39264)
          Thread.sleep(hbInterval)
        }
      }
    }
  }

  def format(timestamp: Long, format: String): String = {
    val df = new SimpleDateFormat(format)
    df.format(new Date(timestamp))
  }

  def createPacket(packet: Map[String, _]): JSONObject = {
    val pkt = new JSONObject()
    packet.foreach(p => pkt.put(p._1, p._2))
    pkt
  }

  val ID = new AtomicLong(90760000)
  def nextTxID: Long = {
    ID.incrementAndGet()
  }

  clientActor ! Start // 发送一个Start消息，第一次与远程Actor握手（通过本地ClientActor进行转发）
  Thread.sleep(2000)

  clientActor ! Header("HEADER", 20, encrypted=false) // 发送一个Header消息到远程Actor（通过本地ClientActor进行转发）
  Thread.sleep(2000)

  hbWorker.start // 启动心跳线程

  // send some packets
  val DT_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS"
  val r = Random
  val packetCount = 10
  val serviceProviders = Seq("CMCC", "AKBBC", "OLE")
  val payServiceProvicers = Seq("PayPal", "CMB", "ICBC", "ZMB", "XXB")

  def nextProvider(seq: Seq[String]): String = {
    seq(r.nextInt(seq.size))
  }

  val startWhen = System.currentTimeMillis()
  for(i <- 0 until packetCount) { // 持续发送packetCount个Packet消息
  val pkt = createPacket(Map[String, Any](
    "txid" -> nextTxID,
    "pvid" -> nextProvider(serviceProviders),
    "txtm" -> format(System.currentTimeMillis(), DT_FORMAT),
    "payp" -> nextProvider(payServiceProvicers),
    "amount" -> 1000 * r.nextFloat()))
    clientActor ! Packet("PKT", System.currentTimeMillis, pkt.toString)
  }
  val finishWhen = System.currentTimeMillis()
  log.info("FINISH: timeTaken=" + (finishWhen - startWhen) + ", avg=" + packetCount/(finishWhen - startWhen))

  Thread.sleep(2000)

  // ask remote actor to shutdown
  val waitSecs = hbInterval
  clientActor ! Shutdown(waitSecs) // 发送Packet消息完成，通知远程Actor终止服务

  running = false
  while(hbWorker.isAlive) { // 终止心跳线程
    log.info("Wait heartbeat worker to exit...")
    Thread.sleep(300)
  }
  system.shutdown // 终止本地ActorSystem系统

}
