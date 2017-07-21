package par_8_继承

/**
  * Created by lyen on 16-10-27.
  */
abstract class Item {

  def price(): Double
  def description(): String
  override def toString: String = "description:" + description() + "price" + price()

}
