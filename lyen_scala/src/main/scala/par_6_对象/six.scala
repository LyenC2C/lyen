package par_6_对象

/**
  * Created by lyen on 16-10-26.
  */
object Card extends Enumeration with App {

  val M = Value("♣")
  val T = Value("♠")
  val H = Value("♥")
  val F = Value("♦")

  println(Card.M)
  println(Card.T)
  println(Card.H)
  println(Card.F)

  def color(c: Card.Value) {
    if (c == Card.M || c == Card.T) print("Black")
    else print("Red")
  }

  color(Card.H)

}

object RGB extends Enumeration with App {
  val RED = Value(0xff0000, "Red")
  val BLACK = Value(0x000000, "Black")
  val GREEN = Value(0x00ff00, "Green")
  val CYAN = Value(0x00ffff, "Cyan")
  val YELLOW = Value(0xffff00, "Yellow")
  val WHITE = Value(0xffffff, "White")
  val BLUE = Value(0x0000ff, "Blue")
  val MAGENTA = Value(0xff00ff, "Magenta")
}