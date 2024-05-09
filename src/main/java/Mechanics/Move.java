package Mechanics;
import Pieces.*;
import Board.Board;

import java.util.ArrayList;
import java.util.Scanner;

public class Move {
    private Scanner scanner;

    public Move() {
        scanner = new Scanner(System.in);
    }

    public boolean isOccupied(Board board, ArrayList<Integer> position) {
        int x = position.get(0);
        int y = position.get(1);
        return board.getPieceAt(x, y) != null;
    }

    public boolean isOccupiedBySameColor(Board board, ArrayList<Integer> position, boolean isWhite) {
        int x = position.get(0);
        int y = position.get(1);
        Piece piece = board.getPieceAt(x, y);
        return piece != null && piece.isWhite == isWhite;
    }

    public boolean isWithinBounds(ArrayList<Integer> position) {
        int x = position.get(0);
        int y = position.get(1);
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    //
    public boolean isKingInCheck(Board board, boolean isWhite) {

        ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<>();
        Piece king = board.findKing(isWhite);
        int kingX = king.currentXPosition;
        int kingY = king.currentYPosition;
        boolean kingWhite = king.isWhite;

        Piece knight = new Knight(kingWhite, kingX, kingY);
        Piece queen = new Queen(kingWhite, kingX, kingY);
        Piece pawn = new Pawn(kingWhite, kingX, kingY);

        board.setPieceAt(knight, kingX, kingY);
        legalMoves.addAll(getLegalMoves(board, knight, false));
        board.setPieceAt(king, kingX, kingY);

        board.setPieceAt(queen, kingX, kingY);
        legalMoves.addAll(getLegalMoves(board, knight, false));
        board.setPieceAt(king, kingX, kingY);

        board.setPieceAt(pawn, kingX, kingY);
        legalMoves.addAll(getLegalMoves(board, knight, false));
        board.setPieceAt(king, kingX, kingY);
        for (ArrayList<Integer> move : legalMoves) {
            if (move.get(0) == kingX && move.get(1) == kingY) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<ArrayList<Integer>> getLegalMoves(Board board, Piece piece, boolean checkIfKingIsCheck) {
        ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<>();
        if (checkIfKingIsCheck) {
            //if (!isKingInCheck(board, piece.isWhite)) {
                if (piece.getClass() == Bishop.class || piece.getClass() == Rook.class || piece.getClass() == Queen.class) {
                    legalMoves.addAll(getLegalMultipleMoves(board, piece, checkIfKingIsCheck));
                } else if (piece.getClass() == Pawn.class) {
                    legalMoves.addAll(getLegalPawnMoves(board, piece, checkIfKingIsCheck));
                }else {
                    legalMoves.addAll(getLegalSingleMoves(board, piece, true));
                }
            //}
        } else {
            if (piece.getClass() == Bishop.class || piece.getClass() == Rook.class || piece.getClass() == Queen.class) {
                legalMoves.addAll(getLegalMultipleMoves(board, piece, checkIfKingIsCheck));
            } else {
                /*
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
                */
                legalMoves.addAll(getLegalSingleMoves(board, piece, checkIfKingIsCheck));
            }
        }
        return legalMoves;
    }

    public ArrayList<ArrayList<Integer>> getLegalPawnMoves (Board board, Piece piece, boolean checkIfKingIsInCheck) {
        ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<>();
        for (ArrayList<Integer> direction : piece.directions) {
            int oldX = piece.currentXPosition;
            int oldY = piece.currentYPosition;
            int newX = oldX + direction.get(0);
            int newY = oldY + direction.get(1);
            ArrayList<Integer> move = new ArrayList<>();
            move.add(newX);
            move.add(newY);
            if (isWithinBounds(move) && !isOccupied(board, move) && direction == piece.directions.get(0)) {
                if (!isKingInCheck(board, piece.isWhite)) {
                    checkMove(board, piece, legalMoves, checkIfKingIsInCheck, oldX, oldY, move);
                    boolean canMoveAgain = false;
                    if (piece.isFirstMove && isWithinBounds(move) && !isOccupiedBySameColor(board, move, piece.isWhite)) {
                        canMoveAgain = true;
                    }
                    move.set(0, move.get(0)+direction.get(0));
                    move.set(1, move.get(1)+direction.get(1));
                    if (piece.isFirstMove && isWithinBounds(move) && canMoveAgain) {
                        checkMove(board, piece, legalMoves, checkIfKingIsInCheck, newX, newY, move);
                    }
                }
            }
        }
        return legalMoves;
    }

    public ArrayList<ArrayList<Integer>> getLegalSingleMoves (Board board, Piece piece, boolean checkIfKingIsInCheck) {
        ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<>();
        for (ArrayList<Integer> direction : piece.directions) {
            int oldX = piece.currentXPosition;
            int oldY = piece.currentYPosition;
            int newX = oldX + direction.get(0);
            int newY = oldY + direction.get(1);
            ArrayList<Integer> move = new ArrayList<>();
            move.add(newX);
            move.add(newY);
            checkMove(board, piece, legalMoves, checkIfKingIsInCheck, oldX, oldY, move);
        }
        return legalMoves;
    }

    public ArrayList<ArrayList<Integer>> getLegalMultipleMoves (Board board, Piece piece, boolean checkIfKingIsInCheck) {
        ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<>();
        for (ArrayList<Integer> direction : piece.directions) {
            int oldX = piece.currentXPosition;
            int oldY = piece.currentYPosition;
            int newX = oldX + direction.get(0);
            int newY = oldY + direction.get(1);
            ArrayList<Integer> move = new ArrayList<>();
            move.add(newX);
            move.add(newY);

            boolean canMoveAgain = true;
            while (isWithinBounds(move) && canMoveAgain) {
                if (isOccupiedBySameColor(board, move, piece.isWhite)) {
                    break;
                } else if (isOccupied(board, move)) {
                    canMoveAgain = false;
                }
                checkMove(board, piece, legalMoves, checkIfKingIsInCheck, oldX, oldY, move);
            }
        }
        return legalMoves;
    }

    public ArrayList<ArrayList<Integer>>  checkMove(Board board, Piece piece, ArrayList<ArrayList<Integer>> legalMoves, boolean checkIfKingIsInCheck, int x, int y, ArrayList<Integer> move) {
        int newX = move.get(0);
        int newY = move.get(1);
        if (isWithinBounds(move)) {
            if (checkIfKingIsInCheck) {
                if (!isKingInCheck(board, piece.isWhite) && !isOccupiedBySameColor(board, move, piece.isWhite)) {
                    Piece capturedPiece = board.movePiece(piece, newX, newY);
                    legalMoves.add(move);
                    board.undoMove(piece, capturedPiece, x, y, newX, newY);
                }
            } else {
                if (!isOccupiedBySameColor(board, move, piece.isWhite)) {
                    Piece capturedPiece = board.movePiece(piece, newX, newY);
                    legalMoves.add(move);
                    board.undoMove(piece, capturedPiece, x, y, newX, newY);
                }
            }
        }
        return legalMoves;
    }


    public boolean isMovePuttingOwnKingInCheck(Board board, Piece piece, ArrayList<Integer> move) {
        int oldX = piece.currentXPosition;
        int oldY = piece.currentYPosition;
        int newX = move.get(0);
        int newY = move.get(1);

        Piece capturedPiece = board.getPieceAt(newX, newY);
        board.setPieceAt(piece, newX, newY); // Temporarily move the piece to see if it puts own king in check

        boolean ownKingInCheck = isKingInCheck(board, piece.isWhite);

        board.undoMove(piece, capturedPiece, oldX, oldY, newX, newY); // Undo the temporary move

        return ownKingInCheck;
    }

    public void movePiece(Board board, Piece piece, ArrayList<Integer> move) {
        int currentX = piece.currentXPosition;
        int currentY = piece.currentYPosition;
        if (piece.getClass() == Pawn.class && ((piece.isWhite && move.get(0) == 7) || (!piece.isWhite && move.get(0) == 0))) {
            promotePawn(board, piece, move.get(0), move.get(1));
        } else {
            piece.currentXPosition = move.get(0);
            piece.currentYPosition = move.get(1);
            board.getBoard().get(move.get(0)).set(move.get(1), piece);
        }
        board.getBoard().get(currentX).set(currentY, null);
        piece.isFirstMove = false;
        board.changeTurns();
    }

    public void promotePawn(Board board, Piece pawn, int x, int y) {
        if (pawn.getClass() == Pawn.class && (x == 7 || x == 0)) {
            Queen newQueen = new Queen(pawn.isWhite, y, x);
            board.getBoard().get(y).set(x, newQueen);
            if (pawn.isWhite) {
                board.getWhitePieces().remove(pawn);
                board.getWhitePieces().add(newQueen);
            } else {
                board.getBlackPieces().remove(pawn);
                board.getBlackPieces().add(newQueen);
            }
            board.getBoard().get(x).set(y, newQueen);
            System.out.println("Pawn has been promoted to a Queen!");
        }
    }

    public boolean isGameOver(Board board) {
        boolean isWhiteTurn = board.getPlayer();
        Piece king = board.findKing(isWhiteTurn);
        return isInCheckmate(board, king) || isStalemate(board);
    }

    private boolean isInCheckmate(Board board, Piece king) {
        ArrayList<Piece> pieces = new ArrayList<>(king.isWhite ? board.getWhitePieces() : board.getBlackPieces());
        for (Piece piece : pieces) {
            ArrayList<ArrayList<Integer>> legalMoves = getLegalMoves(board, piece, true);
            if (!legalMoves.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean isStalemate(Board board) {
        boolean isWhiteTurn = board.getPlayer();
        if (!isKingInCheck(board, isWhiteTurn)) {
            ArrayList<Piece> pieces = new ArrayList<>(isWhiteTurn ? board.getWhitePieces() : board.getBlackPieces());
            for (Piece piece : pieces) {
                ArrayList<ArrayList<Integer>> legalMoves = getLegalMoves(board, piece, true);
                if (!legalMoves.isEmpty()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public int[] getInputFromUser() {
        int[] coordinates = new int[4];
        coordinates[2] = -1;

        System.out.println("Enter the chess positions (e.g., A3 A5):");
        String input = scanner.nextLine();

        String[] positions = input.split(" ");
        if (positions.length == 2) {
            for (String position : positions) {
                int file = position.charAt(0) - 'a'; // Convert letter to corresponding array index
                int rank = Character.getNumericValue(position.charAt(1)) - 1; // Convert number to corresponding array index
                if (position.equals(positions[0])) {
                    coordinates[0] = rank;
                    coordinates[1] = file;
                } else {
                    coordinates[2] = rank;
                    coordinates[3] = file;
                }
            }
        } else if (positions.length == 1) {
            for (String position : positions) {
                int file = position.charAt(0) - 'a'; // Convert letter to corresponding array index
                int rank = Character.getNumericValue(position.charAt(1)) - 1; // Convert number to corresponding array index
                if (position.equals(positions[0])) {
                    coordinates[0] = rank;
                    coordinates[1] = file;
                }
            }
        } else {
            System.out.println("Wrong amount of inputs.");
            System.out.println("Enter the chess positions (e.g., A3 A5):");
            return getInputFromUser();
        }
        return coordinates;
    }

    public void takeTurn(Board board, boolean isWhite) {
        boolean isKingInCheck = isKingInCheck(board, isWhite);

        System.out.println(isKingInCheck ? "Your king is in check!" : "");
        System.out.println(board);
        System.out.println("Which piece would you like to move?");
        int[] coordinates = getInputFromUser();

        int oldX = coordinates[0];
        int oldY = coordinates[1];
        int newX = coordinates[2];
        int newY = coordinates[3];

        Piece piece = board.getPieceAt(oldX, oldY);

        if (piece != null && piece.isWhite == isWhite) {
            ArrayList<ArrayList<Integer>> legalMoves = getLegalMoves(board, piece, true);
            if (newX != -1) {
                ArrayList<Integer> move = new ArrayList<>();
                move.add(newX);
                move.add(newY);
                if (legalMoves.contains(move) && !isMovePuttingOwnKingInCheck(board, piece, move)) {
                    board.setPieceAt(piece, newX, newY);
                    System.out.println("Turn ended!");
                } else {
                    System.out.println("Illegal move or puts your own king in check. Please select a new move.");
                    takeTurn(board, isWhite);
                }
            } else {
                System.out.print("Legal moves: ");
                for (ArrayList<Integer> move : legalMoves) {
                    System.out.print("(" + (char) (move.get(1) + 'a') + (move.get(0) + 1) + ") ");
                }
                System.out.println("\n");
                takeTurn(board, isWhite);
            }
        } else if (piece == null) {
            System.out.println("That space is empty. Please select a piece to move.");
            takeTurn(board, isWhite);
        } else {
            System.out.println("Move your own piece. Please select a new move.");
            takeTurn(board, isWhite);
        }
    }
}