import java.util.*;

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
    protected int col, row;
    protected ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();

    public Piece(COLOR color, char column, int row, TAG tag) {
        this.color = color; 
        this.tag = tag;
        this.col = Board.column_convert.indexOf(column);
        this.row = row - 1;
    }    

    public COLOR getColor() {
        return color;
    }

    public TAG getTag() {
        return tag;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    public ArrayList<ArrayList<Integer>> getMoves() {
        ArrayList<ArrayList<Integer>> moves = new ArrayList<ArrayList<Integer>>();
        for (ArrayList<Integer> a : this.getAttackingSquares()) {
            int new_col = col + a.get(0);
            int new_row = row + a.get(1);
            if (checkInBounds(a))
                if (Board.board[new_col][new_row] == null)
                    moves.add(a);
                else if (!Board.board[new_col][new_row].getColor().equals(this.color))
                    moves.add(a);
        }
        return moves;
    }

    abstract ArrayList<ArrayList<Integer>> getAttackingSquares();

    public boolean checkInBounds(List<Integer> a) {
        if (col + a.get(0) >= 0 && col + a.get(0) <= 7 && 
                row + a.get(1) >= 0 && row + a.get(1) <= 7) 
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
            moves.add(a);
            if (Board.board[a.get(0)][a.get(1)] != null) 
                break;
            n++;
            a.set(0, direction[0]*n);
            a.set(1, direction[0]*n);
        }
        return moves;
    }

}