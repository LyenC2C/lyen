package par_6_对象

/**
  * Created by lyen on 16-10-24.
  */
class SomeClass {
  def update(arg1: Int, arg2: String, arg3: String): String = {
    println("update method called")
    arg1 + "|" + arg2 + "|" + arg3
  }
}

object SomeClass {

  def main(args: Array[String]): Unit = {
    val cl = new SomeClass()
    //等号右边的值会作为 update 方法的最后一个参数。
    val result = (cl(1, "key") = "hello")
    print(result)
  }
}