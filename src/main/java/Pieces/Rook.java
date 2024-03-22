package Pieces;

public class Rook extends Piece {

    Rook() {
        super();
    }

    @Override
    public int[] getDefaultMoves() {
        return new int[]{1, -1, 1, -1};
    }

    @Override
    public int[] getLegalMoves(Piece[] board) {
        int[] legalMoves = new int[]{};

        return new int[0];
    }

    @Override
    public int[] removeIllegalMoves(int[] moves) {
        return new int[0];
    }

    @Override
    public int[] removeOccupied() {
        return new int[0];
    }

    @Override
    public boolean isOccupied(int field) {
        return false;
    }
}
