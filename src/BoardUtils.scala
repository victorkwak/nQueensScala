import com.sun.tools.corba.se.idl.SequenceEntry

import scala.collection.mutable.ArrayBuffer
import scala.util.Random
import scala.collection.mutable

/**
  * Created by cary on 2/21/16.
  */
object BoardUtils {

  def shuffle(toShuffle: Array[Array[Square]]) : Unit = {
    val rand : Random = scala.util.Random

    for (i <- 0 to toShuffle(0).length) {
      var rand1 : Int = rand.nextInt(toShuffle.length)
      // TODO: swap(toShuffle, 0, j, rand, j);
    }
  }

  def swap(arr: Array[Array[Square]], i: Int, j: Int, k: Int, l: Int) : Unit = {
    val temp : Square = arr(i)(j)
    arr(i)(j) = arr(k)(l)
    arr(i)(j).setXY(i, j)
    arr(k)(l) = temp
    arr(k)(l).setXY(k, l)
  }

  def lowestNeighbor(current: Board) : Board = {
    val children : ArrayBuffer[Board] = generateChildren(current)
    var lowest : Board = children(0)

    for (i <- 1 to children.length) {
      if (children(i).getHeuristicCost < lowest.getHeuristicCost) {
        lowest = children(1)
      }
    }

    var lowestChildren : ArrayBuffer[Board] = new ArrayBuffer[Board]()
    val lowestH : Int = lowest.getHeuristicCost
    for (i <- 0 to children.length) {
      if (children(i).getHeuristicCost == lowestH) {
        lowestChildren += children(i)
      }
    }

    val rand : Random = new Random()
    lowestChildren(rand.nextInt(lowestChildren.length))
  }

  def generateChildren(current: Board) : ArrayBuffer[Board] = {
    var children = new ArrayBuffer[Board]()

    for (i <- 0 to current.board.length) {
      for (j <- 0 to current.board(i).length) {
        if (current.board(i)(j).isInstanceOf[Queen]) {
          val toPassIn : Array[Array[Square]] = copyBoard(current.board)
          generateChildrenForQueen(toPassIn, current.board(i)(j).asInstanceOf[Queen], children)
        }
      }
    }

    children
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