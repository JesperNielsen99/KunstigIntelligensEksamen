package Pieces;

import java.util.ArrayList;

public class Queen extends Piece{


    public Queen(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        canBeBlockedStraight = true;
        canBeBlockedCross = true;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getDefaultMoves() {
        ArrayList<ArrayList<Integer>> newMoveArray = new ArrayList<>();

        for (int i = 1; i < 8; i++) {
            if (i != currentXPosition) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(i);
                move.add(currentYPosition);
                newMoveArray.add(move);
            }
            if (i != currentYPosition) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(currentXPosition);
                move.add(i);
                newMoveArray.add(move);
            }
            if (currentXPosition + i < 8 && currentYPosition + i < 8) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(currentXPosition + i);
                move.add(currentYPosition + i);
                newMoveArray.add(move);
            }
            if (currentXPosition - i >= 0 && currentYPosition + i < 8) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(currentXPosition - i);
                move.add(currentYPosition + i);
                newMoveArray.add(move);
            }
            if (currentXPosition + i < 8 && currentYPosition - i >= 0) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(currentXPosition + i);
                move.add(currentYPosition - i);
                newMoveArray.add(move);
            }
            if (currentXPosition - i >= 0 && currentYPosition - i >= 0) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(currentXPosition - i);
                move.add(currentYPosition - i);
                newMoveArray.add(move);
            }
        }

        return newMoveArray;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getMoves() {
        return null;
    }
    @Override
    public ArrayList<ArrayList<Integer>> getLegalMoves(ArrayList<ArrayList<Integer>> illegalMoves) {
        return null;
    }

    public String toString() {
        if (isWhite) {
            return "Q";
        }
        return "q";
    }
}
