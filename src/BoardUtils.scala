import scala.annotation.switch
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

/**
  * Created by cary on 2/21/16.
  */
object BoardUtils {

  def shuffle(toShuffle: Array[Array[Square]]) = {
    val rand: Random = scala.util.Random

    for (i <- toShuffle(0).indices) {
      val rand1: Int = rand.nextInt(toShuffle.length)
      swap(toShuffle, 0, i, rand1, i)
    }
  }

  def swap(arr: Array[Array[Square]], i: Int, j: Int, k: Int, l: Int) = {
    val temp: Square = arr(i)(j)
    arr(i)(j) = arr(k)(l)
    arr(i)(j).x = i
    arr(i)(j).y = j
    arr(k)(l) = temp
    arr(k)(l).x = k
    arr(k)(l).y = l
  }

  def lowestNeighbor(current: Board): Board = {
    val children = generateChildren(current)
    var lowest = children(0)

    for (i <- 1 until children.length) {
      if (children(i).getHeuristicCost < lowest.getHeuristicCost) {
        lowest = children(i)
      }
    }

    var lowestChildren = new ArrayBuffer[Board]()
    val lowestH = lowest.getHeuristicCost
    for (i <- children.indices) {
      if (children(i).getHeuristicCost == lowestH) {
        lowestChildren += children(i)
      }
    }

    val rand = new Random()
    lowestChildren(rand.nextInt(lowestChildren.length))
  }

  private def generateChildren(current: Board): ArrayBuffer[Board] = {
    val children = new ArrayBuffer[Board]()
    for (squares <- current.getBoard) {
      for (square <- squares) {
        (square: @switch) match {
          case queen: Queen =>
            val toPassIn = copyBoard(current.getBoard)._1
            for (board <- generateChildrenForQueen(toPassIn, queen)) {
              children += board
            }
          case empty: Empty =>
        }
      }
    }
    children
  }

  private def generateChildrenForQueen(board: Array[Array[Square]], queen: Queen): ArrayBuffer[Board] = {
    val children = new ArrayBuffer[Board]()
    if (queen.x!= 0) {
      swap(board, queen.x, queen.y, 0, queen.y)
      children += new Board(board)
    }
    for (i <- 0 until board.length - 1) {
      swap(board, i, queen.y, i + 1, queen.y)
      if (i + 1 != queen.x) {
        children += new Board(board)
      }
    }
    children
  }

  def copyBoard(board: Array[Array[Square]]): (Array[Array[Square]], Array[Queen]) = {
    val newBoard = Array.ofDim[Square](board.length, board(0).length)
    val newQueenBoard = Array.ofDim[Queen](board.length)
    var queenIndex = 0
    for (i <- board.indices) {
      for (j <- board(0).indices) {
        (board(i)(j): @switch) match {
          case queen: Queen =>
            newBoard(i)(j) = new Queen(i, j)
            newQueenBoard(queenIndex) = newBoard(i)(j).asInstanceOf[Queen]
            queenIndex += 1
          case empty: Empty =>
            newBoard(i)(j) = new Empty(i, j)
        }
      }
    }
    (newBoard, newQueenBoard)
  }

  /**
    *
    * @param function code to run
    * @tparam R return type of the function
    * @return Tuple with the result of function and number of nanoseconds the function took to run.
    */
  def measureRuntime[R](function: => R): (R, Long) = {
    val start = System.nanoTime()
    val result = function //call by name
    val end = System.nanoTime()
    (result, end - start)
  }
}