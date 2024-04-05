import Board.Board;
import Mechanics.Move;

public class Game {

    public static void main(String[] args) {
        Board board = new Board();
        Move move = new Move();
        board.initializeBoard();
        //System.out.println(move.isOccupiedByYou(board, 1, 1, true));
        System.out.println(move.getLegalMoves(board, board.getBoard().get(0).get(0)));
        //System.out.println(board);
    }

    public void run() {

    }
}
