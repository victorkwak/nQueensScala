/**
  * Created by cary on 2/21/16.
  */
class Board (boardPass: Array[Array[Square]], heuristic: Int){

  var board: Array[Array[Square]] = boardPass
  var heuristicCost : Integer = heuristic
  val DEFAULT_VALUE = 5

//  def this(n: Integer) = {
//
//    this(Array.ofDim[Square](n, n), null)
//
//    for (i <- 0 to n) {
//      board(0)(i) = new Queen(0, i)
//    }
//
//    for (i <- 0 to n) {
//      for (j <- 0 to n) {
//        board(i)(j) = new Empty(i, j)
//      }
//    }
//
//    // TODO: boardutils.shuffle(board)
//  }

  def this(board: Array[Array[Square]]) {
    this(board, DEFAULT_VALUE)
  }

  def this(x : Int) = this(null, x)


}
