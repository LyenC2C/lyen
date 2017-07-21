package study

import java.io.File

import scala.io.Source

/**
  * Created by lyen on 16-8-28.
  */

/**
 *对于隐式：
  * 1.优先从当前类的伴生对象中查找
  * 2.其次在
 */
//貌似必须为object不能为class
object ContextHelper {

  implicit class FileRicher(file: File) {
    def read = Source.fromFile(file.getPath).mkString
  }
  
  implicit class Od(x: Int){
    def add(y: Int) = x + y
  }

}
object Implicit_Class {
  def main(args: Array[String]): Unit = {
    import ContextHelper._
    println(1.add(2))
  }
}
