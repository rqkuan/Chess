import java.util.*;
import javax.swing.ImageIcon;

public class Queen extends Piece {

    public Queen(COLOR color) {
        super(color, TAG.QUEEN);
        if (color == Piece.COLOR.WHITE)
            this.icon = new ImageIcon("Icons/White_Queen.png");
        else
            this.icon = new ImageIcon("Icons/Black_Queen.png");
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