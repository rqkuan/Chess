public class Main {

    public static void main(String[] args){
        Display display = new Display();
        Board board = new Board();
        display.draw("white");
        board.move('e', 2, 'e', 4);
        display.draw("black");
        board.move('e', 7, 'e', 5);
        display.draw("white");
        board.move('e', 1, 'e', 2);
        display.draw("black");
        board.move('e', 8, 'e', 7);
        display.draw("white");
        
    }
}