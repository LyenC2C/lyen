package lyen

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.SortedMap

/**
  * Created by lyen on 16-12-7.
  */
object Diff extends App {

  val conf = new SparkConf().setAppName("predict").setMaster("local[*]")
  val sc = new SparkContext(conf)

  val pridict_data = "西昌市同城房地产经纪有限责任公司"

  val data = sc.textFile("/home/lyen/mark")
  val pridictData = sc.textFile("/home/lyen/total")
  //  val predict_filed = pridictData.map(_.split(',')).map(x => x(0)).collect()
  val predict_filed = pridictData.map(_.split(',')).map(x => x(0))
  val model = data.map(_.split(',')).map(x => (x(2), x(0))).groupByKey().mapValues { x =>
    var num = 0
    for (i <- x) {
      num = num + i.length
    }
    (x, num)
  }.mapValues { x =>
    val str = x._1.reduce(_ + "" + _)
    var map = SortedMap[Char, Int]()
    for (key <- str) {
      map += (key -> (map.getOrElse(key, 0) + 1))
    }
    val re = for ((k, v) <- map) yield {
      (k, (v.toFloat / x._2.toFloat))
    }
    re
  }.cache()

  val result_2 = predict_filed.map { str =>
    model.mapValues { x =>
      val re = for (ch <- str) yield {
        (ch, x.get(ch), str)
      }
      re
    }.mapValues { x => x.filter(m => m._2 != None) }.filter(x => x._2.length != 0).mapValues { x =>
      var sum = 0f
      var name = ""
      for (re <- x.toArray) {
        sum = sum + re._2.get
        name = re._3
      }
      (sum, name)
    }.top(1)(Ordering.by(_._2))
  }.flatMap { x => x.map { y =>
    var tp = y._2._2
    if (y._2._1 == 0f)
      tp = "1"
    (y._1, tp)
  }
  }
}
