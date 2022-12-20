import java.util.*;

public class Rook extends Piece {

    public Rook(String color, char column, int row, String tag) {
        super(color, column, row, tag);
    }

    public ArrayList<ArrayList<Integer>> getAttackingSquares() {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        moves.addAll(addLine(new int[] {0, 1}));
        moves.addAll(addLine(new int[] {0, -1}));
        moves.addAll(addLine(new int[] {1, 0}));
        moves.addAll(addLine(new int[] {-1, 0}));
        return moves;
    }

}