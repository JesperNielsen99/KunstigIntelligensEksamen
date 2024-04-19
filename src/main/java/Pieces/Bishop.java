package Pieces;

import java.util.ArrayList;

public class Bishop extends Piece{

    public Bishop(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        super.directions.add(new ArrayList<>() {{ add(1); add(1); }});
        super.directions.add(new ArrayList<>() {{ add(-1); add(1); }});
        super.directions.add(new ArrayList<>() {{ add(1); add(-1); }});
        super.directions.add(new ArrayList<>() {{ add(-1); add(-1); }});
    }

    public String toString() {
        if (isWhite) {
            return "B";
        }
        return "b";
    }
}
