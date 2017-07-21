package par_3_数组相关操作

import scala.math.random

/**
  * Created by lyen on 16-10-26.
  */
object Third extends App {

  //1.
  def mkArr(n: Int): Array[Int] = {
    /* way_1
        val arr = new Array[Int](n)
        val rand = new scala.util.Random()
        for (i <- arr) yield {
          rand.nextInt(n)
        }
    */
    val a = for (i <- 0 until n) yield (random * n).toInt
    a.toArray
  }

  //2.
  def swap(arr: Array[Int]): Array[Int] = {
    for (i <- 0 until arr.length if i + 1 < arr.length && i % 2 == 0) {
      val t = arr(i)
      arr(i) = arr(i + 1)
      arr(i + 1) = t
    }
    arr
  }

  def swap_2(arr: Array[Int]): Array[Int] = {
    val s = for (i <- 0 until arr.length) yield {
      if ((i + 1) < arr.length && i % 2 == 0) {
        val t = arr(i)
        arr(i) = arr(i + 1)
        arr(i + 1) = t
      }
      arr(i)
    }
    s.toArray
  }

  mkArr(10).mkString(",").foreach(print)
  println
  swap(Array(1, 2, 3, 5, 7, 9, 10)).mkString(",").foreach(print)
  println
  swap_2(Array(1, 2, 3, 5, 7, 9, 10)).mkString(",").foreach(print)
}
