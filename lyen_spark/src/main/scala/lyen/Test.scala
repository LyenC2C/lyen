package lyen

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext, SparkEnv}

/**
  * Created by lyen on 16-11-14.
  */
object Test extends App {

  println("life is short you need spark!!!")

  val conf = new SparkConf().setMaster("local").setAppName("test")
  val sc = new SparkContext(conf)

  val data = List(("lyen", "student"), ("lyen", "IT"), ("hzt", "student"))
  val dataRDD = sc.parallelize(data)
  val re = dataRDD.combineByKey(x => List(x), (x: List[String], y: String) => x :+ y, (x: List[String],y: List[String])=> x ::: y);
  re.foreach(println)
  def combiner(): List[Int] = {
    List(1,2,3,4,5,6,7)
  }
}
