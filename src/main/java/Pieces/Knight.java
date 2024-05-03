package Pieces;

import java.util.ArrayList;

public class Knight extends Piece {

    private static final int knightValue = 320;
    private static final int[][] knightBoardHeuristic = {
            {-50, -40, -30, -30, -30, -30, -40, -50},
            {-40, -20,   0,   0,   0,   0, -20, -40},
            {-30,   0,  10,  15,  15,  10,   0, -30},
            {-30,   5,  15,  20,  20,  15,   5, -30},
            {-30,   0,  15,  20,  20,  15,   0, -30},
            {-30,   5,  10,  15,  15,  10,   5, -30},
            {-40, -20,   0,   5,   5,   0, -20, -40},
            {-50, -40, -30, -30, -30, -30, -40, -50}
    };

    public Knight(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        super.directions.add(new ArrayList<>() {{ add(-2); add(-1); }});
        super.directions.add(new ArrayList<>() {{ add(-2); add(1); }});
        super.directions.add(new ArrayList<>() {{ add(-1); add(-2); }});
        super.directions.add(new ArrayList<>() {{ add(1); add(-2); }});
        super.directions.add(new ArrayList<>() {{ add(2); add(-1); }});
        super.directions.add(new ArrayList<>() {{ add(2); add(1); }});
        super.directions.add(new ArrayList<>() {{ add(-1); add(2); }});
        super.directions.add(new ArrayList<>() {{ add(1); add(2); }});
    }

    @Override
    public int getHeuristicValue(){
        int boardHeuristicValue;

        if (isWhite) {
            boardHeuristicValue = knightBoardHeuristic[currentXPosition][currentYPosition];
        } else {
            boardHeuristicValue = knightBoardHeuristic[7 - currentXPosition][currentYPosition];
        }

        return knightValue + boardHeuristicValue;
    }

    @Override
    public int[][] getBoardHeuristic() {
        return knightBoardHeuristic;
    }

    public String toString() {
        if (isWhite) {
            return "H";
        }
        return "h";
    }
}
