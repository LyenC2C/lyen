package sort

/**
  * Created by lyen on 17-3-21.
  */
object mergeSort extends App {
    var reverse_pairs = 0//逆序数
    def msort[T](cmp:(T, T) => Boolean)(l:List[T]):List[T] = {
      def merge(l1:List[T], l2:List[T]):List[T]=(l1, l2)match{
        case(Nil, _) => l2
        case(_, Nil) => l1
        case(x::left1, y::left2) =>
          if(cmp(x, y))
            x::merge(left1, l2)
          else{
            reverse_pairs += l1.length
            y::merge(l1, left2)
          }
      }
      val n = l.length / 2
      if(n == 0)
        return l
      else{
        val (l1, l2) = l.splitAt(n)
        merge(msort(cmp)(l1), msort(cmp)(l2))
      }
    }
    println(msort((x:Int, y:Int) => x<y)(List(5, 4, 3, 2, 7,6 )))
    println(reverse_pairs)
}
