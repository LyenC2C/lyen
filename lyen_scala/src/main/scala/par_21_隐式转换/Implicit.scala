package par_21_隐式转换

import org.apache.commons.lang.math.Fraction

/**
  * Created by lyen on 16-11-1.
  */
class Implicit {

}
class A {

}
class RichA(a:A){
  def rich = {
    println("I am A rich man")
  }
}
object Implicit extends App {
  //隐式转换
  implicit def A2RichA(a: A) = new RichA(a)
  val a = new A
  a.rich
  //隐式参数
  def testParam(implicit name: String){
    println(name)
  }
  implicit val name = "Lyen is working hard to pretend a busy man,however it does not work for his career"
  testParam
  testParam("why not me")
  //隐式转换函数
//  implicit def int2Fraction(a: Int) = new Fraction(a,1)
//  val re = 5 * Fraction(1,2)
  //隐式类
  implicit class Calculator(x: Int){
    def add(a: Int) = a + 1
  }
  println(1.add(1))

}
