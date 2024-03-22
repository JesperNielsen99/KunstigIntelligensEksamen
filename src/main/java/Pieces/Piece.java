package Pieces;

public abstract class Piece {
    public boolean isWhite;
    public int currentPosition;
    int[] moves = getDefaultMoves();


    public abstract int[] getDefaultMoves();
    public abstract int[] getLegalMoves(Piece[] board);
    public abstract int[] removeIllegalMoves(int[] moves);
    public abstract void removeOccupied();
    public abstract boolean isOccupied(int field);
}
