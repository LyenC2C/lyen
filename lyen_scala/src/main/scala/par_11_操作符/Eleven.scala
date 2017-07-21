package par_11_操作符

/**
  * Created by lyen on 16-10-28.
  */
class RichFile(val path: String) {


}

object RichFile extends App {

  def apply(path: String): RichFile = new RichFile(path)

  def unapply(richFile: RichFile) = {
    if (richFile.path == null && richFile.path == "") {
      None
    } else {
      val reg = "([/\\w+]+)/(\\w+)\\.(\\w+)".r
      val reg(r1, r2, r3) = richFile.path
      Some((r1, r2, r3))
    }
  }

  val file = RichFile("/home/lyen/dgsg/sdfgsdg/222.txt")
  val RichFile(r1, r2, r3) = file
  print(r1 + "\t" + r2 + "\t" + r3)
}