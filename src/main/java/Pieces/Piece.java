package Pieces;

import java.util.ArrayList;

public abstract class Piece {
    public boolean isWhite;
    public int currentXPosition;
    public int currentYPosition;

    ArrayList<ArrayList<Integer>> moves = getMoves();

    public Piece(int piecePosition) {
    }
    public Piece(boolean isWhite, int currentXPosition, int currentYPosition) {
        this.isWhite = isWhite;
        this.currentXPosition = currentXPosition;
        this.currentYPosition = currentYPosition;
    }


    public abstract ArrayList<ArrayList<Integer>> getDefaultMoves();
    public abstract ArrayList<ArrayList<Integer>> getMoves();
    public ArrayList<ArrayList<Integer>> getLegalMoves(ArrayList<ArrayList<Integer>> moves) {
        return removeIllegalMoves(getDefaultMoves());
    };
    public ArrayList<ArrayList<Integer>> removeIllegalMoves(ArrayList<ArrayList<Integer>> moves) {
        return null;
    };
}
