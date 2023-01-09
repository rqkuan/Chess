import java.util.*;
import javax.swing.ImageIcon;

public class Rook extends Piece {

    public Rook(COLOR color, char column, int row) {
        super(color, column, row, TAG.ROOK);
        if (color == Piece.COLOR.WHITE)
            this.icon = new ImageIcon("Icons/White_Rook.png");
        else
            this.icon = new ImageIcon("Icons/Black_Rook.png");
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