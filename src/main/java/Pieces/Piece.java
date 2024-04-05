package Pieces;

import java.util.ArrayList;

public abstract class Piece {
    public boolean isWhite;
    public boolean canBeBlockedStraight;
    public boolean canBeBlockedCross;
    public int currentXPosition;
    public int currentYPosition;


    public Piece(boolean isWhite, int currentXPosition, int currentYPosition) {
        this.isWhite = isWhite;
        this.currentXPosition = currentXPosition;
        this.currentYPosition = currentYPosition;
        canBeBlockedStraight = false;
        canBeBlockedCross = false;
    }


    public abstract ArrayList<ArrayList<Integer>> getDefaultMoves();
    public abstract ArrayList<ArrayList<Integer>> getMoves();
    public ArrayList<ArrayList<Integer>> getLegalMoves(ArrayList<ArrayList<Integer>> illegalMoves) {
        return removeIllegalMoves(getDefaultMoves());
    };
    public ArrayList<ArrayList<Integer>> removeIllegalMoves(ArrayList<ArrayList<Integer>> moves) {
        return null;
    };
}
