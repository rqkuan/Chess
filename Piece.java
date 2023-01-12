import java.util.*;
import javax.swing.ImageIcon;

abstract class Piece {

    public enum COLOR {WHITE, BLACK}
    public enum TAG {
        PAWN(""), 
        BISHOP("B"), 
        KNIGHT("N"), 
        ROOK("R"), 
        QUEEN("Q"), 
        KING("K");

        public final String code;
        private TAG(String code) {
            this.code = code;
        }
    }
    protected final COLOR color;
    protected final TAG tag;
    protected Tile tile;
    protected ImageIcon icon;

    public Piece(COLOR color, TAG tag) {
        this.color = color; 
        this.tag = tag;
    }    

    public COLOR getColor() {
        return color;
    }

    public TAG getTag() {
        return tag;
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public ImageIcon getIcon() {
        return icon;
    }


    public ArrayList<ArrayList<Integer>> getMoves() {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        for (ArrayList<Integer> a : getAttackingSquares()) {
            int new_col = tile.getCol() + a.get(0);
            int new_row = tile.getRow() + a.get(1);
            if (Board.board[new_col][new_row].getPiece() == null)
                moves.add(a);
            else if (!Board.board[new_col][new_row].getPiece().getColor().equals(color))
                moves.add(a);
        }
        return moves;
    }

    abstract ArrayList<ArrayList<Integer>> getAttackingSquares();

    public boolean checkInBounds(ArrayList<Integer> a) {
        if (tile.getCol() + a.get(0) >= 0 && tile.getCol() + a.get(0) <= 7 && 
            tile.getRow() + a.get(1) >= 0 && tile.getRow() + a.get(1) <= 7) 
            return true;
        return false;
    }

    public ArrayList<ArrayList<Integer>> addLine(int[] direction) {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> a = new ArrayList<Integer>();
        int n = 1;
        a.add(direction[0]);
        a.add(direction[1]);
        while(checkInBounds(a)) {
            moves.add(new ArrayList<Integer>(a));
            if (Board.board[tile.getCol() + a.get(0)][tile.getRow() + a.get(1)].getPiece() != null) 
                break;
            n++;
            a.set(0, direction[0]*n);
            a.set(1, direction[1]*n);
        }
        return moves;
    }

}