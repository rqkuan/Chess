public class King extends Piece {

    public King(String color, char column, int row, String tag) {
        super(color, column, row, tag);
    }

    public int[][] getMovePattern() {
        //WIP
        if (this.color.equals("white")) 
            return new int[][] {{1, 0}};
        return new int[][] {{-1, 0}};
    }

    public int[][] getAttackingSquares() {
        //WIP
        if (this.color.equals("white"))
            return new int[][] {{1, 1}, {1, -1}};
        return new int[][] {{-1, 1}, {-1, -1}};            
    }

}