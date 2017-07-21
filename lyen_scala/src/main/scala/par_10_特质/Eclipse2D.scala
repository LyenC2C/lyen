package par_10_特质

import java.awt.Rectangle

/**
  * Created by lyen on 16-10-28.
  */
//1.貌似不可行
//class Eclipse2D{
//  val egg = new java.awt.geom.Ellipse2D.Double(5, 10, 20, 30) with RectangleLike
//  egg.translate(10, -10)
//  egg.grow(10, 20)
//
//}
//
//trait RectangleLike extends Rectangle

/*
//使用自身特質
trait RectangleLike {
  this: java.awt.geom.Ellipse2D.Double =>
  def translate(x: Double, y: Double): Unit = {
    this.x = x
    this.y = y
  }

  def grow(x: Double, y: Double) {
    this.x += x
    this.y += y
  }
}
class Test {
  val egg = new java.awt.geom.Ellipse2D.Double(4,6,8,9) with RectangleLike
  egg.translate(4,5)
  egg.grow(6,7)
}
*/