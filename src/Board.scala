/**
  * Created by Victor Kwak on 2/21/16.
  */
class Board {


  /**
    * Created by Victor Kwak on 11/9/15.
    */
  private final val board: Array[Array[Square]] = null
  private var heuristicCost: Integer = null

  def this(n: Int) {
    this()
    board = new Array[Array[Square]](n, n) {
      var j: Int = 0
      while (j < board(0).length) {
        {
          board(0)(j) = new Queen(0, j)
        }
        ({
          j += 1;
          j - 1
        })
      }
    }
    {
      var i: Int = 1
      while (i < board.length) {
        {
          {
            var j: Int = 0
            while (j < board(i).length) {
              {
                board(i)(j) = new Empty(i, j)
              }
              ({
                j += 1;
                j - 1
              })
            }
          }
        }
        ({
          i += 1;
          i - 1
        })
      }
    }
    BoardUtils.shuffle(board)
  }

  def this(board: Array[Array[Square]]) {
    this()
    this.board = BoardUtils.copyBoard(board)
  }

  def this(queens: util.List[Integer]) {
    this()
    val n: Int = queens.size
    board = new Array[Array[Square]](n, n) {
      var j: Int = 0
      while (j < queens.size) {
        {
          board(queens.get(j))(j) = new Queen(queens.get(j), j)
        }
        ({
          j += 1;
          j - 1
        })
      }
    }
    {
      var i: Int = 0
      while (i < board.length) {
        {
          {
            var j: Int = 0
            while (j < board(i).length) {
              {
                if (!(board(i)(j).isInstanceOf[Queen])) {
                  board(i)(j) = new Empty(i, j)
                }
              }
              ({
                j += 1;
                j - 1
              })
            }
          }
        }
        ({
          i += 1;
          i - 1
        })
      }
    }
  }

  def getBoard: Array[Array[Square]] = {
    return board
  }

  def getHeuristicCost: Int = {
    if (heuristicCost == null) {
      heuristicCost = heuristic
    }
    return heuristicCost
  }

  def getFitnessScore: Int = {
    return (board.length * (board.length - 1) / 2) - getHeuristicCost
  }

  def getBoardAsList: util.List[Integer] = {
    val queens: util.List[Integer] = new util.ArrayList[Integer] {
      var j: Int = 0
      while (j < board.length) {
        {
          {
            var i: Int = 0
            while (i < board.length) {
              {
                if (board(i)(j).isInstanceOf[Queen]) {
                  queens.add(i)
                  break //todo: break is not supported
                }
              }
              ({
                i += 1;
                i - 1
              })
            }
          }
        }
        ({
          j += 1;
          j - 1
        })
      }
    }
    return queens
  }

  private def heuristic: Int = {
    val attackingPairs: util.Set[Pair] = new util.HashSet[Pair]
    for (squares <- board) {
      for (square <- squares) {
        if (square.isInstanceOf[Queen]) {
          checkLeft(square.asInstanceOf[Queen], attackingPairs)
          checkUpLeft(square.asInstanceOf[Queen], attackingPairs)
          checkUpRight(square.asInstanceOf[Queen], attackingPairs)
          checkRight(square.asInstanceOf[Queen], attackingPairs)
          checkDownLeft(square.asInstanceOf[Queen], attackingPairs)
          checkDownRight(square.asInstanceOf[Queen], attackingPairs)
        }
      }
    }
    return attackingPairs.size
  }

  private def checkLeft(queen: Queen, attackingPairs: util.Set[Pair]) {
    {
      var j: Int = queen.y - 1
      while (j >= 0) {
        {
          if (board(queen.x)(j).isInstanceOf[Queen]) {
            attackingPairs.add(new Pair(queen, board(queen.x)(j).asInstanceOf[Queen]))
          }
        }
        ({
          j -= 1;
          j + 1
        })
      }
    }
  }

  private def checkRight(queen: Queen, attackingPairs: util.Set[Pair]) {
    {
      var j: Int = queen.y + 1
      while (j < board(0).length) {
        {
          if (board(queen.x)(j).isInstanceOf[Queen]) {
            attackingPairs.add(new Pair(queen, board(queen.x)(j).asInstanceOf[Queen]))
          }
        }
        ({
          j += 1;
          j - 1
        })
      }
    }
  }

  private def checkUpRight(queen: Queen, attackingPairs: util.Set[Pair]) {
    var i: Int = queen.x - 1
    var j: Int = queen.y + 1
    while (i >= 0 && j < board.length) {
      if (board(i)(j).isInstanceOf[Queen]) {
        attackingPairs.add(new Pair(queen, board(i)(j).asInstanceOf[Queen]))
      }
      i -= 1
      j += 1
    }
  }

  private def checkUpLeft(queen: Queen, attackingPairs: util.Set[Pair]) {
    var i: Int = queen.x - 1
    var j: Int = queen.y - 1
    while (i >= 0 && j >= 0) {
      if (board(i)(j).isInstanceOf[Queen]) {
        attackingPairs.add(new Pair(queen, board(i)(j).asInstanceOf[Queen]))
      }
      i -= 1
      j -= 1
    }
  }

  private def checkDownRight(queen: Queen, attackingPairs: util.Set[Pair]) {
    var i: Int = queen.x + 1
    var j: Int = queen.y + 1
    while (i < board.length && j < board.length) {
      if (board(i)(j).isInstanceOf[Queen]) {
        attackingPairs.add(new Pair(queen, board(i)(j).asInstanceOf[Queen]))
      }
      i += 1
      j += 1
    }
  }

  private def checkDownLeft(queen: Queen, attackingPairs: util.Set[Pair]) {
    var i: Int = queen.x + 1
    var j: Int = queen.y - 1
    while (i < board.length && j >= 0) {
      if (board(i)(j).isInstanceOf[Queen]) {
        attackingPairs.add(new Pair(queen, board(i)(j).asInstanceOf[Queen]))
      }
      i += 1
      j -= 1
    }
  }

  def compareTo(o: Board): Int = {
    return this.getFitnessScore - o.getFitnessScore
  }

  override def toString: String = {
    val toString: StringBuilder = new StringBuilder
    for (squares <- board) {
      {
        var i: Int = 0
        while (i < squares.length) {
          {
            toString.append(squares(i))
            if (i == squares.length - 1) {
              toString.append("\n")
            }
          }
          ({
            i += 1;
            i - 1
          })
        }
      }
    }
    return toString.toString + getHeuristicCost
  }

}
