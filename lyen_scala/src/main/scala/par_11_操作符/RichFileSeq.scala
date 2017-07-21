package par_11_操作符

/**
  * Created by lyen on 16-10-28.
  */
class RichFileSeq(val path: String) {

}

object RichFileSeq extends App {
  def apply(path: String): RichFileSeq = new RichFileSeq(path)

  def unapplySeq(richFileSeq: RichFileSeq): Option[Seq[String]] = {
    if (richFileSeq.path == "" && richFileSeq.path == null) {
      None
    } else {
      Some(richFileSeq.path.trim.split("/").filter(!_.equals("")))
    }
  }

  val richFileSeq = RichFileSeq("/home/lyen/222.txt")
  val RichFileSeq(first, middle, last) = richFileSeq
  println("First: %s Middle: %s  Last: %s".format(first, middle, last))

  //  val RichFileSeq(r @ _*) = richFileSeq


}
