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

    public boolean isNotOutOfBounds(ArrayList<Integer> move) {
        return move.get(0) >= 0 && move.get(0) <= 7 && move.get(1) >= 0 && move.get(1) <= 7;
    }

    public ArrayList<Piece> isKingInCheck(Board board, Piece piece) {
        ArrayList<Piece> piecesCheckingKing = new ArrayList<>();

        // Find the king's position
        ArrayList<Integer> kingPosition = new ArrayList<>();
        kingPosition.add(piece.currentXPosition);
        kingPosition.add(piece.currentYPosition);

        // Check if any opponent's pieces threaten the king
        ArrayList<Piece> pieces;
        if (!piece.isWhite) {
            pieces = board.getWhitePieces();
        } else {
            pieces = board.getBlackPieces();
        }

        for (Piece pieceOnBoard : pieces) {
            if (pieceOnBoard.getClass() != King.class) {
                ArrayList<ArrayList<Integer>> pieceList = getLegalMoves(board, pieceOnBoard);
                if (pieceList.contains(kingPosition)) { // If the piece can attack the king
                    piecesCheckingKing.add(pieceOnBoard);
                }
            }
        }
        return piecesCheckingKing;
    }

    public ArrayList<Piece> isTakable(Board board, Piece piece) {
        ArrayList<Piece> piecesCapableOfTaking = new ArrayList<>();
        ArrayList<Integer> piecePosition = new ArrayList<>();
        piecePosition.add(piece.currentXPosition);
        piecePosition.add(piece.currentYPosition);

        // Check if any opponent's pieces threaten the king
        ArrayList<Piece> pieces;
        if (!piece.isWhite) {
            pieces = board.getWhitePieces();
        } else {
            pieces = board.getBlackPieces();
        }

        for (Piece pieceOnBoard : pieces) {
            ArrayList<ArrayList<Integer>> pieceList = getLegalMoves(board, pieceOnBoard);
            if (pieceList.contains(piecePosition)) { // If the piece can attack the king
                piecesCapableOfTaking.add(pieceOnBoard);
            }
        }
        return piecesCapableOfTaking;
    }

    public ArrayList<ArrayList<Integer>> getLegalMoves(Board board, Piece piece) {
        ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<>();
        ArrayList<Piece> piecesCheckingKing = isKingInCheck(board, piece);
        if (piecesCheckingKing.isEmpty()) {
            if (piece.getClass() == Bishop.class || piece.getClass() == Rook.class || piece.getClass() == Queen.class) {
                for (ArrayList<Integer> direction : piece.directions) {
                    int x = piece.currentXPosition;
                    int y = piece.currentYPosition;
                    ArrayList<Integer> position = new ArrayList<>();
                    position.add(x);
                    position.add(y);

                    while (isNotOutOfBounds(position)) {
                        position = new ArrayList<>();
                        position.add(x);
                        position.add(y);
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
            } else if (piece.getClass() == Pawn.class) {
                for (ArrayList<Integer> direction : piece.directions) {
                    int x = piece.currentXPosition;
                    int y = piece.currentYPosition;
                    int nextX = x + direction.get(0);
                    int nextY = y + direction.get(1);

                    ArrayList<Integer> move = new ArrayList<>();

                    move.add(nextX);
                    move.add(nextY);

                    ArrayList<Boolean> legalMoveOrNot = isLegalMove(board, move, piece.isWhite);

                    if (isNotOutOfBounds(move)) {
                        if (!isOccupied(board, move) && direction == piece.directions.get(0)) {
                            legalMoves.add(move);
                            if (piece.isFirstMove && legalMoveOrNot.get(1)) {
                                x = nextX;
                                y = nextY;
                                nextX = x + direction.get(0);
                                nextY = y + direction.get(1);

                                move = new ArrayList<>();

                                move.add(nextX);
                                move.add(nextY);

                                legalMoveOrNot = isLegalMove(board, move, piece.isWhite);
                                if (legalMoveOrNot.get(0)) {
                                    legalMoves.add(move);
                                }
                            }
                        } else if (isOccupied(board, move) && !isOccupiedByYou(board, move, piece.isWhite) && direction != piece.directions.get(0)) {
                            legalMoves.add(move);
                        }
                    }
                }
            } else if (piece.getClass() == King.class) {
                    for (ArrayList<Integer> direction : piece.directions) {
                        int x = piece.currentXPosition;
                        int y = piece.currentYPosition;
                        int nextX = x + direction.get(0);
                        int nextY = y + direction.get(1);

                        ArrayList<Integer> move = new ArrayList<>();
                        move.add(nextX);
                        move.add(nextY);

                        piece.currentXPosition = nextX;
                        piece.currentYPosition = nextY;
                        if (isKingInCheck(board, piece).isEmpty()) {
                            ArrayList<Boolean> legalMoveOrNot = isLegalMove(board, move, piece.isWhite);

                            if (legalMoveOrNot.get(0)) {
                                legalMoves.add(move);
                            }
                        }
                        piece.currentXPosition = x;
                        piece.currentYPosition = y;
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
        } else if (piecesCheckingKing.size() == 1) {
            if (piece.getClass() == King.class) {
                for (ArrayList<Integer> direction : piece.directions) {
                    int x = piece.currentXPosition;
                    int y = piece.currentYPosition;
                    int nextX = x + direction.get(0);
                    int nextY = y + direction.get(1);

                    ArrayList<Integer> move = new ArrayList<>();
                    move.add(nextX);
                    move.add(nextY);

                    piece.currentXPosition = nextX;
                    piece.currentYPosition = nextY;
                    if (isKingInCheck(board, piece).isEmpty()) {
                        ArrayList<Boolean> legalMoveOrNot = isLegalMove(board, move, piece.isWhite);

                        if (legalMoveOrNot.get(0)) {
                            legalMoves.add(move);
                        }
                    }
                    piece.currentXPosition = x;
                    piece.currentYPosition = y;
                }
            }
        } else {
        }
        return legalMoves;
    }

    public ArrayList<Boolean> isLegalMove(Board board, ArrayList<Integer> move, boolean isWhite) {
        ArrayList<Boolean> legalMoveCanMoveAfter = new ArrayList<>();
        if (isNotOutOfBounds(move)) {
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
        boolean isKingInCheck = !isKingInCheck(board, board.findKing(isWhite)).isEmpty();
        //if (isKingInCheck) {}
        //STOP CHECK
        //CASTLE, Ensure not in check.
        System.out.println(board);
        System.out.println("Which piece would you like to move?");
        System.out.println("Enter the chess positions (e.g., A3 A5):");

        int[] coordinates = inputScanner();

        int oldX = coordinates[0];
        int newX = coordinates[2];
        int oldY = coordinates[1];
        int newY = coordinates[3];

        Piece piece = board.getBoard().get(oldX).get(oldY);
        if (piece != null && piece.isWhite == isWhite) {
            ArrayList<ArrayList<Integer>> legalMoves = getLegalMoves(board, piece);
            if (!legalMoves.isEmpty()) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(newX);
                move.add(newY);
                if (legalMoves.contains(move)) {
                    board.getBoard().get(oldX).set(oldY, null);
                    piece.currentXPosition = newX;
                    piece.currentYPosition = newY;
                    board.getBoard().get(newX).set(newY, piece);
                    piece.isFirstMove = false;
                    if (piece.getClass() == Pawn.class && (newY == 7 || newX == 0)) {
                        promotePawn(board, piece, newX, newY);
                    }
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

    public void promotePawn(Board board, Piece pawn, int x, int y) {
        if (pawn.getClass() == Pawn.class && (y == 7 || y == 0)) {
            Queen newQueen = new Queen(pawn.isWhite, x, y);
            board.getBoard().get(x).set(y, newQueen);
            System.out.println("Pawn has been promoted to a Queen!");
        }
    }

    public int[] inputScanner() {
        int[] coordinates = new int[4];

        String input = scanner.nextLine();

        String[] positions = input.split(" ");
        if (positions.length == 2) {
            for (String position : positions) {
                int file = position.charAt(0) - 'a'; // Convert letter to corresponding array index
                int rank = Character.getNumericValue(position.charAt(1)) - 1; // Convert number to corresponding array index
                if (position == positions[0]) {
                    coordinates[0] = rank;
                    coordinates[1] = file;
                } else {
                    coordinates[2] = rank;
                    coordinates[3] = file;
                }
            }
        } else {
            System.out.println("Wrong amount of inputs.");
            System.out.println("Enter the chess positions (e.g., a3 a5):");
            inputScanner();
        }
        return coordinates;
    }
}
