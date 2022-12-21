public class Board {
    
    public static Piece[][] board = new Piece[8][8];
    public static String column_convert = "abcdefgh";

    public Board() {
        //Initialize white pieces
        this.addPiece(new Rook(Piece.COLOR.WHITE, 'a', 1));
        this.addPiece(new Knight(Piece.COLOR.WHITE, 'b', 1));
        this.addPiece(new Bishop(Piece.COLOR.WHITE, 'c', 1));
        this.addPiece(new Queen(Piece.COLOR.WHITE, 'd', 1));
        this.addPiece(new King(Piece.COLOR.WHITE, 'e', 1));
        this.addPiece(new Bishop(Piece.COLOR.WHITE, 'f', 1));
        this.addPiece(new Knight(Piece.COLOR.WHITE, 'g', 1));
        this.addPiece(new Rook(Piece.COLOR.WHITE, 'h', 1));
        this.addPiece(new Pawn(Piece.COLOR.WHITE, 'a', 2));
        this.addPiece(new Pawn(Piece.COLOR.WHITE, 'b', 2));
        this.addPiece(new Pawn(Piece.COLOR.WHITE, 'c', 2));
        this.addPiece(new Pawn(Piece.COLOR.WHITE, 'd', 2));
        this.addPiece(new Pawn(Piece.COLOR.WHITE, 'e', 2));
        this.addPiece(new Pawn(Piece.COLOR.WHITE, 'f', 2));
        this.addPiece(new Pawn(Piece.COLOR.WHITE, 'g', 2));
        this.addPiece(new Pawn(Piece.COLOR.WHITE, 'h', 2));

        //Initialize black pieces
        this.addPiece(new Rook(Piece.COLOR.BLACK, 'a', 8));
        this.addPiece(new Knight(Piece.COLOR.BLACK, 'b', 8));
        this.addPiece(new Bishop(Piece.COLOR.BLACK, 'c', 8));
        this.addPiece(new Queen(Piece.COLOR.BLACK, 'd', 8));
        this.addPiece(new King(Piece.COLOR.BLACK, 'e', 8));
        this.addPiece(new Bishop(Piece.COLOR.BLACK, 'f', 8));
        this.addPiece(new Knight(Piece.COLOR.BLACK, 'g', 8));
        this.addPiece(new Rook(Piece.COLOR.BLACK, 'h', 8));
        this.addPiece(new Pawn(Piece.COLOR.BLACK, 'a', 7));
        this.addPiece(new Pawn(Piece.COLOR.BLACK, 'b', 7));
        this.addPiece(new Pawn(Piece.COLOR.BLACK, 'c', 7));
        this.addPiece(new Pawn(Piece.COLOR.BLACK, 'd', 7));
        this.addPiece(new Pawn(Piece.COLOR.BLACK, 'e', 7));
        this.addPiece(new Pawn(Piece.COLOR.BLACK, 'f', 7));
        this.addPiece(new Pawn(Piece.COLOR.BLACK, 'g', 7));
        this.addPiece(new Pawn(Piece.COLOR.BLACK, 'h', 7));
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

    //Change to GUI soon
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