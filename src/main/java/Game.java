import Board.Board;
import Pieces.Rook;

import java.util.ArrayList;

public class Game {

    public static void main(String[] args) {
        Rook rook = new Rook(1, 2);
        ArrayList<ArrayList<Integer>> legalMoves = rook.getDefaultMoves();
        for (ArrayList<Integer> array: legalMoves) {
            System.out.println(array);
        }
        Board board = new Board();
        board.initializeBoard();
        System.out.println(board);
    }

    public void run() {

    }
}
