import java.util.*;

public class Pawn extends Piece {

    public Pawn(String color, char column, int row, String tag) {
        super(color, column, row, tag);
    }

    public ArrayList<ArrayList<Integer>> getMoves() {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> a = new ArrayList<Integer>();
        //move forward
        a.add(0);
        if (color.equals("white")) 
            a.add(1);
        else
            a.add(-1);
        
        //make sure there's no piece blocking the tile
        if (Board.board[col][row + a.get(1)] == null) {
            moves.add(a);

            //move two spaces forward only from starting square
            if (color.equals("white")) {
                if (row == 1) 
                    a.set(1, 2);
            } else if (row == 6) 
                    a.set(1, -2);

            //make sure there's no piece blocking the tile
            if (Board.board[col][row + a.get(1)] == null) 
                moves.add(a);
        }

        //add the possibility of capturing diagonally
        for (ArrayList<Integer> b : this.getAttackingSquares()) {
            int new_col = col + b.get(0);
            int new_row = row + b.get(1);
            if (checkInBounds(a))
                if (Board.board[new_col][new_row] == null)
                    moves.add(a);
                else if (!Board.board[new_col][new_row].getColor().equals(this.color))
                    moves.add(a);
        }
        return moves;
    }

    public ArrayList<ArrayList<Integer>> getAttackingSquares() {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> a = new ArrayList<Integer>();
        if (color.equals("white")) 
            a.add(1);
        else
            a.add(-1);
        a.add(1);
        moves.add(a);
        a.set(0, -1);
        moves.add(a);
        return moves;
    }

    public void promote(Piece piece) {
        //implement promotion later
    }

}