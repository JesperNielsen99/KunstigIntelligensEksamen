package Pieces;

import java.util.ArrayList;
import Board.Board;

public abstract class Piece {
    public boolean isWhite;
    public boolean isFirstMove = false;
    public int currentXPosition;
    public int currentYPosition;
    public ArrayList<ArrayList<Integer>> directions;


    public Piece(boolean isWhite, int currentXPosition, int currentYPosition) {
        this.isWhite = isWhite;
        this.currentXPosition = currentXPosition;
        this.currentYPosition = currentYPosition;
        directions = new ArrayList<>();
    }

    public abstract int getHeuristicValue();


    public static void printHeuristicValues(Board board) {
        ArrayList<ArrayList<Piece>> boardArray = board.getBoard();
        for (int i = 0; i < boardArray.size(); i++) {
            for (int j = 0; j < boardArray.get(i).size(); j++) {
                Piece piece = boardArray.get(i).get(j);
                if (piece != null) {
                    System.out.println(piece + " at position (" + i + "," + j +
                            ") has heuristic value: " + piece.getHeuristicValue());
                }
            }
        }
    }
    }