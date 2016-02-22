/**
  * Victor Kwak
  */
object NQueens extends App {
  val N = 17
  val HILLCLIMBING_LOOPS = 100
  var totalTime = 0L
  var solvedCounter = 0
  var stepCounter = 0
  var solved: Board = null

  for (i <- 0 until HILLCLIMBING_LOOPS) {
    val boardAndTime = BoardUtils.measureRuntime(AIAlgorithms.hillClimbing(new Board(N)))
    val board: Board = boardAndTime._1._1
    stepCounter += boardAndTime._1._2
    totalTime += boardAndTime._2
    if (board.getHeuristicCost == 0) {
      solvedCounter += 1
      if (solved == null) {
        solved = board
      }
    }
  }
  println(solved)
  println("Number of problems attempted: " + HILLCLIMBING_LOOPS)
  println("Number of problems solved: " + solvedCounter)
  println("Average number of steps for Hill Climbing: " + stepCounter.toDouble / HILLCLIMBING_LOOPS)
  println("Average time taken: " + (totalTime / 1000000000d) / HILLCLIMBING_LOOPS + "s")
  println("Average solve rate: " + (solvedCounter.toDouble / HILLCLIMBING_LOOPS) * 100 + "%")
}