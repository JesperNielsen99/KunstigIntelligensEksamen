package Pieces;

import java.util.ArrayList;

public class Bishop extends Piece{

    private static final int bishopValue = 330;
    private static final int[][] BishopBoardHeuristic = {
            { -20, -10, -10, -10, -10, -10, -10, -20 },
            { -10,   5,   0,   0,   0,   0,   5, -10 },
            { -10,  10,  10,  10,  10,  10,  10, -10 },
            { -10,   0,  10,  20,  20,  10,   0, -10 },
            { -10,   5,  15,  20,  20,  15,   5, -10 },
            { -10,   0,  15,  20,  20,  15,   0, -10 },
            { -10,  10,   5,   0,   0,   5,  10, -10 },
            { -20, -10, -10, -10, -10, -10, -10, -20 }
    };

    public Bishop(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        super.directions.add(new ArrayList<>() {{ add(1); add(1); }});
        super.directions.add(new ArrayList<>() {{ add(-1); add(1); }});
        super.directions.add(new ArrayList<>() {{ add(1); add(-1); }});
        super.directions.add(new ArrayList<>() {{ add(-1); add(-1); }});
    }

    @Override
    public int getHeuristicValue(){
        int boardHeuristicValue;

        if (isWhite) {
            boardHeuristicValue = BishopBoardHeuristic[currentXPosition][currentYPosition];
        } else {
             boardHeuristicValue = BishopBoardHeuristic[7 - currentXPosition][currentYPosition];
        }

        return bishopValue + boardHeuristicValue;
        }

    public String toString() {
        if (isWhite) {
            return "B";
        }
        return "b";
    }
}
