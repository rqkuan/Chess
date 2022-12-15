abstract class Piece {

    protected String color, tag;
    protected int col, row;

    public Piece(String color, char column, int row, String tag) {
        this.color = color; 
        this.tag = tag;
        this.col = Board.column_convert.get(column);
        this.row = row - 1;
    }    

    public String getColor() {
        return color;
    }

    public String getTag() {
        return tag;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    abstract int[][] getMovePattern(); //Will return all squares that the piece can freely move to

    abstract int[][] getAttackingSquares();
}