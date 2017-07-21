package par_13_集合

import scala.collection.mutable.ArrayBuffer

/** 主要的集合特质
  *                                             <<trait>>Iterable
  *
  * <<trait>>Seq                                 <<trait>>Set                           <<trait>>Map
  *
  * <<trait>>IndexSeq        List,Stream,Stack,Queue     <<trait>>SortedSet                     <<trait>>SortedMap
  * Vector,Range,ArrayBuffer
  * LinkedList,DoubleLInkedList
  *
  *
  */
object Practice extends App {

  def eee() = {
    //LIst，Array
    val list = List(1, 2)
    //向列表起始位置添加元素
    val l = 4 +: list
    //向列表末尾位置添加元素
    val ll = list :+ 5
    //ArrayBuffer是Vector的可变型
    val ab = ArrayBuffer(1, 2)
    ab += 4
    123 +=: ab
  }

  def indexes(str: String) = {
    import scala.collection.mutable.{Map, SortedSet}
    val map = Map[Char, SortedSet[Int]]()
    for (i <- 0 until str.length()) {
      map += str(i) -> (map.getOrElse(str(i), SortedSet(i)) += i)
      //  map(str(i).toChar) = (map.getOrElse(str(i).toChar, SortedSet(i)) += i)
    }
    map
  }

  println(indexes("Mississippi"))

  def indexes_immutable(str: String) = {
    var map = Map[Char, List[Int]]()
    for (i <- 0 until str.length()) {
      val yon = map.get(str(i))
      yon match {
        case None => map += str(i) -> List(i)
        case _ if (!yon.toList.contains(i)) => map += str(i) -> (yon.get ::: List(i))
      }
      /*
      var map = Map[Char, List[Int]]()
      for (i <- 0 until str.length()) {
        val temp:Option[List[Int]] = map.get(str(i))
        if (temp == None) map += str(i) -> List(i)
        else if (!temp.toList().contains(i)) map += str(i) -> (temp.get ::: List(i))
      }
      */
    }
    map
  }

  println(indexes_immutable("Mississippi"))

  def removeZero(lst: List[Int]): List[Int] = {
    lst.filter(_ != 0)
  }

  val a = List(1, 2, 3, 0, 5, 0, 7)
  println(removeZero(a))

  def stringMap(arr: Array[String], map: Map[String, Int]): Array[Int] = {
    arr.flatMap(map.get(_))
  }

  val arr = Array("Tom", "Fred", "Harry")
  val map = Map("Tom" -> 3, "Dick" -> 4, "Harry" -> 5)
  println(stringMap(arr, map).mkString(","))

  // /: => foldLeft   :\ => foldRight
  def definedList_right(list: List[Int]) = {
    (list :\ List[Int]()) (_ :: _)
  }

  def definedList_left(list: List[Int]) = {
    (list /: List[Int]()) (_ :+ _)
  }

  //(list :\ List[Int]())(_ :: _) equivalent to  (list :\ List[Int]())(_ +: _) or ( list /: List[Int]()) (_ :+ _)
  def definedList_reverse(list: List[Int]) = {
    (list :\ List[Int]()) ((a, b) => b :+ a)
  }

  val list = List(1, 2, 3, 4, 5, 6)
  definedList_left(list).foreach(print)
  definedList_right(list).foreach(print)
  definedList_reverse(list).foreach(print)


  val prices = List(5.0, 20.0, 9.95)
  val quantities = List(10, 2, 1)
  println((prices zip quantities) map { pair => pair._1 * pair._2 })
  println((prices zip quantities) map {
    Function.tupled(_ * _)
  })

  def to2DArray(cols: Int): Array[Array[Int]] = {
    val arr = Array(1, 2, 3, 4, 5, 6)
    arr.grouped(cols).toArray
  }

  to2DArray(3).foreach(x => println(x.mkString("[", ",", "]")))

  import scala.collection.immutable.HashMap

  def frequence(strs: Array[String]): HashMap[String, Int] = {
    val result = strs.par.aggregate(HashMap[String, Int]())(
      {
        (m, c) =>
          m + (c -> (m.getOrElse(c, 0) + 1))
      }, {
        (m1, m2) =>
          (m1.keySet ++ m2.keySet).foldLeft(HashMap[String, Int]()) {
            (result, k) =>
              result + (k -> (m1.getOrElse(k, 0) + m2.getOrElse(k, 0)))
          }
      })
    result
  }

  def frequence_2(strs: Array[String]): HashMap[String, Int] = {
    val result = strs.par.aggregate(HashMap[String, Int]())(
      {
        (m, c) =>
          m + (c -> (m.getOrElse(c, 0) + 1))
      }, {
        (m1, m2) =>
          (m1.keySet ++ m2.keySet).foldLeft(HashMap[String, Int]()) {
            (result, k) =>
              result + (k -> (m1.getOrElse(k, 0) + m2.getOrElse(k, 0)))
          }
      })
    result
  }

  val strs = "hello world you can you up no can no bb"
  val re = strs.split("\\s+")
  print(frequence(re))

}
