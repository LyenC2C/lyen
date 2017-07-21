package accumulation

/**
  * Created by lyen on 16-9-1.
  */
object Test_split {
  def main(args: Array[String]): Unit = {
    val str = "123q"+"\u0001"+"weadd"
    str.split("\u0001").foreach(println)
    print("\u0001")

  }

}
