package par_4_映射和元祖

import scala.collection.SortedMap
import scala.io.Source

/**
  * Created by lyen on 16-10-26.
  */
object Fourth extends App {
  val str = "hello"
  val str_2 = "world"

  //  print(str zip (str_2))
  1.

  def worcount_HashMap(): Unit = {
    import scala.collection.mutable.HashMap
    val source = Source.fromFile("/home/lyen/222.txt").mkString
    val words = source.split(" ")
    val map = new HashMap[String, Int]()
    for (key <- words) {
      // map(key) = map.getOrElse(key, 0) + 1
      map += key -> (map.getOrElse(key, 0) + 1)
    }
    print(map.mkString(","))
  }

  2.

  def worcount_Map(): Unit = {
    val source = Source.fromFile("/home/lyen/222.txt").mkString
    val words = source.split(" ")
    var map = Map[String, Int]()
    for (key <- words) {
      map += (key -> (map.getOrElse(key, 0) + 1))
    }
    print(map.mkString(","))
  }

  3.

  def worcount_SortedMap(): Unit = {
    val source = Source.fromFile("/home/lyen/222.txt").mkString
    val words = source.split(" ")
    //不会改变对象状态的的方法去掉()是不错的风格，要改变的对象必须加上()
    var map = SortedMap[String, Int]()
    for (key <- words) {
      map += (key -> (map.getOrElse(key, 0) + 1))
    }
    print(map.mkString(","))
  }

  4.

  def worcount_TreeMap(): Unit = {
    import scala.collection.mutable.Map
    import java.util.TreeMap
    import scala.collection.JavaConversions._
    val source = Source.fromFile("/home/lyen/222.txt").mkString
    val words = source.split(" ")
    //不会改变对象状态的的方法去掉()是不错的风格
    val map: Map[String, Int] = new TreeMap[String, Int]()
    for (key <- words) {
      map(key) = map.getOrElse(key, 0) + 1
    }
    print(map.mkString(","))
  }

  5.

  def worcount_zhedie(): Unit = {
    val source = Source.fromFile("/home/lyen/222.txt").mkString
    val words = source.split(" ")
    //    (Map[Char, Int]) /: "dfhdfhh")((w: Char, c: Int) => w + (c -> (w.getOrElse(c, 0)) + 1)))
  }

  worcount_HashMap()
  println
  worcount_Map()
  println
  worcount_SortedMap()
  println
  worcount_TreeMap()
}
