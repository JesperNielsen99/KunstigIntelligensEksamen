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
            if (!move.isGameOver(board)) {
                move.takeTurn(board, board.getPlayer());
                move.takeTurn(board, board.getPlayer());
                /*
                if (!ai.isWhite) {
                    move.takeTurn(board, board.getPlayer());
                    System.out.println(board);
                    ai.aiMove(board);
                } else {
                    ai.aiMove(board);
                    move.takeTurn(board, board.getPlayer());
                }
            } else {
                if (board.getPlayer()) {
                    System.out.println("Game Over! The AI is the Winner!");
                }
                System.out.println("Game Over! The Player is the Winner!");
                break;
                */
            }
        }
    }



}
