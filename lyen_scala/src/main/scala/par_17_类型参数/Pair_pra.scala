package par_17_类型参数

/**
  * Created by lyen on 16-10-31.
  */
class Pair_immutable[T,S](val p: (T,S)){
  def swap = {
    (p._2,p._1)
  }
}

class Pair_mutable[S](var p: (S,S)){
  def swap = {
    (p._2,p._1)
  }
}

class Pair_fanxing[T,S]{
  def swap[T,S](p: (T,S)) = {
    (p._2,p._1)
  }
}

class Pair_st[T](val first: T,val second: T){
  def replaceFirst(newFirst: T) = new Pair_st[T](newFirst,second)
}
class Person
class Student extends Person
object Main extends App {
  val p1 = new Person
  val p2 = new Person
  val s1 = new Student
  val pair_st = new Pair_st(p1, p2)
  pair_st.replaceFirst(s1) //返回值类型为  Pair[Person]

  def middle[T](it: Iterable[T]): T = {
    def list = it.toList
    list(list.size / 2)
  }
  val str = "World"
  println("%s's middle is %c".format(str, middle(str)))
}
//当参数类型相同时可以调用swap
class Pair_same[S, T]( val p:(S,T)){
  def swap(implicit ev1: S =:= T, ev2: T =:= S) { // 这里需要双重的 类型约束
    (p._2,p._1)
  }
}
//当参数类型相同时可以调用swap
class Pair_same2[S, T](private var first: S, private var second: T) {
  def swap(implicit ev1: S =:= T, ev2: T =:= S) { // 这里需要双重的 类型约束
  val temp = first
    first = second
    second = temp
  }
  override def toString() = "(" + first +", " + second + ")"
}