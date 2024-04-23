import Board.Board;
import Mechanics.Move;
import Pieces.Bishop;
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
//            Piece bishop = new Bishop(true, 2, 3);
//            Piece.printPieceBoardHeuristicValues(bishop);
//            Piece.printHeuristicValues(board);



            move.movePiece1(board, true);
            //AI do black stuff
            //AI.move(board, false);

            Piece.printHeuristicValues(board);
            move.movePiece1(board, false);
        }
    }



}
