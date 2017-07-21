package par_12_高阶函数

/**
  * Created by lyen on 16-10-28.
  */
object Test {
  //mulBy有一个类型为Double的参数返回一个类型为（Double） => Double 的函数
  def mulBy(factor: Double) = (x: Double) => factor * x

  //value接受一个函数(接受Double返回Double)作为参数的函数
  def value(f: (Double) => Double): Double = f(2.5)
  value(_ * 3)
  //接受两个参数的函数
  def mul(x: Int,y: Int) = x * y
  //柯里化  接受一个参数，生成另外一个接受单个参数的函数
  def mulOneAtTime(x: Int) = (y: Int) => x * y
  mulOneAtTime(6)(7)
  //简化 柯里化
  def mulOneAAtTime(x: Int)(y: Int) = x * y

}
