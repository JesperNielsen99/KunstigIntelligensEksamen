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
                if (j%2==0 || j%3==0) {
                    board.get(i).add(null);
                } else {
                    board.get(i).add(new Rook());
                }
            }
        }
    }

    public String toString() {
        String boardString = " *********\n";
        for (int i = 0; i< 8; i++) {
            boardString += 8-i;
            for (int j = 0; j < 8; j++) {
                if (board.get(i).get(j) == null) {
                    boardString += " ";
                } else {
                    boardString += board.get(i).get(j).toString();
                }
            }
            boardString += "\n";
        }
        boardString += " ABCDEFGH";
        return boardString;
    }

}
