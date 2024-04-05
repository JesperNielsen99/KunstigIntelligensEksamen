package Pieces;

import java.util.ArrayList;



public class Rook extends Piece {


    public Rook(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        canBeBlockedStraight = true;
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
        }

        return newMoveArray;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getMoves() {
        return null;
    }

    @Override
    public ArrayList<ArrayList<Integer>> getLegalMoves(ArrayList<ArrayList<Integer>> illegalMoves) {
        ArrayList<ArrayList<Integer>> defaultMoves = getDefaultMoves();
        for (ArrayList<Integer> move : illegalMoves) {
            if (defaultMoves.contains(move)) {
                defaultMoves.remove(move);
            }
        }
        System.out.println(defaultMoves);
        return defaultMoves;
    }

    public String toString() {
        if (isWhite) {
            return "R";
        }
        return "r";
    }
}
