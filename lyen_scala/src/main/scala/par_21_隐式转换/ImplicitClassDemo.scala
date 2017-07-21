package par_21_隐式转换

/**
  * Created by lyen on 16-11-1.
  */
object ImplicitClassDemo {

   object MyImplicitTypeConversion{
    implicit def strToInt(str: String) = str.toInt
  }

  def main(args: Array[String]) {
    //compile error!
    //val max = math.max("1", 2);
    import MyImplicitTypeConversion._
    val max = math.max("1", 2);
    println(max)
  }

}
