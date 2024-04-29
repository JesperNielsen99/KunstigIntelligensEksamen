import Board.Board;
import Mechanics.Move;
import Pieces.Piece;
import AI.AI;

public class Game {
    static Move move;
    static Board board;
    static Piece piece;
    static AI ai;

    public static void main(String[] args) {
        board = new Board();
        move = new Move();
        ai = new AI(false);
        board.initializeBoard();
        run();
    }

    public static void run() {
        while (true) {
            move.takeTurn(board, board.getPlayer());
            ai.aiMove(board);

            Piece.printHeuristicValues(board);
        }
    }



}
