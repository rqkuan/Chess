import java.awt.*;
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
    private static final JButton button1 = new JButton("Undo");  
    private static final JButton button2 = new JButton("Concede");  
    private static JPanel sidebar = new JPanel();  
    private static JLabel title = new JLabel();  
    private static JTextArea moveHistoryDisplay = new JTextArea();  
    private static boolean gameover = false;

    /**
     * Board()
     * Initializes all buttons and draws the intial screen
     */
    public Board() {
        //Setting the Frame
        setTitle("Chess");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        setSize(752, 540);
        setVisible(true);
        Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dm.getWidth() - this.getWidth()) / 2);
        int y = (int) ((dm.getHeight() - this.getHeight()) / 2);
        this.setLocation(x, y);

        //Sidebar
        add(sidebar);
        sidebar.setBounds(512, 0, 240, 512);
        sidebar.setBackground(Color.decode("#BBBBBB"));
        sidebar.setLayout(null);
        
        //Title (in green above the move history display)
        sidebar.add(title);
        title.setText("Move History");
        title.setOpaque(true);
        title.setBackground(Color.decode("#779556"));
        title.setBorder(new LineBorder(Color.black));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 14));
        title.setBounds(20, 40, 200, 50);

        //Move history display (scrollable)
        JScrollPane scp = new JScrollPane(moveHistoryDisplay);
        sidebar.add(scp);
        scp.setBounds(20, 90, 200, 350);
        moveHistoryDisplay.setEditable(false);
        
        //Left button (Undo/Play Again)
        sidebar.add(button1);
        button1.addActionListener(this);
        button1.setOpaque(true);
        button1.setBorder(new LineBorder(Color.black));
        button1.setBackground(Color.decode("#779556"));
        button1.setBounds(20, 460, 90, 32);

        //Right button (Concede/Quit)
        sidebar.add(button2);
        button2.addActionListener(this);
        button2.setOpaque(true);
        button2.setBorder(new LineBorder(Color.black));
        button2.setBackground(Color.decode("#779556"));
        button2.setBounds(130, 460, 90, 32);

        //Filling the board with tiles
        for (int col = 0; col < 8; col++) {
            for (int row = 0; row < 8; row++) {
                board[col][row] = new Tile(col, row);
                board[col][row].setOpaque(true);
                board[col][row].setBorder(new LineBorder(Color.black));
                board[col][row].addActionListener(this);
                add(board[col][row]);
            }
        }
        //Setting up the pieces and drawing the board & move history
        reset();
        updateMoveHistory();
        draw();
    }

    /**
     * reset
     * Resets/initializes the board position and move history
     * Would have been used for customizing the position
     */
    public void reset() {
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

    /**
     * addPiece
     * @param piece
     * @param col
     * @param row
     * Adds a piece to a tile on the board (If the tile has an existing piece, it erases it and leaves it blank)
     * Would have also been used for customizing position
     */
    public void addPiece(Piece piece, int col, int row){
        if (board[col][row].getPiece() == null) {
            board[col][row].setPiece(piece);
            piece.setTile(board[col][row]);
            board[col][row].setIcon(piece.getIcon());
        }
        else
            board[col][row].setPiece(null);
    }
    
    
    /**
     * actionPerformed
     * @param e
     * Sensing all button actions and reacting accordingly
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //Left button clicked
        if (e.getSource() == button1) {
            //Play Again
            if (gameover) {
                gameover = false; 
                selected = null;
                reset();
                turn = Piece.COLOR.WHITE;
                moveHistory.clear();
                updateMoveHistory();
                title.setText("Move History");
                button1.setText("Undo");
                button2.setText("Concede");
                draw();
                return;
            }

            //Undo
            if (moveHistory.size() != 0) {
                undo();
                changeTurn();
                updateMoveHistory();
                selected = null;
                draw();
            }
            return;
        }

        //Right button clicked
        if (e.getSource() == button2) {
            //Quit
            if (gameover)
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
            
            //Concede
            gameover = true; 
            changeTurn(); 
            title.setText(turn + " WINS (Resignation)");
            button1.setText("Play Again");
            button2.setText("Quit");
            return;
        }

        //Don't let the player touch the board if the game is over
        if (gameover)
            return;

        
        Tile t = ((Tile)e.getSource());
        Piece p = t.getPiece();
        //If the clicked tile is not one of the player's pieces
        if (p == null || p.getColor() != turn) {
            //If the player has not selected a piece, don't do anything
            if (selected == null || selected.getPiece() == null) {
                return;
            }
            
            //Check if the tile clicked is a tile that the selected piece can move to (and move the piece if so)
            ArrayList<Integer> a = new ArrayList<Integer>();
            a.add(t.getCol()-selected.getCol());
            a.add(t.getRow()-selected.getRow());
            if (offerMoves().contains(a)) {                
                move(t);
                
                //Special moves that require additional code: 
                //Pawn moves: 
                if (t.getPiece().getTag() == Piece.TAG.PAWN) { 
                    //Special case: Promotion  
                    if (t.getRow() == 0 || t.getRow() == 7) {
                        t.setPiece(null);
                        addPiece(new Queen(turn), t.getCol(), t.getRow());
                        String previous = moveHistory.remove(moveHistory.size()-1);
                        previous += "=Q";
                        if (previous.charAt(previous.length()-4) == '+') 
                            previous = previous.substring(0, previous.length()-4) 
                            + previous.substring(previous.length()-3) + "+";
                        moveHistory.add(previous);
                    }
                    //Special Case: En Passant
                    else {
                        Tile behindPawn = board[t.getCol()][t.getRow() + turn.color_num*2 - 1];
                        if (behindPawn.getPiece() != null && behindPawn.getPiece().getTag() == Piece.TAG.PAWN) {
                            int col = column_convert.indexOf(moveHistory.get(moveHistory.size()-1).charAt(0));
                            int row = Character.getNumericValue(moveHistory.get(moveHistory.size()-1).charAt(1)) - 1;
                            undo();
                            selected = board[col][row];
                            if (checkEnPassant().size() != 0) {
                                behindPawn.setPiece(null);
                                String previous = moveHistory.remove(moveHistory.size() - 1);
                                moveHistory.add(previous.substring(0, 2)
                                    + "x" + previous.charAt(4) + previous.charAt(1));
                            }
                            move(t);
                        }
                    }  
                } 

                //King moves: 
                else {
                    //Special case: Castling
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

                //After moving a piece, switch to the other player's turn and update move history
                changeTurn();
                if (findChecks().size() != 0)
                            moveHistory.set(moveHistory.size() - 1, moveHistory.get(moveHistory.size() - 1) + "+");
                updateMoveHistory();
                
                //Look for checkmates/stalemates
                if (checkGameover()) {
                    button1.setText("Play Again");
                    button2.setText("Quit");
                    draw();
                    return;
                }
            } 
            selected = null;
            draw();
            return;
        }

        /*If the tile clicked had one of the player's pieces on it, 
        select the tile and offer the player options for moves to take*/
        selected = t;
        draw();
        offerMoves();
    }

    /**
     * offerMoves
     * @return ArrayList<ArrayList<Integer>> valid
     * Checks what tiles the selected piece can "see" and offer them as a move if they are legal
     * (returns an arraylist of coordinates of valid moves)
     * (an illegal move is a move that would leave your king in check)
     */
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

        //Special case: Offer castling
        for (ArrayList<Integer> a : checkCastles()) {
            board[selected.getCol() + a.get(0)][selected.getRow() + a.get(1)].setIcon(new ImageIcon("Icons/Dot.png"));
            valid.add(a);
        }

        //Special case: Offer en passant
        ArrayList<Integer> a = checkEnPassant();
        if (a.size() != 0) {
            board[selected.getCol() + a.get(0)][selected.getRow() + a.get(1)].setIcon(new ImageIcon("Icons/Dot.png"));
            valid.add(a);
        }

        return valid;
    }

    /**
     * checkGameover
     * @return gameover
     * Checks if the game has reached checkmate or stalemate
     * changes the boolean gameover variable and also returns its value
     */
    private boolean checkGameover() {
        //Check if there are no valid moves
        gameover = true;
        for (Tile[] column : board) {
            for (Tile t : column) {
                if (t.getPiece() != null && t.getPiece().getColor() == turn) {
                    selected = t;
                    if (offerMoves().size() != 0) {
                        gameover = false;
                    }
                }
            }
            selected = null;
        }       

        //If there are no valid moves, then it's either checkmate or stalemate (depending on whether or not the king is in check)
        if (gameover) {
            //Checkmate
            if (findChecks().size() > 0) {
                String checkmate_code = moveHistory.get(moveHistory.size()-1);
                checkmate_code = checkmate_code.substring(0, checkmate_code.length() - 1) + "#";
                moveHistory.set(moveHistory.size()-1, checkmate_code);
                changeTurn();
                title.setText(turn + " WINS (Checkmate)");
            }
            //Stalemate 
            else
                title.setText("Stalemate");
            return gameover;
        } 

        /*If there are still valid moves, check for stalemate by lack of material 
        (the remaining pieces cannot result in a checkmate)*/
        int material_count = 0;
        boolean flag = false;
        gameover = true;
        for (int i = 0; i < 2; i++) {
            for (Tile[] column : board) {
                for (Tile t : column) {
                    if (t.getPiece() != null && t.getPiece().getColor() == turn) {
                        switch (t.getPiece().getTag().code) {
                            case "": 
                                material_count += 5;
                                break;
                            case "N": 
                                material_count += 2;
                                break;
                            case "B": 
                                material_count += 3;
                                break;
                            case "R": 
                                material_count += 5;
                                break;
                            case "Q": 
                                material_count += 5;
                        }
                        //This is the scenario where the game is not over
                        if (material_count >= 5) {
                            if (flag)
                                changeTurn();
                            gameover = false;
                            return gameover;
                        }
                    }
                }
            }      
            flag = true;
            changeTurn();
            material_count = 0;
        }
        //Stalemate
        title.setText("Stalemate");
        return gameover;
    }

    /**
     * checkEnPassant
     * @return ArrayList<Integer> a
     * Checks if an "en passant" can be done (you can look this up in case you don't know)
     * (returns the coordinate of where en passant can be made)
     */
    private ArrayList<Integer> checkEnPassant() {
        //Filtering the most previous move to only represent movement (no check indicators)
        ArrayList<Integer> a = new ArrayList<Integer>();
        if (moveHistory.size() == 0)
            return new ArrayList<Integer>();
        String previous = moveHistory.get(moveHistory.size() - 1);
        if (previous.charAt(previous.length()-1) == '+') 
            previous = previous.substring(0, previous.length()-1);
        
        //Check that the move was a pawn move two spaces forward
        int from_row = Character.getNumericValue(previous.charAt(1));
        int to_row = Character.getNumericValue(previous.charAt(previous.length() - 1));
        if (selected.getPiece().getTag() != Piece.TAG.PAWN || 
            previous.charAt(0) == Character.toUpperCase(previous.charAt(0)) || 
            to_row - 1 != selected.getRow() || (from_row - to_row) % 2 != 0) 
            {return new ArrayList<Integer>();}
        
        //Check that the pawn move was right beside the selected pawn and that it's on the same row
        int column = column_convert.indexOf(previous.charAt(0));
        if (column == selected.getCol() - 1) {
            a.add(-1);
            a.add((turn.color_num*-2) + 1);
            return a;
        } else if (column == selected.getCol() + 1) {
            a.add(1);
            a.add((turn.color_num*-2) + 1);
            return a;
        }
        return new ArrayList<Integer>();
    }

    /**
     * checkCastles
     * @return ArrayList<ArrayList<Integer>> castles
     * Checks if a "castle" can be done (you can look this up in case you don't know)
     * (returns the coordinate of where the castle can be done)
     */
    private ArrayList<ArrayList<Integer>> checkCastles() {
        ArrayList<ArrayList<Integer>> castles = new ArrayList<ArrayList<Integer>>();
        boolean short_castle = true;
        boolean long_castle = true;
        
        //Check to make sure the selected piece is the king and it has not moved
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

        //Make sure there are no pieces in the way
        for (int i = 1; i < 4; i++)
            if (board[i][selected.getRow()].getPiece() != null)
                long_castle = false;
        for (int i = 5; i < 7; i++)
            if (board[i][selected.getRow()].getPiece() != null)
                short_castle = false;
        if (!short_castle && !long_castle)
            return new ArrayList<ArrayList<Integer>>();

        //Make sure that the king does not castle through checks or while in check
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

        //Check if each castle is possible and return all possible castles
        if (short_castle) {
            ArrayList<Integer> a = new ArrayList<Integer>();
            a.add(2);
            a.add(0);
            castles.add(a);
        }
        if (long_castle) {
            ArrayList<Integer> a = new ArrayList<Integer>();
            a.add(-2);
            a.add(0);
            castles.add(a);
        }
        return castles;
    }

    /**
     * findChecks
     * @return ArrayList<ArrayList<Integer>> checks
     * Looks for checks on the current player's pieces by looking at the tiles which the opponent's pieces are attacking
     * (returns coordinates of all checks) 
     * (if the position was customized to have more than one king, there could be more than one check)
     */
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

    /**
     * move
     * @param t
     * Moves the selected piece to the specified tile and records the move in moveHistory
     */
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

        moveHistory.add(moveCode);
    }

    /**
     * undo
     * Undoes the move previous move
     */
    public void undo() {
        //Filtering the most previous move to only represent movement (no check indicators)
        String previous = moveHistory.remove(moveHistory.size()-1);
        if (previous.charAt(previous.length()-1) == '+')
            previous = previous.substring(0, previous.length()-1);

        //Special case: castling
        if (previous.equals("O-O-O")) {
            changeTurn();
            board[2][(8 - turn.color_num)%8].setPiece(null);
            board[3][(8 - turn.color_num)%8].setPiece(null);
            addPiece(new King(turn), 4, (8 - turn.color_num)%8);
            addPiece(new Rook(turn), 0, (8 - turn.color_num)%8);
            changeTurn();
            return;
        }
        else if (previous.equals("O-O")) {
            changeTurn();
            board[5][(8 - turn.color_num)%8].setPiece(null);
            board[6][(8 - turn.color_num)%8].setPiece(null);
            addPiece(new King(turn), 4, (8 - turn.color_num)%8);
            addPiece(new Rook(turn), 7, (8 - turn.color_num)%8);
            changeTurn();
            return;
        }
        
        //Special case: Promotion
        else if (previous.charAt(previous.length()-2) == '=') {
            previous = previous.substring(0, previous.length()-2);
            int col = column_convert.indexOf(previous.charAt(previous.length()-2));
            int row = Character.getNumericValue(previous.charAt(previous.length()-1))-1;
            changeTurn();
            board[col][row].setPiece(new Pawn(turn));
            changeTurn();
        }

        //Special case: En Passant
        else if (previous.length() == 5 && previous.charAt(1) == previous.charAt(4)) {
            int col = column_convert.indexOf(previous.charAt(3));
            int row = Character.getNumericValue(previous.charAt(1))-1;
            board[col][row - (turn.color_num * -2) - 1].setPiece(null);

            addPiece(new Pawn(turn), col, row);
            changeTurn();
            col = column_convert.indexOf(previous.charAt(0));
            addPiece(new Pawn(turn), col, row);
            changeTurn();
            return;
        }

        //If there's no special case, filter the beginning of the move code (piece type indicators)
        if (previous.charAt(0) == Character.toUpperCase(previous.charAt(0)))
            previous = previous.substring(1);

        //The tile that the piece moved to
        int col = column_convert.indexOf(previous.charAt(previous.length()-2));
        int row = Character.getNumericValue(previous.charAt(previous.length()-1))-1;
        Tile t = board[col][row];

        //The tile it moved from
        col = column_convert.indexOf(previous.charAt(0));
        row = Character.getNumericValue(previous.charAt(1)-1);
        addPiece(t.getPiece(), col, row); //Put the piece back to where it was
        selected = board[col][row];

        //Put any piece back if the move was a capture
        t.setPiece(null);
        t.setIcon(null);
        if (previous.charAt(2) == 'x') {
            if (previous.length() == 5)
                addPiece(new Pawn(turn), t.getCol(), t.getRow());
            else {
                char type = previous.charAt(3);
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

    /**
     * changeTurn
     * Changes the turn from white to black / black to white
     */
    private void changeTurn() {
        if (turn == Piece.COLOR.WHITE)
            turn = Piece.COLOR.BLACK;
        else
            turn = Piece.COLOR.WHITE;
    }

    /**
     * updateMoveHistory
     * updates the move history display (JTextArea)
     */
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

    /**
     * draw
     * Gets rid of all "move offers" and highlights the move recent move, selected piece, and all checks
     * (also draws the board properly from the current player's pov)
     */
    public void draw() {
        for (Tile[] column : board) {
            for (Tile t : column) {
                //Erase "move offers"
                if (t.getPiece() != null) 
                    t.setIcon(t.getPiece().getIcon());
                else
                    t.setIcon(null);

                //Reseting highlights
                if ((t.getCol() + t.getRow())%2 == 1)
                    t.setBackground(Color.decode("#eaecd0"));
                else
                    t.setBackground(Color.decode("#779556"));

                //Orienting the board properly for the current player
                if (turn == Piece.COLOR.WHITE) 
                    t.setBounds(t.getCol()*64, (7-t.getRow())*64, 64, 64);
                else
                    t.setBounds((7-t.getCol())*64, t.getRow()*64, 64, 64);
            }
        }

        //Highlight selected piece in yellow
        if (selected != null)
            selected.setBackground(Color.yellow);
        
        //Highlight checks in red
        for (ArrayList<Integer> a : findChecks())
            board[a.get(0)][a.get(1)].setBackground(Color.red);

        //Highlight most recent move in yellow
        if (moveHistory.size() == 0)
            return;
        String previous = moveHistory.get(moveHistory.size()-1);
        //Filtering the most previous move to only represent movement (promotions & checks/checkmates)
        if (previous.charAt(previous.length()-1) == '+' || previous.charAt(previous.length()-1) == '#')
            previous = previous.substring(0, previous.length()-1);
        if (previous.charAt(previous.length()-2) == '=')
            previous = previous.substring(0, previous.length()-2);
        
        //Special case: castling
        if (previous.equals("O-O-O")) {
            changeTurn();
            board[2][(8 - turn.color_num)%8].setBackground(Color.yellow);
            board[3][(8 - turn.color_num)%8].setBackground(Color.yellow);
            changeTurn();
            return;
        }
        else if (previous.equals("O-O")) {
            changeTurn();
            board[5][(8 - turn.color_num)%8].setBackground(Color.yellow);
            board[6][(8 - turn.color_num)%8].setBackground(Color.yellow);
            changeTurn();
            return;
        }

        //Special case: En Passant
        if (previous.length() == 5 && previous.charAt(1) == previous.charAt(4)) {
            int col = column_convert.indexOf(previous.charAt(3));
            int row = Character.getNumericValue(previous.charAt(1))-1;
            board[col][row - (turn.color_num * -2) - 1].setBackground(Color.yellow);

            col = column_convert.indexOf(previous.charAt(0));
            board[col][row].setBackground(Color.yellow);
            return;
        }

        //If there's no special case, filter the beginning of the move code (piece type indicators)
        if (previous.charAt(0) == Character.toUpperCase(previous.charAt(0)))
            previous = previous.substring(1);
        
        //Highlight the tile that the piece moved to
        int col = column_convert.indexOf(previous.charAt(0));
        int row = Character.getNumericValue(previous.charAt(1)) - 1;
        board[col][row].setBackground(Color.yellow);
        
        //Highlight the tile that the piece moved from
        col = column_convert.indexOf(previous.charAt(previous.length()-2));
        row = Character.getNumericValue(previous.charAt(previous.length()-1)) - 1;
        board[col][row].setBackground(Color.yellow);
    }

}