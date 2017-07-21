package par_12_高阶函数

/**
  * Created by lyen on 16-10-28.
  */
object Practice extends App {

  def value_1(func: (Int) => Int, low: Int, high: Int): List[(Int, Int)] = {
    (low to high map (data => (data, func(data))) toList)
  }

  println(value_1(x => x * x, -5, 5).mkString)

  def value_2(func: (Int) => Int)(low: Int, high: Int) = {
    (low to high map (data => (data, func(data))) toList)
  }

  println(value_2(x => x * x)(-5, 5).mkString)

  def reduceLeft(): Unit = {
    val arr = Array(3, 6, 8, 7, 9, 5, 4)
    arr.reduceLeft((a, b) => if (a > b) a else b)
  }

  println(1 to 10 reduceLeft (_ * _))

  def largest_0(func: (Int) => Int, inputSeq: Seq[Int]): Int = {
    inputSeq reduceLeft ((a, b) => if (func(a) > func(b)) func(a) else func(b))
  }
  def largest_1(func: (Int) => Int, inputSeq: Seq[Int]): Int = {
    (inputSeq map (num => func(num))).max
  }
  print(largest_1(x => 10 * x - x * x, 1 to 10))


  def largest_c(func: (Int) => Int, inputSeq: Seq[Int]): Int = {
    (inputSeq map (num => (num, func(num)))).maxBy(x => x._2)._1
  }
  def largest_c_1(func: (Int) => Int, inputSeq: Seq[Int]): Int = {
    inputSeq reduceLeft ((a, b) => if (func(a) > func(b)) a else b)
  }
  println(largest_c_1(x => 10 * x - x * x, 1 to 10))

}
