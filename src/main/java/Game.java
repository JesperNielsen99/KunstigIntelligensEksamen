import Board.Board;
import Mechanics.Move;
import Pieces.Piece;
import AI.AI;

public class Game {
    static Move move;
    static Board board;
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
            if (!ai.isWhite) {
                move.takeTurn(board, board.getPlayer());
                ai.aiMove(board);
            } else {
                ai.aiMove(board);
                move.takeTurn(board, board.getPlayer());
            }

            Piece.printHeuristicValues(board);
        }
    }



}
