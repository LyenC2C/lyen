package par_6_对象

/**
  * Created by lyen on 16-10-24.
  */
class Email_more(addr: String, number: String) {

}

object Email_more {

  def apply(addr: String, number: String): Email_more = new Email_more(addr, number)

  def main(args: Array[String]): Unit = {
    val email_more = Email("sfsdf", "sdfsdf", "fasafafa")
  }
}
