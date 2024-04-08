package Mechanics;

import Board.Board;
import Pieces.*;

import java.util.ArrayList;
import java.util.Scanner;

public class Move {
    Scanner scanner;

    public Move() {
        scanner = new Scanner(System.in);
    }

    public boolean isOccupied(Board board, ArrayList<Integer> move) {
        int x = move.get(0);
        int y = move.get(1);
        return board.getBoard().get(x).get(y) != null;
    }

    public boolean isOccupiedByYou(Board board, ArrayList<Integer> move, boolean isWhite) {
        int x = move.get(0);
        int y = move.get(1);
        return board.getBoard().get(x).get(y).isWhite == isWhite;
    }

    public boolean isNotOutOfBounds(int x, int y) {
        return x >= 0 && x <= 7 && y >= 0 && y <= 7;
    }

    public ArrayList<ArrayList<Integer>> getLegalMoves(Board board, Piece piece) {
        ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<>();
        if (piece.getClass() == Bishop.class || piece.getClass() == Rook.class ||piece.getClass() == Queen.class) {
            for (ArrayList<Integer> direction : piece.directions) {
                int x = piece.currentXPosition;
                int y = piece.currentYPosition;

                while (isNotOutOfBounds(x, y)) {
                    int nextX = x + direction.get(0);
                    int nextY = y + direction.get(1);

                    ArrayList<Integer> move = new ArrayList<>();
                    move.add(nextX);
                    move.add(nextY);

                    ArrayList<Boolean> legalMoveOrNot = isLegalMove(board, move, piece.isWhite);

                    if (legalMoveOrNot.get(0)) {
                        legalMoves.add(move);
                        if (legalMoveOrNot.get(1)) {
                            x = nextX;
                            y = nextY;
                        } else {
                            x = -1;
                            y = -1;
                        }
                    } else {
                        x = -1;
                        y = -1;
                    }
                }
            }
        } else {
            for (ArrayList<Integer> direction : piece.directions) {
                int x = piece.currentXPosition;
                int y = piece.currentYPosition;
                int nextX = x + direction.get(0);
                int nextY = y + direction.get(1);

                ArrayList<Integer> move = new ArrayList<>();
                move.add(nextX);
                move.add(nextY);

                ArrayList<Boolean> legalMoveOrNot = isLegalMove(board, move, piece.isWhite);

                if (legalMoveOrNot.get(0)) {
                    legalMoves.add(move);
                }
            }
        }
        return legalMoves;
    }

    public ArrayList<Boolean> isLegalMove(Board board, ArrayList<Integer> move, boolean isWhite) {
        ArrayList<Boolean> legalMoveCanMoveAfter = new ArrayList<>();
        if (isNotOutOfBounds(move.get(0), move.get(1))) {
            if (!isOccupied(board, move)) {
                boolean legalMove = true;
                boolean canMoveAfter = true;
                legalMoveCanMoveAfter.add(legalMove);
                legalMoveCanMoveAfter.add(canMoveAfter);
                return legalMoveCanMoveAfter;
            } else {
                if (!isOccupiedByYou(board, move, isWhite)) {
                    boolean legalMove = true;
                    boolean canMoveAfter = false;
                    legalMoveCanMoveAfter.add(legalMove);
                    legalMoveCanMoveAfter.add(canMoveAfter);
                    return legalMoveCanMoveAfter;
                }
            }
        }
        boolean legalMove = false;
        boolean canMoveAfter = false;
        legalMoveCanMoveAfter.add(legalMove);
        legalMoveCanMoveAfter.add(canMoveAfter);
        return legalMoveCanMoveAfter;
    }

    public void movePiece(Board board, boolean isWhite) {
        System.out.println(board);
        System.out.println("Which piece would you like to move?");
        int x = scanner.nextInt()-1;
        int y = scanner.nextInt()-1;
        Piece piece = board.getBoard().get(x).get(y);
        System.out.println(piece);
        if (piece != null && piece.isWhite == isWhite) {
            ArrayList<ArrayList<Integer>> legalMoves = getLegalMoves(board, piece);
            System.out.println(legalMoves);
            if (!legalMoves.isEmpty()) {
                System.out.println("Where would you like to move " + piece + " to?");
                int newX = scanner.nextInt() - 1;
                int newY = scanner.nextInt() - 1;
                ArrayList<Integer> move = new ArrayList<>();
                move.add(newX);
                move.add(newY);
                if (legalMoves.contains(move)) {
                    Piece pieceToMove = board.getBoard().get(x).get(y);
                    board.getBoard().get(x).set(y, null);
                    pieceToMove.currentXPosition = newX;
                    pieceToMove.currentYPosition = newY;
                    board.getBoard().get(newX).set(newY, pieceToMove);
                } else {
                    System.out.println("Illegal move. Please Select a new move.");
                    movePiece(board, isWhite);
                }
            } else {
                System.out.println("No Legal moves. Please Select a new move.");
                movePiece(board, isWhite);
            }
        } else if (piece == null) {
            System.out.println("That space is blank. Please Select a new move.");
            movePiece(board, isWhite);
        } else {
            System.out.println("Move your own piece. Please Select a new move.");
            movePiece(board, isWhite);
        }
    }
}
