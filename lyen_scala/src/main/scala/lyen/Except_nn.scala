package lyen

import scala.collection.mutable.ArrayBuffer

/**
  * Created by lyen on 16-9-13.
  */
object Except_nn {


  def except_1(): Unit = {
    val arr = Array(1, 2, -1, 9, 2, 7, -5, 8)
    var flag = 0;
    val reArr = arr.filter(x => {
      if (x < 0) flag += 1
      (x > 0 || flag < 2)
    })
    reArr.foreach(println)
  }

  def except_2(): Unit = {
    val arr = ArrayBuffer(1, 2, -1, 9, 2, 7, -5, 8)
    var first = true
    //收集需要保留的下标
    val indexes: IndexedSeq[Int] = for (i <- 0 until arr.length if first || arr(i) >= 0) yield {
      if (arr(i) < 0) first = false;i
    }
    indexes.foreach(println)
    for (j <- 0 until indexes.length) {
      arr(j) = arr(indexes(j))
      arr.trimEnd(arr.length - indexes.length)
    }
    arr.foreach(println)
  }

  def main(args: Array[String]): Unit = {

    except_2()
  }
}
