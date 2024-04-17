package Pieces;

import java.util.ArrayList;

public class Pawn extends Piece {

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

    public String toString() {
        if (isWhite) {
            return "P";
        }
        return "p";
    }

}

