package par_17_类型参数

/**
  * Created by lyen on 16-10-31.
  */
class Pair[T <% Ordered[T]](val first: T, val second: T) {

  def smaller = if (first < second) first else second

}
// <: 上界， >:下界 称做变量界定       同<:，>:一样不过<% 表示可以 隐士转换  传入Int型必要时可以隐士转换RichInt 称做视图界定
class Pair_2[T <% Comparable[T]](val first: T, val second: T) {

  def smaller = if (first.compareTo(second) < 0) first else second

}
//上下文界定
class Pair_3[T: Ordering](val first: T,val second: T){
  def smaller(implicit ord: Ordering[T]) = {
    if(ord.compare(first,second) < 0) first else second
  }
  //manifest上下文界定
  def mkPair[T: Manifest](first: T,second: T) = {
    val r = new Array[T](2);r(0) = first; r(1) =second; r
  }
}
object Pair_2 extends App {
  val s = new Pair(1,2)
  print(s.smaller)
}