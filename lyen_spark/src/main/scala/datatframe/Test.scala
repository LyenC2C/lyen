package datatframe

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SQLContext}
import org.apache.spark.sql.functions._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by lyen on 17-2-4.
  */

case class Person(name: String, age: Long)

case class Student(name: String, info: Array[Long])

object Test extends App {
  val conf = new SparkConf().setAppName("DataSet").setMaster("local")
  val sc = new SparkContext(conf)
  val re = sc.makeRDD(Array(10,20,30)).distinct.stats().variance

  print(re)
  //  val sqlContext = new SQLContext(sc)
  //
  //  import sqlContext.implicits._
  //
  //  //val df = Seq(Person("Andy", 32), Person("lyen", 23), Person("lyen", 20)).toDF().as[Person]
  //  //  val df = Seq(Person("Andy", 32), Person("lyen", 23), Person("lyen", 20)).toDF().groupBy("name").agg(sum("age"), max($"age"))
  //  //  df.show()
  //  val ds = Seq(Student("Andy", Array(0, 30)), Student("lyen", Array(1, 30)), Student("lyen", Array(1, 23))).toDS()
  //    .flatMap(x => x.info.map(m => (x.name, m))).show()

//  val a = Array(1, 2, 3).map(x => x.toDouble)
//  val b = Array(4, 5, 6)
//  val s = (1 to 3).toArray
//  a.zip(b).zip(s).foreach(println)
//  s


}
