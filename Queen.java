import java.util.*;

public class Queen extends Piece {

    public Queen(COLOR color, char column, int row) {
        super(color, column, row, TAG.QUEEN);
    }

    public ArrayList<ArrayList<Integer>> getAttackingSquares() {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        moves.addAll(addLine(new int[] {0, 1}));
        moves.addAll(addLine(new int[] {0, -1}));
        moves.addAll(addLine(new int[] {1, 0}));
        moves.addAll(addLine(new int[] {-1, 0}));
        moves.addAll(addLine(new int[] {1, 1}));
        moves.addAll(addLine(new int[] {-1, 1}));
        moves.addAll(addLine(new int[] {1, -1}));
        moves.addAll(addLine(new int[] {-1, -1}));
        return moves;
    }

}