import java.util.*;

public class Rook extends Piece {

    public Rook(COLOR color, char column, int row) {
        super(color, column, row, TAG.ROOK);
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