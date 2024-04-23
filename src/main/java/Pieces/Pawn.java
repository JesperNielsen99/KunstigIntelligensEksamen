package Pieces;

import java.util.ArrayList;

public class Pawn extends Piece {
    private static final int pawnValue = 100;
    private static final int[][] pawnBoardHeuristic = {
            {  0,  0,  0,  0,  0,  0,  0,  0 },
            { 50, 50, 50, 50, 50, 50, 50, 50 },
            { 10, 10, 20, 30, 30, 20, 10, 10 },
            {  5,  5, 10, 25, 25, 10,  5,  5 },
            {  0,  0,  0, 20, 20,  0,  0,  0 },
            {  5, -5,-10,  0,  0,-10, -5,  5 },
            {  5, 10, 10,-20,-20, 10, 10,  5 },
            {  0,  0,  0,  0,  0,  0,  0,  0 }
    };


    public Pawn(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        isFirstMove = true;
        if (isWhite) {
            super.directions.add(new ArrayList<>() {{ add(1); add(0); }});
            super.directions.add(new ArrayList<>() {{ add(1); add(1); }});
            super.directions.add(new ArrayList<>() {{ add(1); add(-1); }});
        } else {
            super.directions.add(new ArrayList<>() {{ add(-1); add(0); }});
            super.directions.add(new ArrayList<>() {{ add(-1); add(1); }});
            super.directions.add(new ArrayList<>() {{ add(-1); add(-1); }});
        }
    }

    @Override
    public int getHeuristicValue(){
        int boardHeuristicValue;

        if (isWhite) {
            boardHeuristicValue = pawnBoardHeuristic[currentXPosition][currentYPosition];
        } else {
            boardHeuristicValue = pawnBoardHeuristic[7 - currentXPosition][currentYPosition];
        }

        return pawnValue + boardHeuristicValue;
    }

    @Override
    public int[][] getBoardHeuristic() {
        return pawnBoardHeuristic;
    }

    public String toString() {
        if (isWhite) {
            return "P";
        }
        return "p";
    }

}

