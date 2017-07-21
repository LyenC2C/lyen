package par_6_对象

import scala.reflect.ClassTag

/**
  * Created by lyen on 16-10-24.
  */
class Email {

}

/**
  * apply通常被称作注入方法
  * unapply通常被称为提取方法，使用unapply来提取固定数量的对象，使用unapplySeq来提取一个序列
  */
object Email {
  /*
  def apply(address: String, phone: String): par_6_对_象.Email = new par_6_对_象.Email

  def main(args: Array[String]): Unit = {
    val email = par_6_对_象.Email("yddsfj", "189")
  }
  */
  def apply[T: ClassTag](paras: T*): Email = new Email()
  def main(args: Array[String]): Unit = {
    val email: Email = Email("lyen","fas", "egadfgdg")
    print(email)
  }
}