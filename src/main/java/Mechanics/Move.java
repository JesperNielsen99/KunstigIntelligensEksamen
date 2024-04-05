package Mechanics;

import Board.Board;
import Pieces.Piece;

import java.util.ArrayList;

public class Move {

    public Move() {
    }

    public boolean isOccupied(Board board, ArrayList<Integer> move) {
        int x = move.get(0);
        int y = move.get(1);
        return board.getBoard().get(x).get(y) != null;
    }

    public boolean isOccupiedByYou(Board board, ArrayList<Integer> move, boolean isWhite) {
        int x = move.get(0);
        int y = move.get(1);
        return board.getBoard().get(x).get(y).isWhite == isWhite;
    }

    public ArrayList<ArrayList<Integer>> removeIllegalMoves(Board board, Piece piece) {
        ArrayList<ArrayList<Integer>> moves = piece.getDefaultMoves();
        ArrayList<ArrayList<Integer>> illegalMoves = new ArrayList<>();
        for (ArrayList<Integer> move : moves) {
            if (!isOutOfBounds(move) && isOccupied(board, move)) {
                if (!isOccupiedByYou(board, move, piece.isWhite)) {
                    illegalMoves.add(move);
                }
            }
        }
        return illegalMoves;
    };

    public boolean isOutOfBounds(ArrayList<Integer> move) {
        int x = move.get(0);
        int y = move.get(1);
        return x < 0 || x > 7 || y < 0 || y > 7;
    }

    public ArrayList<ArrayList<Integer>> getLegalMoves(Board board, Piece piece) {
        ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<>();
        piece.getLegalMoves(removeIllegalMoves(board, piece));
        return legalMoves;
    }
}
