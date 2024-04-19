import Board.Board;
import Mechanics.Move;

public class Game {
    static Move move;
    static Board board;

    public static void main(String[] args) {
        board = new Board();
        move = new Move();
        board.initializeBoard();
        run();
    }

    public static void run() {
        while (true) {
            move.movePiece(board, true);
            //AI do black stuff
            //AI.move(board, false);
            move.movePiece(board, false);
        }
    }
}
