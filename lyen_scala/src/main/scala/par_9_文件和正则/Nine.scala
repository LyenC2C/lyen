package par_9_文件和正则

import java.io.{File, FileInputStream, PrintWriter}

import scala.io.Source

/**
  * Created by lyen on 16-10-27.
  */
object Nine extends App {
  /**
    * val source = Source.fromFile("/home/lyen/222.txt")
    * val words = source.mkString.split("\\s+")
    * words foreach print _
    */

  val pattern =
  """\s+[0-9]+\s+""".r
  val str = "99 bottles, 98 fakers"
  val re = pattern.findAllIn(str).toArray.foreach(println)

  val pattern_2 = """([0-9]+)\s([a-z]+)""".r
  val str_2 = "98 fakers,88 lake"
  val re_2 = pattern_2.findAllIn(str_2)
  for (pattern_2(num, name) <- re_2) {
    println(name + ":" + num)
  }

  val file = new File("")
  val in = new FileInputStream(file)
  val bytes = new Array[Byte](file.length.toInt)
  in.read(bytes)
  in.close()

  val out = new PrintWriter("numbers.txt")
  val quantity = 5d
  val price = 6f
  for(i <- 1 to 100) out.println(i)
  out.close()
  //当传递数字给printf方法时，编译器会抱怨说你需要将它转换成AnyRef:
  out.printf("%6d %10.2f",quantity.asInstanceOf[AnyRef],price.asInstanceOf[AnyRef])
  //为了避免这个麻烦，也可以用String类的format方法
  out.print("%6d %10.2f".format(quantity,price))

  import java.io.File
  //遍历某个目录下所有子目录的函数
  def subdirs(dir:File):Iterator[File] = {
    val children = dir.listFiles.filter(_.isDirectory())
    children.toIterator ++ children.toIterator.flatMap(subdirs _)
  }
  //访问所有子目录
  //for(d <- subdirs(dir)) 处理 d

}
