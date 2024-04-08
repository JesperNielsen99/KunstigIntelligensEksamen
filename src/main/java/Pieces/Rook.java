package Pieces;

import java.util.ArrayList;



public class Rook extends Piece {

    public Rook(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        super.directions.add(new ArrayList<>() {{ add(0); add(1); }});
        super.directions.add(new ArrayList<>() {{ add(0); add(-1); }});
        super.directions.add(new ArrayList<>() {{ add(1); add(0); }});
        super.directions.add(new ArrayList<>() {{ add(-1); add(0); }});
    }

    public String toString() {
        if (isWhite) {
            return "R";
        }
        return "r";
    }
}
