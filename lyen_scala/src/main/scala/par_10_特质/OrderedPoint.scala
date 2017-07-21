package par_10_特质

import java.awt.Point


/**
  * Created by lyen on 16-10-28.
  */
class OrderedPoint extends Point with Ordered[Point] {

  def compare(that: Point): Int = if (this.x <= that.x && this.y < that.y) -1 else if (this.x == that.x && this.y == that.y) 0 else 1

}
