package Pieces;

import java.util.ArrayList;

public abstract class Piece {
    public boolean isWhite;
    public int currentXPosition;
    public int currentYPosition;

    ArrayList<ArrayList<Integer>> moves = getMoves();

    public Piece(int piecePosition) {
    }
    public Piece() {
    }


    public abstract ArrayList<ArrayList<Integer>> getDefaultMoves();
    public abstract ArrayList<ArrayList<Integer>> getMoves();
    public abstract ArrayList<ArrayList<Integer>> getLegalMoves(Piece[] board);
    public abstract void removeIllegalMoves(ArrayList<Integer> moves);
    public abstract void removeOccupied();
    public abstract boolean isOccupied(int field);
}
