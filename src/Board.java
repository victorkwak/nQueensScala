import java.util.HashSet;
import java.util.Set;

/**
 * Created by Victor Kwak on 11/9/15.
 */
public class Board {
    final private Square[][] board;
    private Integer heuristicCost;

    public Board(int n) {
        board = new Square[n][n];
        for (int j = 0; j < board[0].length; j++) {
            board[0][j] = new Queen(0, j);
        }
        for (int i = 1; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Empty(i, j);
            }
        }
        BoardUtils.shuffle(board);
    }

    public Board(Square[][] board) {
        this.board = BoardUtils.copyBoard(board);
    }

    public Square[][] getBoard() {
        return board;
    }

    public int getHeuristicCost() {
        if (heuristicCost == null) {
            heuristicCost = heuristic();
        }
        return heuristicCost;
    }

    private int heuristic() {
        Set<Pair> attackingPairs = new HashSet<>();
        for (Square[] squares : board) {
            for (Square square : squares) {
                if (square instanceof Queen) {
                    checkLeft((Queen) square, attackingPairs);
                    checkUpLeft((Queen) square, attackingPairs);
                    checkUpRight((Queen) square, attackingPairs);
                    checkRight((Queen) square, attackingPairs);
                    checkDownLeft((Queen) square, attackingPairs);
                    checkDownRight((Queen) square, attackingPairs);
                }
            }
        }
        return attackingPairs.size();
    }

    private void checkLeft(Queen queen, Set<Pair> attackingPairs) {
        for (int j = queen.y() - 1; j >= 0; j--) {
            if (board[queen.x()][j] instanceof Queen) {
                attackingPairs.add(new Pair(queen, (Queen) board[queen.x()][j]));
            }
        }
    }

    private void checkRight(Queen queen, Set<Pair> attackingPairs) {
        for (int j = queen.y() + 1; j < board[0].length; j++) {
            if (board[queen.x()][j] instanceof Queen) {
                attackingPairs.add(new Pair(queen, (Queen) board[queen.x()][j]));
            }
        }
    }

    private void checkUpRight(Queen queen, Set<Pair> attackingPairs) {
        int i = queen.x() - 1;
        int j = queen.y() + 1;
        while (i >= 0 && j < board.length) {
            if (board[i][j] instanceof Queen) {
                attackingPairs.add(new Pair(queen, (Queen) board[i][j]));
            }
            --i;
            ++j;
        }
    }

    private void checkUpLeft(Queen queen, Set<Pair> attackingPairs) {
        int i = queen.x() - 1;
        int j = queen.y() - 1;
        while (i >= 0 && j >= 0) {
            if (board[i][j] instanceof Queen) {
                attackingPairs.add(new Pair(queen, (Queen) board[i][j]));
            }
            --i;
            --j;
        }
    }

    private void checkDownRight(Queen queen, Set<Pair> attackingPairs) {
        int i = queen.x() + 1;
        int j = queen.y() + 1;
        while (i < board.length && j < board.length) {
            if (board[i][j] instanceof Queen) {
                attackingPairs.add(new Pair(queen, (Queen) board[i][j]));
            }
            ++i;
            ++j;
        }
    }

    private void checkDownLeft(Queen queen, Set<Pair> attackingPairs) {
        int i = queen.x() + 1;
        int j = queen.y() - 1;
        while (i < board.length && j >= 0) {
            if (board[i][j] instanceof Queen) {
                attackingPairs.add(new Pair(queen, (Queen) board[i][j]));
            }
            ++i;
            --j;
        }
    }

    @Override
    public String toString() {
        StringBuilder toString = new StringBuilder();
        for (Square[] squares : board) {
            for (int i = 0; i < squares.length; i++) {
                toString.append(squares[i]);
                if (i == squares.length - 1) {
                    toString.append("\n");
                }
            }
        }
        return toString.toString() +
                getHeuristicCost();
    }
}
