package Pieces;

import java.util.ArrayList;
import java.util.Arrays;

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

    //anvendes til beregning af en piece værdi
    public abstract int getHeuristicValue();
    //anvendes til at få board værdier til specifik brik, i forbindelse med træk.
    public abstract int[][] getBoardHeuristic();

    //viser brikkens heuristic value på dens position. Viser for alle brikker på boarded.
    public static void printHeuristicValues(Board board) {
        System.out.println("Printing Heuristic Values:");
        ArrayList<ArrayList<Piece>> boardArray = board.getBoard();
        for (int i = 0; i < boardArray.size(); i++) {
            for (int j = 0; j < boardArray.get(i).size(); j++) {
                Piece piece = boardArray.get(i).get(j);
                if (piece != null) {
                    String pieceColor = piece.isWhite ? "White" : "Black";
                    String message = "Piece " + piece.toString() + " (" + pieceColor + ") at position (" + i + "," + j +
                            ") has heuristic value: " + piece.getHeuristicValue() + "\n";
                    System.out.print(message);
                }
            }
        }
    }

    //Viser heuristic values på board for den enkelte piece.
    public static void printPieceBoardHeuristicValues(Piece piece) {
        int[][] heuristicValues = piece.getBoardHeuristic();
        System.out.println("Board heuristic values for " + piece.getClass().getSimpleName() + ":");
        for (int[] row : heuristicValues) {
            System.out.println(Arrays.toString(row));
        }
    }


