package Pieces;

import java.util.ArrayList;

public class Queen extends Piece{


    public Queen(int x, int y) {
        super();
        currentXPosition = x;
        currentYPosition = y;
    }

    public Queen(){

    }




    @Override
    public ArrayList<ArrayList<Integer>> getDefaultMoves() {
        ArrayList<ArrayList<Integer>> newMoveArray = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            if (i != currentXPosition) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(i);
                move.add(currentYPosition);
                newMoveArray.add(move);
            }
        }

        // Add all possible vertical moves
        for (int i = 0; i < 8; i++) {
            if (i != currentYPosition) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(currentXPosition);
                move.add(i);
                newMoveArray.add(move);
            }
        }
        // Diagonal moves
        // Up and to the right
        for (int i = 1; i < 8; i++) {
            if (currentXPosition + i < 8 && currentYPosition + i < 8) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(currentXPosition + i);
                move.add(currentYPosition + i);
                newMoveArray.add(move);
            }
        }

        // Up and to the left
        for (int i = 1; i < 8; i++) {
            if (currentXPosition - i >= 0 && currentYPosition + i < 8) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(currentXPosition - i);
                move.add(currentYPosition + i);
                newMoveArray.add(move);
            }
        }

        // Down and to the right
        for (int i = 1; i < 8; i++) {
            if (currentXPosition + i < 8 && currentYPosition - i >= 0) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(currentXPosition + i);
                move.add(currentYPosition - i);
                newMoveArray.add(move);
            }
        }

        // Down and to the left
        for (int i = 1; i < 8; i++) {
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
    public ArrayList<ArrayList<Integer>> getLegalMoves(Piece[] board) {
        return null;
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
        return "Q";
    }
}
