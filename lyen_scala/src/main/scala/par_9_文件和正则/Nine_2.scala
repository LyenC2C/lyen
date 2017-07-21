package par_9_文件和正则

import java.io.PrintWriter

import scala.io.Source

/**
  * Created by lyen on 16-10-27.
  */
object Nine_2 extends App {

  def p() {
    val path = ""
    val source = Source.fromFile(path).getLines()
    val sourceRev = source.toArray.reverse
    val pw = new PrintWriter(path)
    sourceRev.foreach(line => pw.write(line + "\n"))
    pw.close()

    for(n <- 0 to 20){
      val t = BigDecimal(2).pow(n)
      pw.write(t.toString())
      pw.write("\t\t")
      pw.write((1/t).toString())
      pw.write("\n")
    }
  }

}
