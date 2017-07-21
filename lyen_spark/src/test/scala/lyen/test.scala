package lyen

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by lyen on 17-3-23.
  */
object test extends App {

  val conf = new SparkConf().setAppName("lyen").setMaster("local[*]")

  val sc = new SparkContext(conf)

  val data = sc.makeRDD(Array(Array("lyen","cc"),Array("lyen","mm"),Array("hzt","kk")))


}
