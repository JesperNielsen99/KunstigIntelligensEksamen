package Pieces;

import java.util.ArrayList;

public class Pawn extends Piece {
    private boolean isFirstMove = true;

    public Pawn(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
    }

    @Override
    public ArrayList<ArrayList<Integer>> getDefaultMoves() {
        ArrayList<ArrayList<Integer>> newMoveArray = new ArrayList<>();

        int direction = isWhite ? -1 : 1; // White moves up (decrease y), Black moves down (increase y)

        ArrayList<Integer> move = new ArrayList<>();
        move.add(currentXPosition);
        move.add(currentYPosition + direction);
        newMoveArray.add(move);


        if (isFirstMove) {
            ArrayList<Integer> firstMove = new ArrayList<>();
            firstMove.add(currentXPosition);
            firstMove.add(currentYPosition + (2 * direction));
            newMoveArray.add(firstMove);
        }

        // Capturing moves: Diagonally forward
        ArrayList<Integer> captureLeft = new ArrayList<>();
        captureLeft.add(currentXPosition - 1);
        captureLeft.add(currentYPosition + direction);
        newMoveArray.add(captureLeft);

        ArrayList<Integer> captureRight = new ArrayList<>();
        captureRight.add(currentXPosition + 1);
        captureRight.add(currentYPosition + direction);
        newMoveArray.add(captureRight);

        return newMoveArray;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getMoves() {
        return null;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getLegalMoves(ArrayList<ArrayList<Integer>> moves) {
        ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<>();
        return legalMoves;
    }

    public String toString() {
        if (isWhite) {
            return "wP";
        }
        return "bP";
    }

}

