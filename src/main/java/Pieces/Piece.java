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


    public abstract int[] getDefaultMoves();
    public abstract int[] getLegalMoves(Piece[] board);
    public abstract int[] removeIllegalMoves(int[] moves);
    public abstract void removeOccupied();
    public abstract boolean isOccupied(int field);
}
