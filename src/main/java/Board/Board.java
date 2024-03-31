package Board;

import Pieces.*;

import java.util.ArrayList;

public class Board {
    ArrayList<ArrayList<Piece>> board = new ArrayList<>();

    public Board() {
    }

//    public void initializeBoard() {
//        for (int i = 0; i< 8; i++) {
//            board.add(new ArrayList<>());
//            for (int j = 0; j < 8; j++) {
//                if (j%2==0 || j%3==0) {
//                    board.get(i).add(null);
//                } else {
//                    board.get(i).add(new Rook());
//                }
//            }
//        }
//    }

    public void initializeBoard() {

        for (int i = 0; i < 8; i++) {
            ArrayList<Piece> row = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                row.add(null);
            }
            board.add(row);
        }

        // Setup Pawns
        for (int i = 0; i < 8; i++) {
            // Assuming black is at the top (0) and white at the bottom (7)
            // Adjust if your board orientation is different
            board.get(1).set(i, new Pawn(i, 1)); // Black Pawns
            board.get(6).set(i, new Pawn(i, 6)); // White Pawns
        }

        // Setup Rooks
        board.get(0).set(0, new Rook(0, 0)); // Black Rook
        board.get(0).set(7, new Rook(7, 0)); // Black Rook
        board.get(7).set(0, new Rook(0, 7)); // White Rook
        board.get(7).set(7, new Rook(7, 7)); // White Rook

        // Setup Queen
        board.get(0).set(3, new Queen(0, 3)); //Black
        board.get(7).set(3, new Queen(7, 3)); //White

        //Setup Bishop
        board.get(0).set(2, new Bishop(0, 2)); //Black
        board.get(0).set(5, new Bishop(0, 5)); //Black
        board.get(7).set(2, new Bishop(7, 2)); //White
        board.get(7).set(5, new Bishop(7, 5)); //White

        //Setup Knight (N)
        board.get(0).set(1, new Knight(0, 1)); //Black
        board.get(0).set(6, new Knight(0, 6)); //Black
        board.get(7).set(1, new Knight(7, 1)); //White
        board.get(7).set(6, new Knight(7, 6)); //White

        //Setup King
        board.get(0).set(4, new King(0, 4)); //Black
        board.get(7).set(4, new King(7, 4)); //White

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
