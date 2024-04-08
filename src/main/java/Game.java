import Board.Board;
import Mechanics.Move;

public class Game {
    static Move move;
    static Board board;

    public static void main(String[] args) {
        board = new Board();
        move = new Move();
        board.initializeBoard();
        //System.out.println(move.getLegalMoves(board, board.getBoard().get(0).get(4)));
        //System.out.println(board.getBoard().get(0).get(4));
        //System.out.println(board);
        run();
    }

    public static void run() {
        while (true) {
            move.movePiece(board, true);
            move.movePiece(board, false);

        }
    }
}
