package Pieces;

import java.util.ArrayList;

public class King extends Piece {

    public King(boolean isWhite, int x, int y) {
        super(isWhite, x, y);
        super.directions.add(new ArrayList<>() {{ add(1); add(1); }});
        super.directions.add(new ArrayList<>() {{ add(-1); add(-1); }});
        super.directions.add(new ArrayList<>() {{ add(1); add(-1); }});
        super.directions.add(new ArrayList<>() {{ add(-1); add(-1); }});
        super.directions.add(new ArrayList<>() {{ add(0); add(1); }});
        super.directions.add(new ArrayList<>() {{ add(0); add(-1); }});
        super.directions.add(new ArrayList<>() {{ add(1); add(0); }});
        super.directions.add(new ArrayList<>() {{ add(-1); add(0); }});
    }

    public String toString() {
        if (isWhite) {
            return "K";
        }
        return "k";
    }
}
