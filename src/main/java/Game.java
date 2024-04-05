import Board.Board;
import Mechanics.Move;
import Pieces.Piece;

public class Game {

    public static void main(String[] args) {
        Board board = new Board();
        Move move = new Move();
        board.initializeBoard();
        Piece testPiece = board.getBoard().get(0).get(2);
        System.out.println(board.getBoard().get(0).get(2));
        System.out.println(board.getBoard().get(0).get(2).canBeBlockedStraight);
        System.out.println(board.getBoard().get(0).get(2).canBeBlockedCross);
        //System.out.println(move.getLegalMoves(board, board.getBoard().get(0).get(1)));
        System.out.println(board);
    }

    public void run() {

    }
}
