public class Board {
    
    public static Piece[][] board = new Piece[8][8];
    public static String column_convert = "abcdefgh";

    public Board() {
        //Initialize white pieces
        this.addPiece(new Rook("white", 'a', 1, "♖"));
        this.addPiece(new Knight("white", 'b', 1, "♘"));
        this.addPiece(new Bishop("white", 'c', 1, "♗"));
        this.addPiece(new Queen("white", 'd', 1, "♕"));
        this.addPiece(new King("white", 'e', 1, "♔"));
        this.addPiece(new Bishop("white", 'f', 1, "♗"));
        this.addPiece(new Knight("white", 'g', 1, "♘"));
        this.addPiece(new Rook("white", 'h', 1, "♖"));
        this.addPiece(new Pawn("white", 'a', 2, "♙"));
        this.addPiece(new Pawn("white", 'b', 2, "♙"));
        this.addPiece(new Pawn("white", 'c', 2, "♙"));
        this.addPiece(new Pawn("white", 'd', 2, "♙"));
        this.addPiece(new Pawn("white", 'e', 2, "♙"));
        this.addPiece(new Pawn("white", 'f', 2, "♙"));
        this.addPiece(new Pawn("white", 'g', 2, "♙"));
        this.addPiece(new Pawn("white", 'h', 2, "♙"));

        //Initialize black pieces
        this.addPiece(new Rook("black", 'a', 8, "♜"));
        this.addPiece(new Knight("black", 'b', 8, "♞"));
        this.addPiece(new Bishop("black", 'c', 8, "♝"));
        this.addPiece(new Queen("black", 'd', 8, "♛"));
        this.addPiece(new King("black", 'e', 8, "♚"));
        this.addPiece(new Bishop("black", 'f', 8, "♝"));
        this.addPiece(new Knight("black", 'g', 8, "♞"));
        this.addPiece(new Rook("black", 'h', 8, "♜"));
        this.addPiece(new Pawn("black", 'a', 7, "♟︎"));
        this.addPiece(new Pawn("black", 'b', 7, "♟︎"));
        this.addPiece(new Pawn("black", 'c', 7, "♟︎"));
        this.addPiece(new Pawn("black", 'd', 7, "♟︎"));
        this.addPiece(new Pawn("black", 'e', 7, "♟︎"));
        this.addPiece(new Pawn("black", 'f', 7, "♟︎"));
        this.addPiece(new Pawn("black", 'g', 7, "♟︎"));
        this.addPiece(new Pawn("black", 'h', 7, "♟︎"));
    }

    public void addPiece(Piece piece){
        board[piece.getCol()][piece.getRow()] = piece;
    }

    public void offerMoves(char column, int row) {
        //<clear all previously highlighted squares> (to be implemented in GUI)
        //<display moves that selected piece can make (from Piece.getMoves)>
    }

    public void move(char from_column, int from_row, char to_column, int to_row) {
        //this assumes that the move is valid (handled by offerMoves method)
        Piece p = board[column_convert.indexOf(from_column)][from_row-1];
        board[column_convert.indexOf(from_column)][from_row-1] = null;
        board[column_convert.indexOf(to_column)][to_row-1] = p;
    }

    public void draw(String turn) {
        int row_start = 0;
        int col_start = 7;
        String str = "";
        if (turn.equals("white")) {
            row_start = 7;
            col_start = 0;
        }
        for (int row = row_start; row >= 0 && row <= 7; row += -(row_start%5 - 1)) {
            for (int col = col_start; col >= 0 && col <= 7; col += -(col_start%5 - 1)) {
                if (board[col][row] == null)
                    if ((col + row)%2 == 1)
                        str += "□";
                    else
                        str += "■";
                else
                    str += board[col][row].getTag();
                str += " ";
            }
            str += "\n";
        }
        System.out.println(str);
    }

}