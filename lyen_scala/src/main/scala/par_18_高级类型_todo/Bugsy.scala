package par_18_高级类型_todo

/**
  * Created by lyen on 16-10-31.
  */
object and
class Bugsy {
  var step: Int = _
  private var useNextArgAs:Any = null
  var flag: Boolean = true
  def move(num: Int ): this.type = {if(flag == true) step += num else step -= num;this}
  def show(obj: and.type ): this.type = { useNextArgAs = obj; print(step);this}
  def turn(): this.type = {flag = !flag;this}
}
object Bugsy extends App {
  val bug = new Bugsy
  bug move 4 show and
}
