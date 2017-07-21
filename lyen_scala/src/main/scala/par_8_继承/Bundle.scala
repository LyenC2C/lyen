package par_8_继承

import scala.collection.mutable.ArrayBuffer

/**
  * Created by lyen on 16-10-27.
  */
class Bundle extends Item {

  val items = new ArrayBuffer[SimpleItem]()

  def addItem(item: SimpleItem) = {
    items += item
  }

  override def price(): Double = {
    items.map(_.price).reduce(_ + _)
  }

  override def description(): String = items.map(_.description).mkString(",")

}
