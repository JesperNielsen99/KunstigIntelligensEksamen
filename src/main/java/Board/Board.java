package Board;

import Pieces.*;

import java.util.ArrayList;

public class Board {

    ArrayList<ArrayList<Piece>> board = new ArrayList<>();

    public Board() {
    }

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
            board.get(1).set(i, new Pawn(true, 1, i)); // Black Pawns
            board.get(6).set(i, new Pawn(false, 6, i)); // White Pawns
        }


        // Setup Rooks
        board.get(0).set(0, new Rook(true, 0, 0)); // Black Rook
        board.get(0).set(7, new Rook(true, 0, 7)); // Black Rook
        board.get(7).set(0, new Rook(false, 7, 0)); // White Rook
        board.get(7).set(7, new Rook(false, 7, 7)); // White Rook

        // Setup Queen
        board.get(0).set(3, new Queen(true, 0, 3)); //Black
        board.get(7).set(3, new Queen(false, 7, 3)); //White

        //Setup Bishop
        board.get(0).set(2, new Bishop(true, 0, 2)); //Black
        board.get(0).set(5, new Bishop(true, 0, 5)); //Black
        board.get(7).set(2, new Bishop(false, 7, 2)); //White
        board.get(7).set(5, new Bishop(false, 7, 5)); //White

        //Setup Knight (N)
        board.get(0).set(1, new Knight(true, 0, 1)); //Black
        board.get(0).set(6, new Knight(true, 0, 6)); //Black
        board.get(7).set(1, new Knight(false, 7, 1)); //White
        board.get(7).set(6, new Knight(false, 7, 6)); //White

        //Setup King
        board.get(0).set(4, new King(true, 0, 4)); //Black
        board.get(7).set(4, new King(false, 7, 4)); //White
    }

    public ArrayList<ArrayList<Piece>> getBoard() {
        return board;
    }

    public String toString() {
        String boardString = "------------------\n";
        for (int i = 0; i < 8; i++) {
            boardString += 8-i + "|";
            for (int j = 0; j < 8; j++) {
                if (board.get(8-i-1).get(j) == null) {
                    boardString += " |";
                } else {
                    boardString += board.get(8-i-1).get(j).toString() + "|";
                }
            }
            boardString += "\n------------------\n";
        }
        boardString += "  A B C D E F G H";
        return boardString;
    }
}
