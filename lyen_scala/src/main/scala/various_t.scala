import par_11_操作符.RichFile

/**
  * Created by lyen on 16-10-24.
  */
object various_t extends App{
  val numbers = 1 to 5
  def sum(nums: Int*): Int = {var re = 0;for (i <- nums) {re += i};re}
  val result = sum(numbers: _*) //将1 to 5 当做参数序列处理
//  print(result)

  def recursiveSum(nums: Int*):Int = {
    if(nums == 0) 0 else nums.head + recursiveSum(nums.tail: _*)
  }

  val a = Array(1,5,7,3)
  scala.util.Sorting.quickSort(a)
  a.foreach(println)
  a.mkString("<",",",">").foreach(print)
  a.count(_ < 5)
  Predef.any2ArrowAssoc()
}
