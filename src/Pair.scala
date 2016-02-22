/**
  * Created by Victor Kwak on 2/21/16.
  */
class Pair (private val one: Queen, private val two: Queen){

  override def equals(o: Any): Boolean = {
    if (!o.isInstanceOf[Pair]) {
      return false
    }
    val pairToCompare: Pair = o.asInstanceOf[Pair]
    one == pairToCompare.one && two == pairToCompare.two ||
      one == pairToCompare.two && two == pairToCompare.one
  }

  override def hashCode =
    (one.x * 17 + one.y) + (two.x * 17 + two.y)

  override def toString =
    "{" + one.x + ", " + one.y + "}" + "{" + two.x + ", " + two.y + "}"
}
