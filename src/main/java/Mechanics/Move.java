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
        Piece king = board.findKing(piece.isWhite);

        // Find the king's position
        ArrayList<Integer> kingPosition = new ArrayList<>();
        kingPosition.add(king.currentXPosition);
        kingPosition.add(king.currentYPosition);

        // Check if any opponent's pieces threaten the king
        ArrayList<Piece> pieces;
        if (!piece.isWhite) {
            pieces = board.getWhitePieces();
        } else {
            pieces = board.getBlackPieces();
        }

        for (Piece pieceOnBoard : pieces) {
            if (pieceOnBoard.getClass() != King.class) {
                ArrayList<ArrayList<Integer>> pieceList = getLegalMoves(board, pieceOnBoard, false);
                if (pieceList.contains(kingPosition)) { // If the piece can attack the king
                    piecesCheckingKing.add(pieceOnBoard);
                }
            }
        }
        return piecesCheckingKing;
    }

    public ArrayList<ArrayList<Integer>> getLegalMoves(Board board, Piece piece, boolean checkIfKingIsCheck) {
        ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<>();
        if (checkIfKingIsCheck) {
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
                            ArrayList<Boolean> isCheckedAfterMove = isCheckedAfterMove(board, piece, move);
                            if (!isCheckedAfterMove.get(0)) {
                                legalMoves.add(move);
                                if (!isCheckedAfterMove.get(1)) {
                                    x = nextX;
                                    y = nextY;
                                } else {
                                    break;
                                }
                            } else {
                                break;
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
                        if (isNotOutOfBounds(move) && !isOccupied(board, move) && direction == piece.directions.get(0)) {
                            ArrayList<Boolean> isCheckedAfterMove = isCheckedAfterMove(board, piece, move);
                            if (!isCheckedAfterMove.get(0)) {
                                legalMoves.add(move);
                                if (piece.isFirstMove && !isCheckedAfterMove.get(1)) {
                                    x = nextX;
                                    y = nextY;
                                    nextX = x + direction.get(0);
                                    nextY = y + direction.get(1);

                                    move = new ArrayList<>();

                                    move.add(nextX);
                                    move.add(nextY);

                                    isCheckedAfterMove = isCheckedAfterMove(board, piece, move);
                                    if (isCheckedAfterMove.size() > 1 && !isCheckedAfterMove.get(1)) {
                                        legalMoves.add(move);
                                    }
                                }
                            } else if (isOccupied(board, move) && !isOccupiedByYou(board, move, piece.isWhite) && direction != piece.directions.get(0)) {
                                if (!isCheckedAfterMove.get(1)) {
                                    legalMoves.add(move);
                                }
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

                        if (!isCheckedAfterMove(board, piece, move).get(0)) {
                            legalMoves.add(move);
                        }
                    }
                }
            } else if (piecesCheckingKing.size() == 1) { ///////////////////////////////////////////////////////////////////////////
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
                            if (isNotOutOfBounds(move)) {
                                ArrayList<Boolean> isCheckedAfterMove = isCheckedAfterMove(board, piece, move);
                                if (!isCheckedAfterMove.get(0)) {
                                    legalMoves.add(move);
                                    if (!isCheckedAfterMove.get(1)) {
                                        x = nextX;
                                        y = nextY;
                                    } else {
                                        break;
                                    }
                                } else {
                                    x = nextX;
                                    y = nextY;
                                }
                            } else {
                                x = nextX;
                                y = nextY;
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
                }
            }
        } else {
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
                                break;
                            }
                        } else {
                            break;
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
                    } else if (isNotOutOfBounds(move) && isOccupied(board, move) && !isOccupiedByYou(board, move, piece.isWhite) && direction != piece.directions.get(0)) {
                        legalMoves.add(move);
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
        }
        return legalMoves;
    }

    public ArrayList<Boolean> isCheckedAfterMove(Board board, Piece piece, ArrayList<Integer> move) {
        ArrayList<Boolean> legalMoveOrNot = isLegalMove(board, move, piece.isWhite);
        ArrayList<Boolean> isCheckedAfterMove = new ArrayList<>();
        ArrayList<Piece> isKingInCheck = new ArrayList<>();
        int x = piece.currentXPosition;
        int y = piece.currentYPosition;
        if (piece.getClass() == Pawn.class || piece.getClass() == Rook.class || piece.getClass() == Bishop.class || piece.getClass() == Queen.class) {
            int newMoveX = move.get(0);
            int newMoveY = move.get(1);
            ArrayList<Integer> nextMove = new ArrayList<>();
            nextMove.add(newMoveX);
            nextMove.add(newMoveY);
            if (isNotOutOfBounds(nextMove) && board.getBoard().get(newMoveX).get(newMoveY) == null) {
                board.getBoard().get(newMoveX).set(newMoveY, piece);
                piece.currentXPosition = newMoveY;
                piece.currentYPosition = newMoveY;
                isKingInCheck = isKingInCheck(board, piece);
                if (isKingInCheck.isEmpty()) {
                    if (legalMoveOrNot.get(0)) {
                        isCheckedAfterMove.add(false);
                        if (legalMoveOrNot.get(1)) {
                            isCheckedAfterMove.add(false);
                        } else {
                            isCheckedAfterMove.add(true);
                        }
                    } else {
                        isCheckedAfterMove.add(true);
                    }
                } else {
                    isCheckedAfterMove.add(true);
                }
                board.getBoard().get(newMoveX).set(newMoveY, null);
                piece.currentXPosition = x;
                piece.currentYPosition = y;
            } else {
                isCheckedAfterMove.add(true);
            }
        } else {
            piece.currentXPosition = move.get(0);
            piece.currentYPosition = move.get(1);
            isKingInCheck = isKingInCheck(board, piece);
            if (isKingInCheck.isEmpty()) {
                if (legalMoveOrNot.get(0)) {
                    isCheckedAfterMove.add(false);
                } else {
                    isCheckedAfterMove.add(true);
                }
            } else {
                isCheckedAfterMove.add(true);
            }
            piece.currentXPosition = x;
            piece.currentYPosition = y;
        }
        return isCheckedAfterMove;
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
                } else {
                    boolean legalMove = false;
                    boolean canMoveAfter = false;
                    legalMoveCanMoveAfter.add(legalMove);
                    legalMoveCanMoveAfter.add(canMoveAfter);
                }
            }
        } else {
            boolean legalMove = false;
            boolean canMoveAfter = false;
            legalMoveCanMoveAfter.add(legalMove);
            legalMoveCanMoveAfter.add(canMoveAfter);
        }
        return legalMoveCanMoveAfter;
    }

    public boolean isGameOver(Board board) {
        boolean isGameOver = true;
        // Check if the white king is in checkmate
        if (isInCheckmate(board, true)) {
            return true;
        }

        // Check if the black king is in checkmate
        if (isInCheckmate(board, false)) {
            return true;
        }

        // Check if stalemate
        if (isStalemate(board)) {
            return true;
        }

        // If none of the above conditions are met, the game is not over
        return false;
    }

    // Method to check if a particular isWhite's king is in checkmate
    private boolean isInCheckmate(Board board, boolean isWhite) {
        ArrayList<Piece> pieces = new ArrayList<>();
        if (isWhite) {
            pieces = board.getWhitePieces();
        } else {
            pieces = board.getBlackPieces();
        }
        for (Piece piece : pieces) {
            ArrayList<ArrayList<Integer>> legalMoves = getLegalMoves(board, piece, true);
            if (!legalMoves.isEmpty()) {
                return false; // There exists at least one legal move, so not in checkmate
            }
        }
        // No legal moves for any piece of the specified isWhite, so in checkmate
        return true;
    }

    // Method to check for stalemate
    private boolean isStalemate(Board board) {
        ArrayList<Piece> pieces = new ArrayList<>();
        if (board.getPlayer() == true){
            pieces.addAll(board.getWhitePieces());
        } else {
            pieces.addAll(board.getBlackPieces());
        }
        for (Piece piece : pieces) {
            ArrayList<ArrayList<Integer>> legalMoves = getLegalMoves(board, piece, true);
            if (!legalMoves.isEmpty()) {
                return false; // There exists at least one legal move, so not stalemate
            }
        }
        // No legal moves for any piece of the current player, so stalemate
        return true;
    }

    public int[] inputScanner() {
        int[] coordinates = new int[4];
        coordinates[2] = -1;

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
        } else if (positions.length == 1) {
            for (String position : positions) {
                int file = position.charAt(0) - 'a'; // Convert letter to corresponding array index
                int rank = Character.getNumericValue(position.charAt(1)) - 1; // Convert number to corresponding array index
                if (position == positions[0]) {
                    coordinates[0] = rank;
                    coordinates[1] = file;
                }
            }
        } else {
            System.out.println("Wrong amount of inputs.");
            System.out.println("Enter the chess positions (e.g., a3 a5):");
            inputScanner();
        }
        return coordinates;
    }

    public void takeTurn(Board board, boolean isWhite) {

        boolean isKingInCheck = !isKingInCheck(board, board.findKing(isWhite)).isEmpty();
        System.out.println(isKingInCheck);
        //if (isKingInCheck) {}
        //STOP CHECK
        //CASTLE, Ensure not in check.
        System.out.println(board);
        System.out.println("Which piece would you like to move?");
        System.out.println("Enter the chess positions (e.g., A3 A5):");

        int[] coordinates = inputScanner();

        int oldX = coordinates[0];
        int oldY = coordinates[1];
        int newX = -1;
        int newY = -1;
        if (coordinates[2] != -1) {
            newX = coordinates[2];
            newY = coordinates[3];
        }
        Piece piece = board.getBoard().get(oldX).get(oldY);

        if (piece != null && piece.isWhite == isWhite) {
        //if (piece != null) {
            ArrayList<ArrayList<Integer>> legalMoves = getLegalMoves(board, piece, true);
            if (coordinates[2] != -1) {
                if (!legalMoves.isEmpty()) {
                    ArrayList<Integer> move = new ArrayList<>();
                    move.add(newX);
                    move.add(newY);
                    if (legalMoves.contains(move)) {
                        movePiece(board, piece, move);
                        System.out.println("Turn ended!");
                    } else {
                        System.out.println("Illegal move. Please Select a new move.");
                        takeTurn(board, isWhite);
                    }
                } else {
                    System.out.println("No Legal moves. Please Select a new move.");
                    takeTurn(board, isWhite);
                }
            } else {
                for (ArrayList<Integer> move : legalMoves) {
                    System.out.print("[" + (char) (move.get(1) + 'a') + (move.get(0) + 1) + "]");
                }
                System.out.println("\n");
                takeTurn(board, isWhite);
            }
        } else if (piece == null) {
            System.out.println("That space is blank. Please Select a new move.");
            takeTurn(board, isWhite);
        } else {
            System.out.println("Move your own piece. Please Select a new move.");
            takeTurn(board, isWhite);
        }
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
}