import java.util.*;
import javax.swing.ImageIcon;

public class King extends Piece {

    public King(COLOR color) {
        super(color, TAG.KING);
        if (color == Piece.COLOR.WHITE)
            this.icon = new ImageIcon("Icons/White_King.png");
        else
            this.icon = new ImageIcon("Icons/Black_King.png");
    }

    public ArrayList<ArrayList<Integer>> getAttackingSquares() {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> a = new ArrayList<Integer>();
        //<1, 1>
        a.add(1);
        a.add(1);

        //<1, 0>
        a.add(1);
        a.add(0);

        //<1, -1>
        a.add(1);
        a.add(-1);

        //<0, 1>
        a.add(0);
        a.add(1);

        //<0, -1>
        a.add(0);
        a.add(-1);

        //<-1, 1>
        a.add(-1);
        a.add(1);

        //<-1, 0>
        a.add(-1);
        a.add(0);
        
        //<-1, -1>
        a.add(-1);
        a.add(-1);

        //add all valid squares
        for (int i = 0; i < a.size(); i += 2) {
            ArrayList<Integer> b = new ArrayList<Integer>(a.subList(i, i + 2));
            if (checkInBounds(b))
                moves.add(b);
        }
        return moves;
    }
}