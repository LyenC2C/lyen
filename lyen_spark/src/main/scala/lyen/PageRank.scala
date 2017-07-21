package lyen

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by lyen on 16-11-16.
  */

object PageRank extends App with Serializable {
  @SerialVersionUID(6732270565076291202l)
  val conf = new SparkConf().setAppName("pageRank").setMaster("local-cluster[1,1,1024]")
  val sc = new SparkContext(conf)
  val iterations = 10
  val links: RDD[(String, Array[String])] = sc.parallelize(Array(("A", Array("D")), ("B", Array("A")), ("C", Array("A", "B")), ("D", Array("A", "C"))), 2)
  var ranks: RDD[(String, Double)] = sc.parallelize(Array(("A", 1.0), ("B", 1.0), ("C", 1.0), ("D", 1.0)), 2)
  for (i <- 1 to iterations) {
    val contribs: RDD[(String, (Array[String], Double))] = links.join(ranks, 2)
    val flatMapRDD: RDD[(String, Double)] = contribs.flatMap { case (url, (links, rank)) => links.map(dest => (dest, rank / links.size)) }
    val reduceByKeyRDD = flatMapRDD.reduceByKey(_ + _, 2)
    ranks = reduceByKeyRDD.mapValues(0.15 + 0.85 * _)
    ranks.collect().foreach(x => println(x._1 + "     " + x._2))
    println()
  }

}
