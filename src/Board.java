import scala.Tuple2;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Victor Kwak on 11/9/15.
 */
public class Board {
    // Square and Queen objects are written in Scala but can be seamlessly used in Java without special syntax
    final private Square[][] board;
    final private Queen[] queens;
    private Integer heuristicCost;

    public Board(final int n) {
        board = new Square[n][n];
        queens = new Queen[n];
        int queenIndex = 0;
        for (int j = 0; j < board[0].length; j++) {
            board[0][j] = new Queen(0, j);
            queens[queenIndex++] = (Queen) board[0][j];
        }
        for (int i = 1; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = new Empty(i, j);
            }
        }
        BoardUtils.shuffle(board);
    }

    public Board(final Square[][] board) {
        //Scala Tuple being used natively in Java
        Tuple2<Square[][], Queen[]> tuple = BoardUtils.copyBoard(board);
        this.board = tuple._1();
        this.queens = tuple._2();
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
        for (Queen queen : queens) {
            if (!queen.left()) {
                checkLeft(queen, attackingPairs);
            }
            if (!queen.upLeft()) {
                checkUpLeft(queen, attackingPairs);
            }
            if (!queen.upRight()) {
                checkUpRight(queen, attackingPairs);
            }
            if (!queen.right()) {
                checkRight(queen, attackingPairs);
            }
            if (!queen.downLeft()) {
                checkDownLeft(queen, attackingPairs);
            }
            if (!queen.downRight()) {
                checkDownRight(queen, attackingPairs);
            }
        }
        return attackingPairs.size();
    }

    private void checkLeft(final Queen attackingQueen, final Set<Pair> attackingPairs) {
        for (int j = attackingQueen.y() - 1; j >= 0; j--) {
            if (board[attackingQueen.x()][j] instanceof Queen) {
                Queen attackedQueen = (Queen) board[attackingQueen.x()][j];
                attackingPairs.add(new Pair(attackingQueen, attackedQueen));
                attackedQueen.right_$eq(true);
            }
        }
    }

    private void checkRight(final Queen attackingQueen, final Set<Pair> attackingPairs) {
        for (int j = attackingQueen.y() + 1; j < board[0].length; j++) {
            if (board[attackingQueen.x()][j] instanceof Queen) {
                Queen attackedQueen = (Queen) board[attackingQueen.x()][j];
                attackingPairs.add(new Pair(attackingQueen, attackedQueen));
                attackedQueen.left_$eq(true);
            }
        }
    }

    private void checkUpRight(final Queen attackingQueen, final Set<Pair> attackingPairs) {
        int i = attackingQueen.x() - 1;
        int j = attackingQueen.y() + 1;
        while (i >= 0 && j < board.length) {
            if (board[i][j] instanceof Queen) {
                Queen attackedQueen = (Queen) board[i][j];
                attackingPairs.add(new Pair(attackingQueen, attackedQueen));
                attackedQueen.downLeft_$eq(true);
            }
            --i;
            ++j;
        }
    }

    private void checkUpLeft(final Queen attackingQueen, final Set<Pair> attackingPairs) {
        int i = attackingQueen.x() - 1;
        int j = attackingQueen.y() - 1;
        while (i >= 0 && j >= 0) {
            if (board[i][j] instanceof Queen) {
                Queen attackedQueen = (Queen) board[i][j];
                attackingPairs.add(new Pair(attackingQueen, attackedQueen));
                attackedQueen.downRight_$eq(true);
            }
            --i;
            --j;
        }
    }

    private void checkDownRight(final Queen attackingQueen, final Set<Pair> attackingPairs) {
        int i = attackingQueen.x() + 1;
        int j = attackingQueen.y() + 1;
        while (i < board.length && j < board.length) {
            if (board[i][j] instanceof Queen) {
                Queen attackedQueen = (Queen) board[i][j];
                attackingPairs.add(new Pair(attackingQueen, attackedQueen));
                attackedQueen.upLeft_$eq(true);
            }
            ++i;
            ++j;
        }
    }

    private void checkDownLeft(final Queen attackingQueen, final Set<Pair> attackingPairs) {
        int i = attackingQueen.x() + 1;
        int j = attackingQueen.y() - 1;
        while (i < board.length && j >= 0) {
            if (board[i][j] instanceof Queen) {
                Queen attackedQueen = (Queen) board[i][j];
                attackingPairs.add(new Pair(attackingQueen, attackedQueen));
                attackedQueen.upRight_$eq(true);
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
        return toString.toString();
    }
}
