package process

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by lyen on 17-5-24.
  */
object Phone_mark extends App {

  val conf = new SparkConf()
    .setAppName("phone_mark")
    .setMaster("local")
    .set("spark.driver.memory", "1G")
    .set("spark.executor.memory", "1G")
  val sc = new SparkContext(conf)
  sc.setLogLevel("WARN")
  val data = sc.textFile("/home/lyen/data/phone_mark_v2_2")

  def resetTag(arr: Array[String]): String = {
    var tag = arr(3)
    var reset_tag = ""
    if (tag.length() <= 3 && tag.length() > 0) {
      Array(arr(0), arr(1), arr(2), "", arr(4)).mkString("\001").replace("None","")
    }
    else if (tag.contains("->")) {
      Array(arr(0), arr(1), arr(2),  tag.substring(tag.indexOf(">") + 1, tag.length), arr(4)).mkString("\001").replace("None","")
    }
    else {
      Array(arr(0), arr(1), arr(2), arr(3), arr(4)).mkString("\001").replace("None","")
    }
  }

  data.map(m => m.split("\t")).map(m => resetTag(m)).coalesce(1).saveAsTextFile("/home/lyen/data/phone_mark_v2_tmp")

}
