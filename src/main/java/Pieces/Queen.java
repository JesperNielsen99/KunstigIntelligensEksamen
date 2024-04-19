package Pieces;

import java.util.ArrayList;

public class Queen extends Piece{

    private static final int queenValue = 900;
    private static final int[][] queenBoardHeuristic = {
            {-20, -10, -10,  -5,  -5, -10, -10, -20},
            {-10,   0,   0,   0,   0,   0,   0, -10},
            {-10,   0,   5,   5,   5,   5,   0, -10},
            { -5,   0,   5,   5,   5,   5,   0,  -5},
            {  0,   0,   5,   5,   5,   5,   0,  -5},
            {-10,   5,   5,   5,   5,   5,   0, -10},
            {-10,   0,   5,   0,   0,   0,   0, -10},
            {-20, -10, -10,  -5,  -5, -10, -10, -20}
    };

    public Queen(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        super.directions.add(new ArrayList<>() {{ add(1); add(1); }});
        super.directions.add(new ArrayList<>() {{ add(1); add(-1); }});
        super.directions.add(new ArrayList<>() {{ add(-1); add(1); }});
        super.directions.add(new ArrayList<>() {{ add(-1); add(-1); }});
        super.directions.add(new ArrayList<>() {{ add(0); add(1); }});
        super.directions.add(new ArrayList<>() {{ add(0); add(-1); }});
        super.directions.add(new ArrayList<>() {{ add(1); add(0); }});
        super.directions.add(new ArrayList<>() {{ add(-1); add(0); }});
    }
    @Override
    public int getHeuristicValue(){
        int boardHeuristicValue;

        if (isWhite) {
            boardHeuristicValue = queenBoardHeuristic[currentXPosition][currentYPosition];
        } else {
            boardHeuristicValue = queenBoardHeuristic[7 - currentXPosition][currentYPosition];
        }

        return queenValue + boardHeuristicValue;
    }


    public String toString() {
        if (isWhite) {
            return "Q";
        }
        return "q";
    }
}
