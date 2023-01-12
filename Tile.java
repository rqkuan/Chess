import javax.swing.*;

public class Tile extends JButton{

    private Piece piece;
    private final int col, row;

    public Tile(int col, int row) {
        this.col = col;
        this.row = row;
    }

    public Piece getPiece() {
        return piece;
    }
    
    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

}
