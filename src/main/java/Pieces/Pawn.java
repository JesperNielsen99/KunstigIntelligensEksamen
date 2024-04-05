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
    public ArrayList<ArrayList<Integer>> getLegalMoves(Piece[] board) {
        ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<>();
        return legalMoves;
    }

    @Override
    public void removeIllegalMoves(ArrayList<Integer> moves) {

    }

    @Override
    public void removeOccupied() {

    }

    @Override
    public boolean isOccupied(int field) {
        return false;
    }

    public String toString() {
        return "P";
    }

}

