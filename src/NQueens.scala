/**
  * Victor Kwak
  */
object NQueens {
  private var stepCounter = 0

  /**
    * For analysis, you should generate a large number of 17-queens instances (>100) and solve them.
    * Measure the percentage of solved problems, search costs and the average running time.
    * Explain why you get such results, for example, why the steepest-ascent hill climbing can only
    * solve about 14% of the problems (remember this percentage is for 8-queen, your answer might be different,
    * smaller percentage), or what kind of improvements have you made to make your algorithms more efficient.
    *
    * @param args
    */
  def main(args: Array[String]) {
    val N = 17
    val HILLCLIMBING_LOOPS = 100
    var totalTime = 0L
    var solvedCounter = 0
    stepCounter = 0

    for (i <- 0 to HILLCLIMBING_LOOPS){
      val start: Long = System.nanoTime
      val board: Board = hillClimbing(new Board(N))
      totalTime += (System.nanoTime - start)
      if (board.getHeuristicCost == 0) {
        solvedCounter += 1
      }
      if (i == HILLCLIMBING_LOOPS - 1) {
        System.out.println(board)
      }
    }
    println("Number of problems attempted: " + HILLCLIMBING_LOOPS)
    println("Number of problems solved: " + solvedCounter)
    println("Average number of steps for Hill Climbing: " + stepCounter.toDouble / HILLCLIMBING_LOOPS)
    println("Average time taken: " + (totalTime / 1000000000d) / HILLCLIMBING_LOOPS + "s")
    println("Average steps take: " + solvedCounter.toDouble / HILLCLIMBING_LOOPS)
  }

  def hillClimbing(problem: Board): Board = {
    var varProblem = problem
    var neighbor: Board = null
    var sameH: Int = 0
    val sameHMax: Int = 100
    while (true) {
      stepCounter += 1
      neighbor = BoardUtils.lowestNeighbor(varProblem)
      if (varProblem.getHeuristicCost < neighbor.getHeuristicCost) {
        return varProblem
      }
      if (neighbor.getHeuristicCost == varProblem.getHeuristicCost) {
        sameH += 1
        if (sameH == sameHMax) {
          return varProblem
        }
      }
      varProblem = neighbor
    }
    null
  }
}