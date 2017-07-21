package accumulation

import java.io.{FileInputStream}
import java.util.Properties

import org.apache.commons.cli._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by lyen on 16-8-31.
  */
class Learn_property {

  val conf = new SparkConf().setMaster("local").setAppName(this.getClass.getName)
  val fileParam = "f"
//读取文件内容加载属性并设置到sparkconf中
  def loadProperties(pro_file: String) = {
    val in = new FileInputStream(pro_file)
    val properties: Properties = new Properties()
    properties.load(in)
    val en: java.util.Enumeration[AnyRef] = properties.keys()
    while (en.hasMoreElements) {
      val key = en.nextElement().toString
      conf.set(key, properties.getProperty(key))
    }
    conf
  }
//构建options
  def setOptions: Options = {
    val ops = new Options()
    ops.addOption(fileParam, true, "where the properties file localtion is")
    ops
  }
//解析命令行参数获取参数对应的值(文件路径)
  def parseCommand(para: Array[String]): String = {
    val cmdParser = new BasicParser()
    val cmd = cmdParser.parse(setOptions, para)
    cmd.getOptionValue(fileParam)
  }


}

object Learn_property {
  def main(args: Array[String]): Unit = {
    val pl = new Learn_property
    val file = pl.parseCommand(args)
    val conf_2 = pl.loadProperties(file)
    val sc = new SparkContext(conf_2)
    conf_2.getAll.foreach(println)
  }
}
