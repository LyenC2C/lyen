package study

/**
  * Created by lyen on 16-8-28.
  */

abstract class Reduce[T] {
  def add(x: T, y: T) : T
}

abstract class Sub_Reduce[T] extends Reduce[T] {
  def default: T
}

object icit_Object {
  //隐式对象
  implicit object StringAdd extends Sub_Reduce[String] {
    override def add(x: String, y: String) = x concat y
    override def default = ""
  }
  //隐式对象 (对象名随意)
  implicit object IntegerAdd extends Sub_Reduce[Int] {
    override def add(x: Int, y: Int) = x + y
    override def default = 0
  }
  //隐式对象作为隐式参数
  def sum[T](xs: List[T])(implicit m: Sub_Reduce[T]): T = {
    if (xs.isEmpty) m.default
    else{
      m.add(xs.head,sum(xs.tail))
    }
  }

  def main(args: Array[String]): Unit = {
    println(sum(List("SD","SDF","DF")))
  }

}
