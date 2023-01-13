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
    private static JPanel sidebar = new JPanel();  
    private static JLabel title = new JLabel();  
    private static JTextArea moveHistoryDisplay = new JTextArea();  
    private static boolean gameover = false;

    public Board() {
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setSize(1200, 840);
        setVisible(true);



        add(sidebar);
        sidebar.setBounds(512, 0, 240, 512);
        sidebar.setBackground(Color.decode("#BBBBBB"));
        sidebar.setLayout(null);
        
        sidebar.add(title);
        title.setText("Chess");
        title.setBounds(20, 50, 200, 50);

        JScrollPane scp = new JScrollPane(moveHistoryDisplay);
        sidebar.add(scp);
        scp.setBounds(20, 100, 200, 350);
        moveHistoryDisplay.setEditable(false);
        
        sidebar.add(undoButton);
        undoButton.addActionListener(this);
        undoButton.setOpaque(true);
        undoButton.setBorder(new LineBorder(Color.black));
        undoButton.setBackground(Color.green);
        undoButton.setBounds(20, 470, 100, 32);

        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                board[col][row] = new Tile(col, row);
                board[col][row].setOpaque(true);
                board[col][row].setBorder(new LineBorder(Color.black));
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
        
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == undoButton) {
            if (moveHistory.size() != 0) {
                undo();
                changeTurn();
                updateMoveHistory();
                draw();
            }
            return;
        }
        Tile t = ((Tile)e.getSource());
        Piece p = t.getPiece();
        if (p == null || p.getColor() != turn) {
            if (selected == null || selected.getPiece() == null) {
                return;
            }
            ArrayList<Integer> a = new ArrayList<Integer>();
            a.add(t.getCol()-selected.getCol());
            a.add(t.getRow()-selected.getRow());
            if (offerMoves().contains(a)) {                
                move(t);
                //Check for pawn promotion
                if (t.getPiece().getTag() == Piece.TAG.PAWN && (t.getRow() == 0 || t.getRow() == 7)) {
                    t.setPiece(null);
                    addPiece(new Queen(turn), t.getCol(), t.getRow());
                } else {
                    //Check for castling
                    String check_castled = moveHistory.get(moveHistory.size()-1);
                    if (check_castled.charAt(0) == 'K' && check_castled.charAt(1) == 'e') {
                        if (check_castled.charAt(check_castled.length()-2) == 'c') {
                            board[0][t.getRow()].setPiece(null);
                            addPiece(new Rook(turn), 3, t.getRow());
                            moveHistory.set(moveHistory.size() - 1, "O-O-O");
                        } else if (check_castled.charAt(check_castled.length()-2) == 'g') {
                            board[7][t.getRow()].setPiece(null);
                            addPiece(new Rook(turn), 5, t.getRow());
                            moveHistory.set(moveHistory.size() - 1, "O-O");
                        }
                    }
                }
                changeTurn();
                updateMoveHistory();
                gameover = true;
                for (int col = 0; col < 8; col++) {
                    for (int row = 0; row < 8; row++) {
                        if (board[col][row].getPiece() != null && board[col][row].getPiece().getColor() == turn) {
                            selected = board[col][row];
                            if (offerMoves().size() != 0) {
                                gameover = false;
                            }
                        }
                    }
                    if (!gameover)
                        break;
                }       
                selected = null;

                if (gameover) {
                    if (findChecks().size() > 0) {
                        String checkmate_code = moveHistory.get(moveHistory.size()-1);
                        checkmate_code = checkmate_code.substring(0, checkmate_code.length() - 1);
                        moveHistory.set(moveHistory.size()-1, checkmate_code);
                        changeTurn();
                        System.out.println("Checkmate! (" + turn + " WINS)");
                    } else
                        System.out.println("Stalemate.");
                    return;
                }
            } else if (p == null)
                selected = t;
            draw();
            return;
        }

        selected = t;
        draw();
        offerMoves();
    }

    private ArrayList<ArrayList<Integer>> offerMoves() {
        ArrayList<ArrayList<Integer>> moves = selected.getPiece().getMoves();
        ArrayList<ArrayList<Integer>> valid = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < moves.size(); i++) {
            ArrayList<Integer> a = moves.get(i);
            Tile t = board[selected.getCol() + a.get(0)][selected.getRow() + a.get(1)];
            move(t);
            if (findChecks().size() == 0) {
                valid.add(a);
                changeTurn();
                undo();
                changeTurn();
                t.setIcon(new ImageIcon("Icons/Dot.png"));
                continue;
            }
            changeTurn();
            undo();
            changeTurn();
        }
        for (ArrayList<Integer> a : checkCastles()) {
            board[selected.getCol() + a.get(0)][selected.getRow() + a.get(1)].setIcon(new ImageIcon("Icons/Dot.png"));
            valid.add(a);
        }
        return valid;
    }

    private ArrayList<Integer> checkEnPassant() {
        if (selected.getPiece().getTag() != Piece.TAG.PAWN)
            return new ArrayList<Integer>();
        String previous = moveHistory.get(moveHistory.size() - 1);
        if (previous.charAt(0))
    }

    private ArrayList<ArrayList<Integer>> checkCastles() {
        ArrayList<ArrayList<Integer>> castles = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> a = new ArrayList<Integer>();
        a.add(2);
        a.add(0);
        a.add(-2);
        a.add(0);
        castles.add(new ArrayList<Integer>(a.subList(0, 2)));
        castles.add(new ArrayList<Integer>(a.subList(2, 4)));
        boolean short_castle = true;
        boolean long_castle = true;
        if (selected.getPiece().getTag() != Piece.TAG.KING)
            return new ArrayList<ArrayList<Integer>>();
        for (int i = turn.color_num; i < moveHistory.size(); i += 2) {
            if (moveHistory.get(i).charAt(0) == 'K')
                return new ArrayList<ArrayList<Integer>>();
            if (moveHistory.get(i).substring(0, 2).equals("Ra"))
                long_castle = false;
            if (moveHistory.get(i).substring(0, 2).equals("Rh"))
                short_castle = false;
        }
        for (Tile[] column : board) {
            for (Tile t : column) {
                Piece p = t.getPiece();
                if (p != null && p.getColor() != turn) {
                    ArrayList<ArrayList<Integer>> attacked = p.getAttackingSquares();
                    for (int i = 0; i < attacked.size(); i++) {
                        ArrayList<Integer> coords = new ArrayList<Integer>();
                        coords.add(t.getCol() + attacked.get(i).get(0));
                        coords.add(t.getRow() + attacked.get(i).get(1));
                        if (coords.get(1) == (8 - turn.color_num)%8) {
                            if (coords.get(0) == 4)
                                return new ArrayList<ArrayList<Integer>>();
                            if (coords.get(0) > 4 && coords.get(0) < 7)
                                short_castle = false;
                            if (coords.get(0) > 1 && coords.get(0) < 4)
                                long_castle = false;
                        }
                    }
                }
            }
        }
        for (int i = 1; i < 4; i++)
            if (board[i][selected.getRow()].getPiece() != null)
                long_castle = false;
        for (int i = 5; i < 7; i++)
            if (board[i][selected.getRow()].getPiece() != null)
                short_castle = false;
        if (long_castle && short_castle)
            return castles;
        if (short_castle)
            return new ArrayList<ArrayList<Integer>>(castles.subList(0, 1));
        if (long_castle)
            return new ArrayList<ArrayList<Integer>>(castles.subList(1, 2));
        return new ArrayList<ArrayList<Integer>>();
    }

    private ArrayList<ArrayList<Integer>> findChecks() {
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

    public void move(Tile t) {
        String moveCode = selected.getPiece().getTag().code 
                + column_convert.charAt(selected.getCol()) + (selected.getRow() + 1);
        if (t.getPiece() == null) 
            moveCode += "->";
        else
            moveCode += "x" + t.getPiece().getTag().code;
        moveCode += "" + column_convert.charAt(t.getCol()) + (t.getRow() + 1);

        //this assumes that the move is valid (handled by offerMoves method)
        t.setPiece(selected.getPiece());
        t.setIcon(selected.getPiece().getIcon());
        t.getPiece().setTile(t);

        selected.setPiece(null);
        selected.setIcon(null);
        selected = null;

        if (findChecks().size() > 0)
            moveCode += "+";
        moveHistory.add(moveCode);
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
        selected = board[col][row];

        t.setPiece(null);
        t.setIcon(null);
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
    }

    private void changeTurn() {
        if (turn == Piece.COLOR.WHITE)
            turn = Piece.COLOR.BLACK;
        else
            turn = Piece.COLOR.WHITE;
    }

    public void updateMoveHistory() {
        String str = "1. ";
        for (int i = 0; i < moveHistory.size(); i += 2) {
            str += moveHistory.get(i) + ", ";
            if (moveHistory.size() > i + 1)
                str += moveHistory.get(i + 1) + "\n" + (i/2 + 2) + ". ";
        }
        str += "...";
        moveHistoryDisplay.setText(str); 
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