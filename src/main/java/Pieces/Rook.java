package Pieces;

import java.util.ArrayList;



public class Rook extends Piece {

    private static final int rookValue = 500;
    private static final int[][] rookBoardHeuristic = {
            { 0,  0,  0,  0,  0,  0,  0,  0},
            { 5, 10, 10, 10, 10, 10, 10,  5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            {-5,  0,  0,  0,  0,  0,  0, -5},
            { 0,  0,  0,  5,  5,  0,  0,  0}
    };

    public Rook(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        isFirstMove = true;
        super.directions.add(new ArrayList<>() {{ add(0); add(1); }});
        super.directions.add(new ArrayList<>() {{ add(0); add(-1); }});
        super.directions.add(new ArrayList<>() {{ add(1); add(0); }});
        super.directions.add(new ArrayList<>() {{ add(-1); add(0); }});
    }


    @Override
    public int getHeuristicValue(){
        int boardHeuristicValue;

        if (isWhite) {
            boardHeuristicValue = rookBoardHeuristic[currentXPosition][currentYPosition];
        } else {
            boardHeuristicValue = rookBoardHeuristic[7 - currentXPosition][currentYPosition];
        }

        return rookValue + boardHeuristicValue;
    }

    @Override
    public int[][] getBoardHeuristic() {
        return rookBoardHeuristic;
    }

    public String toString() {
        if (isWhite) {
            return "R";
        }
        return "r";
    }
}
