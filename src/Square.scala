/**
  * Victor Kwak
  */
sealed abstract class Square (var x: Int, var y: Int, val symbol: String){

  def setXY(x: Int, y: Int) {
    this.x = x
    this.y = y
  }

  override def equals(o: Any): Boolean = {
    if (!o.isInstanceOf[Square]) {
      return false
    }
    val compare: Square = o.asInstanceOf[Square]

    symbol == compare.symbol && x == compare.x && y == compare.y
  }

  override def toString = String.valueOf(symbol)
}

class Empty(x: Int, y: Int)
  extends Square(x, y, " - ")

class Queen(x: Int, y: Int)
  extends Square(x, y, " Q ")