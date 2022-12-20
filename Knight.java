import java.util.*;

public class Knight extends Piece {

    public Knight(String color, char column, int row, String tag) {
        super(color, column, row, tag);
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
        for (int i = 0; i < a.size()/2; i++) {
            ArrayList<Integer> b = new ArrayList<Integer>(a.subList(i, i*2));
            if (checkInBounds(b))
                moves.add(b);
        }
                     
        return moves;
    }

}