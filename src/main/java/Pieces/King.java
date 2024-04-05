package Pieces;

import java.util.ArrayList;

public class King extends Piece{
    public King(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        currentXPosition = x;
        currentYPosition = y;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getDefaultMoves() {
        ArrayList<ArrayList<Integer>> newMoveArray = new ArrayList<>();

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                // Skip the (0,0) delta to avoid adding the King's current position
                if (dx == 0 && dy == 0) continue;

                int newX = currentXPosition + dx;
                int newY = currentYPosition + dy;

                // Check for board boundaries
                if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                    ArrayList<Integer> move = new ArrayList<>();
                    move.add(newX);
                    move.add(newY);
                    newMoveArray.add(move);
                }
            }
        }

        return newMoveArray;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getMoves() {
        return null;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getLegalMoves(ArrayList<ArrayList<Integer>> moves) {
        return null;
    }

    public String toString() {
        if (isWhite) {
            return "wK";
        }
        return "bK";
    }
}
