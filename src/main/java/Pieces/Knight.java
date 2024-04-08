package Pieces;

import java.util.ArrayList;

public class Knight extends Piece {

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

    public String toString() {
        if (isWhite) {
            return "H";
        }
        return "h";
    }
}
