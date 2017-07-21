package par_6_对象

/**
  * Created by lyen on 16-10-24.
  */
class Student {

}

object Student {
  /**
    * 相對於unapply()方法，apply()方法則稱之為注入方法（Injection method）
    * 提取方法與注入方法通常同時存在（但非必要），apply()方法與unapply()方法的作用通常是相反的
    */
  def apply(number: String, name: String, age: String): String = {
    number + "\t" + name + "\t" + age
  }

  def unapply(str: String): Option[(String, String, String)] = {
    val parts = str.split("\t")
    if (parts.length == 3) Some(parts(0), parts(1), parts(2)) else None
  }

  def main(args: Array[String]): Unit = {

    /** val student = par_6_对_象.Student("lyen","22","男")
      * actually would be handled as follow:
      * val student = par_6_对_象.Student.apply("lyen","22","男")
      */
    val student = Student("lyen", "22", "男")
    /** val par_6_对_象.Student(number, name, addr) = "B123456,Justin,Kaohsiung"
      * actually would be handled as follow:
      * val par_6_对_象.Student(number,name,addr) = par_6_对_象.Student.unapply("B123456,Justin,Kaohsiung")
      */
    val Student(number, name, addr) = student
    println(number + name + addr)
    val students = List(
      "B123456\tJustin\tKaohsiung",
      "B98765\tMonica\tKaohsiung",
      "B246819\tBush\tTaipei"
    )
    students.foreach(_ match {
      case Student(nb, name, addr) => println(nb + "," + name + "," + addr)
    })
    students.foreach(_ match {
      case Student(_, name, addr) if addr == "Kaohsiung" => println(name)
      case _ =>
    })
    
  }
}
