package datatframe

import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by lyen on 17-2-4.
  */
object Sql2DF {

  case class Users(user_id: Int, user_sex: String, age: Int, occu_id: Int, email: String)

  case class Movie(movie_id: Int, movie_name: String, movie_type: String)

  case class Ratings(user_id: Int, movie_id: Int, score: Double, runtime: String)

  /*
  val conf = new SparkConf().setAppName("movies").setMaster("local")
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)
  import sqlContext.implicits._
  val userId:Int= 2
  val watchedMovie = "U.S. Marshalls (1998)"
  val users = sc.textFile("/home/lyen/datatest/users.dat").map(i=>{val tmp=i.split("::");Users(tmp(0).toInt,tmp(1),tmp(2).toInt,tmp(3).toInt,tmp(4))}).toDF()
  val movie = sc.textFile("/home/lyen/datatest/movies.dat").map(i=>{val tmp=i.split("::");Movie(tmp(0).toInt,tmp(1),tmp(2))}).toDF()
  val ratings = sc.textFile("/home/lyen/datatest/ratings.dat").map(i=>{val tmp = i.split("::");Ratings(tmp(0).toInt,tmp(1).toInt,tmp(2).toDouble,tmp(3))}).toDF()
  val age = sc.textFile("/home/lyen/datatest/age").map(i=>{val tmp = i.split(":");Age(tmp(0).toInt,tmp(1))}).toDF()

  val first = users.where($"user_id"===userId).select($"age_id").map(_.getAs[Int](0)).collect()(0)
  val second = users.where($"age_id"===first).select($"user_id").map(_.getAs[Int](0)).collect()(0)

  val third = ratings.join(movie,ratings("movie_id")===movie("movie_id"),"le  ft").select($"user_id",$"movie_name",$"movie_type",$"score")
  third.where($"user_id"===second and($"movie_name"===watchedMovie)).select($"movie_name",$"score",$"movie_type").show()
*/
  val conf = new SparkConf().setAppName("movies").setMaster("local")
  val sc = new SparkContext(conf)
  val sqlContext = new SQLContext(sc)

  import sqlContext.implicits._

  val userId: Int = 2
  val users = sc.textFile("/home/lyen/data/movie/users.dat").map(i => {
    val tmp = i.split("::");
    Users(tmp(0).toInt, tmp(1), tmp(2).toInt, tmp(3).toInt, tmp(4))
  }).toDF()
  val movie = sc.textFile("/home/lyen/data/movie/movies.dat").map(i => {
    val tmp = i.split("::");
    Movie(tmp(0).toInt, tmp(1), tmp(2))
  }).toDF()
  val ratings = sc.textFile("/home/lyen/data/movie/ratings.dat").map(i => {
    val tmp = i.split("::");
    Ratings(tmp(0).toInt, tmp(1).toInt, tmp(2).toDouble, tmp(3))
  }).toDF()
  val age = users.where($"user_id" === userId).select($"age").map(_.getAs[Int](0)).collect()(0)
  print(age)
  val first = users.where($"user_id" === userId).select("*").map(_.getAs[Int](0)).collect()(0)
}
