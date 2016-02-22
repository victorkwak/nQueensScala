/**
  * Victor Kwak
  */
object NQueens extends App{
  private var stepCounter: Int = 0

  /**
    * For analysis, you should generate a large number of 17-queens instances (>100) and solve them.
    * Measure the percentage of solved problems, search costs and the average running time.
    * Explain why you get such results, for example, why the steepest-ascent hill climbing can only
    * solve about 14% of the problems (remember this percentage is for 8-queen, your answer might be different,
    * smaller percentage), or what kind of improvements have you made to make your algorithms more efficient.
    *
    */

    val N: Int = 17
    val HILLCLIMBING_LOOPS: Int = 100
    var totalTime: Long = 0
    var solvedCounter: Int = 0
    stepCounter = 0
    {
      var i: Int = 0
      while (i < HILLCLIMBING_LOOPS) {
        {
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
        ({
          i += 1; i - 1
        })
      }

    System.out.println("Number of problems attempted: " + HILLCLIMBING_LOOPS)
    System.out.println("Number of problems solved: " + solvedCounter)
    System.out.println("Average number of steps for Hill Climbing: " + stepCounter.toDouble / HILLCLIMBING_LOOPS)
    System.out.println("Average time taken: " + (totalTime / 1000000000d) / HILLCLIMBING_LOOPS + "s")
    System.out.println("Average steps take: " + solvedCounter.toDouble / HILLCLIMBING_LOOPS)


  def hillClimbing(problem: Board): Board = {
    var neighbor: Board = null
    var sameH: Int = 0
    val sameHMax: Int = 100
    while (true) {
      stepCounter += 1
      neighbor = BoardUtils.lowestNeighbor(problem)
      if (problem.getHeuristicCost < neighbor.getHeuristicCost) {
        return problem
      }
      if (neighbor.getHeuristicCost == problem.getHeuristicCost) {
        sameH += 1
        if (sameH == sameHMax) {
          return problem
        }
      }
      problem = neighbor
    }
  }
}