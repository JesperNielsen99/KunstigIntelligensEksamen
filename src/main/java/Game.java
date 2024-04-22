import Board.Board;
import Mechanics.Move;
import Pieces.Piece;

public class Game {
    static Move move;
    static Board board;
    static Piece piece;

    public static void main(String[] args) {
        board = new Board();
        move = new Move();
        board.initializeBoard();
        run();
    }

    public static void run() {
        while (true) {
            move.movePiece1(board, true);
            //AI do black stuff
            //AI.move(board, false);
            move.movePiece(board, false);

            piece.printHeuristicValues(board);
            move.movePiece1(board, false);
        }
    }



}
