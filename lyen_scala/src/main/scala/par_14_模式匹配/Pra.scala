package par_14_模式匹配

/**
  * Created by lyen on 16-10-30.
  */
object Practice extends App {

  def test(list: List[Int]) = {
    //ps: 变量是如何绑定到列表或者元组的不同部分的，由于这种绑定让你很轻松的访问复杂结构的组成部分，因此这样的操作称为'析构'
    list match {
      case 0 :: Nil => "a list which only contains 0"
      case 0 :: tail => "a list which start with 0"
      case x :: y :: Nil => "any list only if it caontains two element and it is banded to x and y"
      case _ => "something else"
    }
  }

  def swap[S, T](tup: (S, T)) = {
    tup match {
      case (a, b) => (b, a)
    }
  }

  def swap(arr: Array[Int]) = {
    arr match {
      case Array(a, b, rest@_*) => Array(b, a) ++ rest
      case _ => arr
    }
  }

  val arr = Array(12, 15, 1, 2, 3, 4, 5)
  println(swap(arr) mkString)

  val l: List[Any] = List(List(3, 8), 2, List(5))

  def leafSum(list: List[Any]): Int = {
    var total = 0
    list.foreach {
      lst =>
        lst match {
          case l: List[Any] => total += leafSum(l)
          case i: Int => total += i
        }
    }
    total
  }

  println(leafSum(l))

  /*
  sealed abstract class BinaryTree
  case class Leaf(value:Int) extends BinaryTree
  case class Node(left: BinaryTree,right: BinaryTree) extends BinaryTree
  val tree = Node(Leaf(3),Node(Leaf(4),Node(Leaf(4),Leaf(5))))
  def leafSum(tree: BinaryTree): Int = {
    tree match {
      case Node(l,r) => leafSum(l) + leafSum(r)
      case Leaf(v) => v
    }
  }
    println(leafSum(tree))
    */
  /*
  sealed abstract class BinaryTree
  case class Leaf(value: Int) extends BinaryTree
  case class Node(tr: BinaryTree*) extends BinaryTree
  val r = Node(Node(Leaf(3), Leaf(8)), Leaf(2), Node(Leaf(5)))
  def leafSum(tree: BinaryTree): Int = {
    tree match {
      case Node(r @ _*) => r.map(leafSum).sum
      case Leaf(v) => v
    }
  }
    println(leafSum(r))
    */
  sealed abstract class BinaryTree

  case class Leaf(value: Int) extends BinaryTree

  case class Node(ch: Char, tr: BinaryTree*) extends BinaryTree

  val r = Node('+', Node('*', Leaf(3), Leaf(8)), Leaf(2), Node('-', Leaf(5)))

  def eval(tree: BinaryTree): Int = {
    tree match {
      case Node(c: Char, r@_*) => if (c == '+') r.map(eval).sum else if (c == '*') r.map(eval).reduceLeft(_ * _) else r.map(eval).foldLeft(0)(_ - _)
      case Leaf(v) => v
    }
  }

  println(eval(r))

  val ls: List[Option[Int]] = List(Option(-1), None, Option(2))
  println(ls.map(_.getOrElse(0)).sum)

  import scala.math._

  def f(x: Double) = if (x >= 0) Some(sqrt(x)) else None
  def g(x: Double) = if (x != 1) Some(1 / (x - 1)) else None
  val h = compose(f, g)
  def compose(f: (Double => Option[Double]), g: (Double => Option[Double])): (Double => Option[Double]) = {
    (x: Double) =>
      if (f(x) == None || g(x) == None) None
      else g(x)
  }
  println(h(2))

}

abstract class Item

case class Multiple(num: Int, item: Item) extends Item

case class Article(description: String, price: Double) extends Item

case class Bundle(description: String, discount: Double, item: Item*) extends Item

object Test extends App {

  def price(it: Item): Double = it match {
    case Article(_, p) => p
      //its @ _* 表示将嵌套的的值绑定到变量
    case Bundle(_, disc, its @ _*) => its.map(price _).sum - disc
    case Multiple(n, it) => n * price(it)
  }

  val p = price(Multiple(10, Article("Blackwell Toster", 29.95)))
  println(p)
}
