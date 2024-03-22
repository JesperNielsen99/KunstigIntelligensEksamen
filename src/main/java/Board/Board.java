package Board;

import Pieces.Piece;
import Pieces.Rook;

import java.util.ArrayList;

public class Board {
    ArrayList<ArrayList<Piece>> board = new ArrayList<>();

    public Board() {
    }

    public void initializeBoard() {
        for (int i = 0; i< 8; i++) {
            board.add(new ArrayList<>());
            for (int j = 0; j < 8; j++) {
                board.get(i).add(new Rook());
            }
        }
    }

    public String toString() {
        String boardString = "*********";
        for (int i = 0; i< 8; i++) {
            for (int j = 0; j < 8; j++) {
                boardString = board.get(i).get(j).toString();
            }
        }
        return boardString;
    }

}
