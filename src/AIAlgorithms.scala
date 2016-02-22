/**
  * Created by Victor Kwak on 2/21/16.
  */
object AIAlgorithms {

  def hillClimbing(problem: Board): (Board, Int) = {
    var varProblem = problem
    var neighbor: Board = null
    val maxSteps = 100
    for (i <- 1 to maxSteps) {
      neighbor = BoardUtils.lowestNeighbor(varProblem)
      if (varProblem.getHeuristicCost < neighbor.getHeuristicCost) {
        return (varProblem, i)
      }
      varProblem = neighbor
    }
    (varProblem, maxSteps)
  }
}
