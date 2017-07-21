package par_18_高级类型_todo

/**
  * Created by lyen on 16-10-31.
  */
class Bug {
  var step: Int = _
  var flag: Boolean = true
  def move(num: Int): this.type = {if(flag == true) step += num else step -= num;this}
  def show(): this.type = { print(step);this}
  def turn(): this.type = {flag = !flag;this}
}

object Bug extends App{
  val bug = new Bug
  bug.move(5).show().move(4).show().turn().move(3).show()
}
/*
class BugCanDie extends Bug{
  def die() = {step = 0;this}
}
object BugCanDie extends App {
  val bugCanDie = new BugCanDie
  bugCanDie.move(5).show().move(4).show().turn().move(3).show().die().show()
}
*/
