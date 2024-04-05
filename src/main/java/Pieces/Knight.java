package Pieces;

import java.util.ArrayList;

public class Knight extends Piece{

    public Knight(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
    }

    @Override
    public ArrayList<ArrayList<Integer>> getDefaultMoves() {
        int[][] moves = {
                {-2, -1}, {-2, 1}, // Upwards L-moves
                {-1, -2}, {1, -2}, // Leftwards L-moves
                {2, -1}, {2, 1},   // Downwards L-moves
                {-1, 2}, {1, 2}    // Rightwards L-moves
        };

        ArrayList<ArrayList<Integer>> newMoveArray = new ArrayList<>();

        for (int[] move : moves) {
            int newX = currentXPosition + move[0];
            int newY = currentYPosition + move[1];

            // Check if the new position is on the board
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                ArrayList<Integer> newMove = new ArrayList<>();
                newMove.add(newX);
                newMove.add(newY);
                newMoveArray.add(newMove);
            }
        }
        int i = 0;
        if(i < newMoveArray.size()) {
            System.out.println(newMoveArray.get(i));
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
        return "H";
    }
}
