import java.util.*;
import javax.swing.ImageIcon;

public class Knight extends Piece {

    public Knight(COLOR color) {
        super(color, TAG.KNIGHT);
        if (color == Piece.COLOR.WHITE)
            this.icon = new ImageIcon("Icons/White_Knight.png");
        else
            this.icon = new ImageIcon("Icons/Black_Knight.png");
    }

    public ArrayList<ArrayList<Integer>> getAttackingSquares() {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> a = new ArrayList<Integer>();
        //<1, 2>
        a.add(1);
        a.add(2);           
        
        //<-1, 2>
        a.add(-1);
        a.add(2); 

        //<-1, -2>
        a.add(-1);
        a.add(-2); 

        //<1, -2>
        a.add(1);
        a.add(-2); 
        
        //<2, 1>
        a.add(2);
        a.add(1); 
        
        //<2, -1>
        a.add(2);
        a.add(-1); 
        
        //<-2, 1>
        a.add(-2);
        a.add(1); 
        
        //<-2, -1>
        a.add(-2);
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