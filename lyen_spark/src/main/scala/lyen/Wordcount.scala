package lyen

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext, SparkEnv}

/**
  * Created by lyen on 16-12-7.
  */
object Wordcount extends App {

  val conf = new SparkConf().setAppName("wordcount").setMaster("local").set("spark.executor.memory","512")
  val sc = new SparkContext(conf)
  sc.setLogLevel("WARN")
  val path_lel = "hdfs://master:9000/data/wordcount.txt"
  //  val counts = sc.textFile(path_lel).flatMap(_.split(' ')).map((_, 1)).reduceByKey(_ + _).collect().foreach(println)//.saveAsTextFile("hdfs://10.3.4.100:9600/user/lel/result").top(20)(Ordering.by(_._2))
  val data = sc.parallelize(Array((1, "2,3"), (4, "5,6")))
  val s = data.flatMapValues(x =>x.split(",")).map(x=>x._1)
  val res = conf.getOption("spark.executor.memory")
    .orElse(Option(System.getenv("SPARK_EXECUTOR_MEMORY")))
    .orElse((Option(System.getenv("SPARK_MEM")))) match {
    case Some(s) => s
    case _ => "sorry"
  }
  println(s"the result is : $res")
  SparkEnv
}
