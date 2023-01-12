import java.awt.Color;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Board extends JFrame implements ActionListener{
    
    public static Tile[][] board = new Tile[8][8];
    public static final String column_convert = "abcdefgh";
    private static Tile selected;
    private static Piece.COLOR turn = Piece.COLOR.WHITE;
    private static ArrayList<String> moveHistory = new ArrayList<String>();
    private static final JButton undoButton = new JButton("Undo");

    public Board() {
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setSize(1200, 840);
        setVisible(true);

        add(undoButton);
        undoButton.addActionListener(this);
        undoButton.setBounds(540, 512, 64, 32);

        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                board[col][row] = new Tile(col, row);
                board[col][row].setOpaque(true);
                board[col][row].setBorder(new LineBorder(Color.BLACK));
                board[col][row].addActionListener(this);
                add(board[col][row]);
                if ((col + row)%2 == 1)
                    board[col][row].setBackground(Color.white);
                else
                    board[col][row].setBackground(Color.green);
            }
        }
        resetBoard();
    }

    public void resetBoard() {
        //Clear the board
        for (Tile[] column : board) 
            for (Tile t : column) 
                t.setPiece(null);
        //Initialize white pieces
        addPiece(new Rook(Piece.COLOR.WHITE),  0, 0);
        addPiece(new Knight(Piece.COLOR.WHITE),  1, 0);
        addPiece(new Bishop(Piece.COLOR.WHITE),  2, 0);
        addPiece(new Queen(Piece.COLOR.WHITE),  3, 0);
        addPiece(new King(Piece.COLOR.WHITE),  4, 0);
        addPiece(new Bishop(Piece.COLOR.WHITE),  5, 0);
        addPiece(new Knight(Piece.COLOR.WHITE),  6, 0);
        addPiece(new Rook(Piece.COLOR.WHITE),  7, 0);
        addPiece(new Pawn(Piece.COLOR.WHITE),  0, 1);
        addPiece(new Pawn(Piece.COLOR.WHITE),  1, 1);
        addPiece(new Pawn(Piece.COLOR.WHITE),  2, 1);
        addPiece(new Pawn(Piece.COLOR.WHITE),  3, 1);
        addPiece(new Pawn(Piece.COLOR.WHITE),  4, 1);
        addPiece(new Pawn(Piece.COLOR.WHITE),  5, 1);
        addPiece(new Pawn(Piece.COLOR.WHITE),  6, 1);
        addPiece(new Pawn(Piece.COLOR.WHITE),  7, 1);

        //Initialize black pieces
        addPiece(new Rook(Piece.COLOR.BLACK),  0, 7);
        addPiece(new Knight(Piece.COLOR.BLACK),  1, 7);
        addPiece(new Bishop(Piece.COLOR.BLACK),  2, 7);
        addPiece(new Queen(Piece.COLOR.BLACK),  3, 7);
        addPiece(new King(Piece.COLOR.BLACK),  4, 7);
        addPiece(new Bishop(Piece.COLOR.BLACK),  5, 7);
        addPiece(new Knight(Piece.COLOR.BLACK),  6, 7);
        addPiece(new Rook(Piece.COLOR.BLACK),  7, 7);
        addPiece(new Pawn(Piece.COLOR.BLACK),  0, 6);
        addPiece(new Pawn(Piece.COLOR.BLACK),  1, 6);
        addPiece(new Pawn(Piece.COLOR.BLACK),  2, 6);
        addPiece(new Pawn(Piece.COLOR.BLACK),  3, 6);
        addPiece(new Pawn(Piece.COLOR.BLACK),  4, 6);
        addPiece(new Pawn(Piece.COLOR.BLACK),  5, 6);
        addPiece(new Pawn(Piece.COLOR.BLACK),  6, 6);
        addPiece(new Pawn(Piece.COLOR.BLACK),  7, 6);
    }

    public void addPiece(Piece piece, int col, int row){
        if (board[col][row].getPiece() == null) {
            board[col][row].setPiece(piece);
            piece.setTile(board[col][row]);
            board[col][row].setIcon(piece.getIcon());
        }
        else
            board[col][row].setPiece(null);
    }
    
    public ArrayList<ArrayList<Integer>> findChecks() {
        ArrayList<ArrayList<Integer>> checks = new ArrayList<ArrayList<Integer>>();
        for (Tile[] column : board) {
            for (Tile t : column) {
                Piece p = t.getPiece();
                if (p != null && p.getColor() != turn) {
                    ArrayList<ArrayList<Integer>> attacked = p.getAttackingSquares();
                    for (int i = 0; i < attacked.size(); i++) {
                        ArrayList<Integer> coords = new ArrayList<Integer>();
                        coords.add(t.getCol() + attacked.get(i).get(0));
                        coords.add(t.getRow() + attacked.get(i).get(1));
                        Tile checkTile = board[coords.get(0)][coords.get(1)];
                        if (checkTile.getPiece() != null && checkTile.getPiece().getTag() == Piece.TAG.KING 
                            && checkTile.getPiece().getColor() == turn && !checks.contains(coords))
                            checks.add(coords);
                    }
                }
            }
        }
        return checks;
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == undoButton) {
            undo();
            return;
        }
        draw();
        Tile t = ((Tile)e.getSource());
        Piece p = t.getPiece();
        if (p == null || p.getColor() != turn) {
            if (selected.getPiece() == null)
                return;
            ArrayList<Integer> a = new ArrayList<Integer>();
            a.add(t.getCol()-selected.getCol());
            a.add(t.getRow()-selected.getRow());
            if (selected.getPiece().getMoves().contains(a)) {
                String moveCode = selected.getPiece().getTag().code 
                + column_convert.charAt(selected.getCol()) + (selected.getRow() + 1);
                if (p == null) 
                    moveCode += "->";
                else
                    moveCode += "x" + t.getPiece().getTag().code;
                moveCode += "" + column_convert.charAt(t.getCol()) + (t.getRow() + 1);
                
                move(t);
                if (findChecks().size() > 0)
                    moveCode += "+";                                                    //also check for checkmates here
                System.out.println(moveCode);
                moveHistory.add(moveCode);
            }
            return;
        }

        selected = t;
        offerMoves();
    }

    public void offerMoves() {
        ArrayList<ArrayList<Integer>> moves = selected.getPiece().getMoves();
        for (ArrayList<Integer> a : moves) {
            Tile t = board[selected.getCol() + a.get(0)][selected.getRow() + a.get(1)];
            //move(t);
            if (findChecks().size() > 0) {
                //undo
                continue;
            }
            t.setIcon(new ImageIcon("Icons/Dot.png"));
        }
    }

    public void move(Tile t) {

        //this assumes that the move is valid (handled by offerMoves method)
        t.setPiece(selected.getPiece());
        t.setIcon(selected.getPiece().getIcon());
        
        selected.setPiece(null);
        selected.setIcon(null);

        t.getPiece().setTile(t);

        if (turn == Piece.COLOR.WHITE)
            turn = Piece.COLOR.BLACK;
        else
            turn = Piece.COLOR.WHITE;
        draw();
    }

    public void undo() {
        String last = moveHistory.remove(moveHistory.size()-1);
        if (last.charAt(0) == Character.toUpperCase(last.charAt(0)))
            last = last.substring(1);
        if (last.charAt(last.length()-1) == '+' || last.charAt(last.length()-1) == '#')
            last = last.substring(0, last.length()-1);

        int col = column_convert.indexOf(last.charAt(last.length()-2));
        int row = Character.getNumericValue(last.charAt(last.length()-1))-1;
        Tile t = board[col][row];

        col = column_convert.indexOf(last.charAt(0));
        row = Character.getNumericValue(last.charAt(1)-1);
        addPiece(t.getPiece(), col, row);
        //board[col][row].setPiece(t.getPiece());

        t.setPiece(null);
        if (last.charAt(2) == 'x') {
            if (last.length() == 5)
                addPiece(new Pawn(turn), t.getCol(), t.getRow());
            else {
                char type = last.charAt(3);
                switch (type) {
                    case 'B': 
                        addPiece(new Bishop(turn), t.getCol(), t.getRow());
                        break;
                    case 'N': 
                        addPiece(new Knight(turn), t.getCol(), t.getRow());
                        break;
                    case 'R': 
                        addPiece(new Rook(turn), t.getCol(), t.getRow());
                        break;
                    case 'Q': 
                        addPiece(new Queen(turn), t.getCol(), t.getRow());
                }
            }
        }
        if (turn == Piece.COLOR.WHITE)
            turn = Piece.COLOR.BLACK;
        else
            turn = Piece.COLOR.WHITE;
        draw();
    }

    public void draw() {
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                Tile t = board[col][row];
                if (t.getPiece() != null) 
                    t.setIcon(t.getPiece().getIcon());
                else
                    t.setIcon(null);

                if (turn == Piece.COLOR.WHITE) 
                    t.setBounds(col*64, (7-row)*64, 64, 64);
                else
                    t.setBounds((7-col)*64, row*64, 64, 64);
            }
        }
    }

}