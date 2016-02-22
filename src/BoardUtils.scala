import java.util.Random

/**
  * Victor Kwak
  */
object BoardUtils {
  /**
    * Uses Fisher-Yates shuffle to shuffle array
    *
    * @param toShuffle array to shuffle
    * @return Shuffled array
    */
  def shuffle(toShuffle: Array[Array[Square]]) {
    val random: Random = new Random {
      var j: Int = 0
      while (j < toShuffle(0).length) {
        {
          val rand: Int = random.nextInt(toShuffle.length)
          swap(toShuffle, 0, j, rand, j)
        }
        ({
          j += 1;
          j - 1
        })
      }
    }
  }

  def swap(array: Array[Array[Square]], i: Int, j: Int, k: Int, l: Int) {
    val temp: Square = array(i)(j)
    array(i)(j) = array(k)(l)
    array(i)(j).setXY(i, j)
    array(k)(l) = temp
    array(k)(l).setXY(k, l)
  }

  def lowestNeighbor(current: Board): Board = {
    val children: util.List[Board] = generateChildren(current)
    var lowest: Board = children.get(0) {
      var i: Int = 1
      while (i < children.size) {
        {
          if (children.get(i).getHeuristicCost < lowest.getHeuristicCost) {
            lowest = children.get(i)
          }
        }
        ({
          i += 1;
          i - 1
        })
      }
    }
    val lowestChildren: util.List[Board] = new util.ArrayList[Board]
    val lowestH: Int = lowest.getHeuristicCost
    for (child <- children) {
      if (child.getHeuristicCost == lowestH) {
        lowestChildren.add(child)
      }
    }
    val random: Random = new Random
    return lowestChildren.get(random.nextInt(lowestChildren.size))
  }

  def generateChildren(current: Board): util.List[Board] = {
    val children: util.List[Board] = new util.ArrayList[Board]
    for (squares <- current.getBoard) {
      for (square <- squares) {
        if (square.isInstanceOf[Queen]) {
          val toPassIn: Array[Array[Square]] = copyBoard(current.getBoard)
          generateChildrenForQueen(toPassIn, square.asInstanceOf[Queen], children)
        }
      }
    }
    return children
  }

  private def generateChildrenForQueen(board: Array[Array[Square]], queen: Queen, children: util.List[Board]) {
    if (queen.x ne 0) {
      swap(board, queen.x, queen.y, 0, queen.y)
      children.add(new Board(board))
    }
    {
      var i: Int = 0
      while (i < board.length - 1) {
        {
          swap(board, i, queen.y, i + 1, queen.y)
          if (i + 1 != queen.x) {
            children.add(new Board(board))
          }
        }
        ({
          i += 1;
          i - 1
        })
      }
    }
  }

  private def printBoard(board: Array[Array[Square]]) {
    for (squares <- board) {
      for (square <- squares) {
        print(square)
      }
      println()
    }
  }

  def copyBoard(board: Array[Array[Square]]): Array[Array[Square]] = {
    val newBoard = Array.ofDim[Square](board.length, board(0).length)
    for (i <- 0 to board.length) {
      for (j <- 0 to board(0).length) {
        if (board(i)(j).isInstanceOf[Queen]) {
          newBoard(i)(j) = new Queen(i, j)
        } else {
          newBoard(i)(j) = new Empty(i, j)
        }
      }
    }
    newBoard
  }
}