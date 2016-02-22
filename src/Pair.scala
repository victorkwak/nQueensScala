/**
  * Created by Victor Kwak on 2/21/16.
  */
class Pair {
  var pair: Array[Queen] = null

  def this(one: Queen, two: Queen) {
    this()
    pair = new Array[Queen](2)
    pair(0) = one
    pair(1) = two
  }

  override def hashCode: Int = {
    (pair(0).x * 17 + pair(0).y) + (pair(1).x * 17 + pair(1).y)
  }

  override def equals(o: Any): Boolean = {
    if (!o.isInstanceOf[Pair]) {
      return false
    }
    val pairToCompare: Pair = o.asInstanceOf[Pair]
    (pair(0) == pairToCompare.pair(0)) && (pair(1) == pairToCompare.pair(1)) || (pair(0) == pairToCompare.pair(1)) && (pair(1) == pairToCompare.pair(0))
  }

  override def toString: String = {
    "{" + pair(0).x + ", " + pair(0).y + "}" + "{" + pair(1).x + ", " + pair(1).y + "}"
  }

}
