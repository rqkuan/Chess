import java.util.*;
import javax.swing.ImageIcon;

public class Bishop extends Piece {

    public Bishop(COLOR color) {
        super(color, TAG.BISHOP);
        if (color == Piece.COLOR.WHITE)
            this.icon = new ImageIcon("Icons/White_Bishop.png");
        else
            this.icon = new ImageIcon("Icons/Black_Bishop.png");
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