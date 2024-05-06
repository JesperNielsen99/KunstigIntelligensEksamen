package Pieces;

import java.util.ArrayList;

public class King extends Piece {

    private static final int kingValue = 20000;
    private static final int[][] KingBoardHeuristicMiddleGame = {
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-30, -40, -40, -50, -50, -40, -40, -30},
            {-20, -30, -30, -40, -40, -30, -30, -20},
            {-10, -20, -20, -20, -20, -20, -20, -10},
            { 20,  20,   0,   0,   0,   0,  20,  20},
            { 20,  30,  10,   0,   0,  10,  30,  20}
    };

    //EndGame bliver ikke anvendt, lagt til evt senere brug inkl metode der implementer endGame længere nede!
    private static final int[][] KingBoardHeuristicEndGame = {
            {-50, -40, -30, -20, -20, -30, -40, -50},
            {-30, -20, -10,   0,   0, -10, -20, -30},
            {-30, -10,  20,  30,  30,  20, -10, -30},
            {-30, -10,  30,  40,  40,  30, -10, -30},
            {-30, -10,  30,  40,  40,  30, -10, -30},
            {-30, -10,  20,  30,  30,  20, -10, -30},
            {-30, -30,   0,   0,   0,   0, -30, -30},
            {-50, -30, -30, -30, -30, -30, -30, -50}
    };

    public King(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        isFirstMove = true;
        super.directions.add(new ArrayList<>() {{ add(1); add(1); }});
        super.directions.add(new ArrayList<>() {{ add(1); add(-1); }});
        super.directions.add(new ArrayList<>() {{ add(-1); add(1); }});
        super.directions.add(new ArrayList<>() {{ add(-1); add(-1); }});
        super.directions.add(new ArrayList<>() {{ add(0); add(1); }});
        super.directions.add(new ArrayList<>() {{ add(0); add(-1); }});
        super.directions.add(new ArrayList<>() {{ add(1); add(0); }});
        super.directions.add(new ArrayList<>() {{ add(-1); add(0); }});
        super.directions.add(new ArrayList<>() {{ add(0); add(2);}});
        super.directions.add(new ArrayList<>() {{ add(0); add(-2);}});
    }

    @Override
    public int getHeuristicValue(){
        int boardHeuristicValue;

        if(isWhite) {
            boardHeuristicValue = KingBoardHeuristicMiddleGame[currentXPosition][currentYPosition];
        } else{
            boardHeuristicValue = KingBoardHeuristicMiddleGame[7 -currentXPosition][currentYPosition];
        }

        return kingValue + boardHeuristicValue;
    }

    //Til hvis vi tilføjer endGame senere
//    @Override
//    public int getHeuristicValue(boolean endGame) {
//        int[][] currentHeuristic;
//        if (endGame) {
//            currentHeuristic = KingBoardHeuristicEndGame;
//        } else {
//            currentHeuristic = KingBoardHeuristicMiddleGame;
//        }
//
//        int boardHeuristicValue;
//        if (isWhite) {
//            boardHeuristicValue = currentHeuristic[currentYPosition][currentXPosition];
//        } else {
//            boardHeuristicValue = currentHeuristic[7 - currentYPosition][currentXPosition];
//        }
//
//        return kingValue + boardHeuristicValue;
//    }

    @Override
    public int[][] getBoardHeuristic() {
        return KingBoardHeuristicMiddleGame;
    }

    public String toString() {
        if (isWhite) {
            return "K";
        }
        return "k";
    }
}
