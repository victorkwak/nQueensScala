import java.util.Random

import scala.collection.mutable.ArrayBuffer

/**
  * Victor Kwak
  */
object BoardUtils {

  def swap(array: Array[Array[Square]], i: Int, j: Int, k: Int, l: Int) {
    val temp: Square = array(i)(j)
    array(i)(j) = array(k)(l)
    array(i)(j).setXY(i, j)
    array(k)(l) = temp
    array(k)(l).setXY(k, l)
  }


  private def generateChildrenForQueen(board: Array[Array[Square]], queen: Queen, children: ArrayBuffer[Board]) {
    if (queen.x != 0) {
      swap(board, queen.x, queen.y, 0, queen.y)
      children += new Board(board)
    }
    for (i <- 0 to board.length) {
      swap(board, i, queen.y, i + 1, queen.y)
      if (i + 1 != queen.x){
        children += new Board(board)
      }
    }
  }

  private def printBoard(board: Array[Array[Square]]) {
    for (squares <- board) {
      for (square <- squares) {
        print(square)
      }
      println
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