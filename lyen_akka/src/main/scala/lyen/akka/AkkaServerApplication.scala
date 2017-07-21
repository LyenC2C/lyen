package lyen.akka

/**
  * Created by lyen on 17-5-25.
  */

import akka.actor.{Actor, ActorLogging, ActorSystem, Props}
import com.typesafe.config.ConfigFactory

object AkkaServerApplication extends App {

  val system = ActorSystem("remote-system", ConfigFactory.load().getConfig("MyRemoteServerSideActor")) // 创建名称为remote-system的ActorSystem：从配置文件application.conf中获取该Actor的配置内容
  val log = system.log
  log.info("Remote server actor started: " + system)

  system.actorOf(Props[RemoteActor], "remoteActor") // 创建一个名称为remoteActor的Actor，返回一个ActorRef，这里我们不需要使用这个返回值

}