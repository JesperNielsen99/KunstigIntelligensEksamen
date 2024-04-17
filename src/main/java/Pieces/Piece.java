package Pieces;

import java.util.ArrayList;

public abstract class Piece {
    public boolean isWhite;
    public boolean isFirstMove = false;
    public int currentXPosition;
    public int currentYPosition;
    public ArrayList<ArrayList<Integer>> directions;


    public Piece(boolean isWhite, int currentXPosition, int currentYPosition) {
        this.isWhite = isWhite;
        this.currentXPosition = currentXPosition;
        this.currentYPosition = currentYPosition;
        directions = new ArrayList<>();
    }
}
