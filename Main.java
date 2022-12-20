public class Main {

    public static void main(String[] args){
        Board board = new Board();
        board.draw("white");
        board.move('e', 2, 'e', 4);
        board.draw("black");
        board.move('e', 7, 'e', 5);
        board.draw("white");
        board.move('e', 1, 'e', 2);
        board.draw("black");
        board.move('e', 8, 'e', 7);
        board.draw("white");
    }
}