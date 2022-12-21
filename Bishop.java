import java.util.*;

public class Bishop extends Piece {

    public Bishop(COLOR color, char column, int row) {
        super(color, column, row, TAG.BISHOP);
    }

    public ArrayList<ArrayList<Integer>> getAttackingSquares() {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        moves.addAll(addLine(new int[] {1, 1}));
        moves.addAll(addLine(new int[] {-1, 1}));
        moves.addAll(addLine(new int[] {1, -1}));
        moves.addAll(addLine(new int[] {-1, -1}));
        return moves;
    }

}