/**
  * A sealed class makes it so that the class cannot be extended from outside of
  * the file. This pattern encourages pattern matching as the programmer is assured
  * that the only possible patterns that can be matched for this class are within
  * this file.
  */
sealed abstract class Square(xInitialValue: Int, yInitialValue: Int, symbolString: String) {
  private var _x = xInitialValue
  private var _y = yInitialValue
  private val _symbol = symbolString

  // Explicit getter
  def x = _x

  // Writing the setter like this allows the programmer to use the setter like a member
  // For example: Square.x = 17 is equivalent to Square.x_=(17)
  def x_=(xChangedValue: Int) = {
    if (xChangedValue >= 0) {
      _x = xChangedValue
    }
  }

  def y = _y

  def y_=(yChangedValue: Int) = {
    if (yChangedValue >= 0) {
      _y = yChangedValue
    }
  }

  def symbol = _symbol

  override def equals(o: Any): Boolean = {
    if (!o.isInstanceOf[Square]) {
      return false
    }
    val compare: Square = o.asInstanceOf[Square]

    symbol == compare.symbol && x == compare.x && y == compare.y
  }

  override def toString = String.valueOf(symbolString)
}

class Empty(x: Int, y: Int)
  extends Square(x, y, " - ")

class Queen(x: Int, y: Int)
  extends Square(x, y, " Q ") {
  var up = false
  var down = false
  var left = false
  var right = false
  var upRight = false
  var upLeft = false
  var downRight = false
  var downLeft = false
}