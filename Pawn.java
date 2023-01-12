import java.util.*;
import javax.swing.ImageIcon;

public class Pawn extends Piece {

    public Pawn(COLOR color) {
        super(color, TAG.PAWN);
        if (color == Piece.COLOR.WHITE)
            this.icon = new ImageIcon("Icons/White_Pawn.png");
        else
            this.icon = new ImageIcon("Icons/Black_Pawn.png");
    }

    public ArrayList<ArrayList<Integer>> getMoves() {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> a = new ArrayList<Integer>();
        //move forward
        a.add(0);
        if (color == Piece.COLOR.WHITE) 
            a.add(1);
        else
            a.add(-1);
        
        //make sure there's no piece blocking the tile
        if (Board.board[tile.getCol()][tile.getRow() + a.get(1)].getPiece() == null) {
            moves.add(new ArrayList<Integer>(a));
            //move two spaces forward only from starting square
            if (color == COLOR.WHITE) {
                if (tile.getRow() == 1) 
                    a.set(1, 2);
            } else if (tile.getRow() == 6) 
                    a.set(1, -2);

            //make sure there's no piece blocking the tile
            if (Board.board[tile.getCol()][tile.getRow() + a.get(1)].getPiece() == null) 
                moves.add(new ArrayList<Integer>(a));
        }

        //add attacking squares
        for (ArrayList<Integer> b : getAttackingSquares()) {
            int new_col = tile.getCol() + b.get(0);
            int new_row = tile.getRow() + b.get(1);
            if (Board.board[new_col][new_row].getPiece() == null)
                continue;
            if (!Board.board[new_col][new_row].getPiece().getColor().equals(color))
                moves.add(b);
        }
        return moves;
    }

    public ArrayList<ArrayList<Integer>> getAttackingSquares() {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> a = new ArrayList<Integer>();
        //<diagonal to the right>
        a.add(1);
        if (color == COLOR.WHITE) 
            a.add(1);
        else
            a.add(-1);

        //<diagonal to the left>
        a.add(-1);
        if (color == COLOR.WHITE) 
            a.add(1);
        else
            a.add(-1);
        
        //add all valid squares
        for (int i = 0; i < a.size(); i += 2) {
            ArrayList<Integer> b = new ArrayList<Integer>(a.subList(i, i + 2));
            if (checkInBounds(b))
                moves.add(b);
        }
        return moves;
    }

    public void promote(Piece piece) {
        //implement promotion later
    }

}