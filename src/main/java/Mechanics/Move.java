package Mechanics;
import Pieces.*;
import Board.Board;

import java.util.ArrayList;
import java.util.Arrays;
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
        // First check if there is a piece at the position
        if (piece == null) {
            return false;  // If there's no piece, it can't be occupied by a piece of the same color
        }
        // Now safely check the color since we know piece is not null
        return piece.isWhite == isWhite;
    }

    public boolean isWithinBounds(ArrayList<Integer> position) {
        int x = position.get(0);
        int y = position.get(1);
        return x >= 0 && x < 8 && y >= 0 && y < 8;
    }

    public boolean isKingInCheck(Board board, boolean isWhite) {
        ArrayList<ArrayList<Integer>> legalMoves;
        ArrayList<Piece> enemyPieces = isWhite ? board.getBlackPieces() : board.getWhitePieces();
        Piece king = board.findKing(isWhite);
        int kingX = king.currentXPosition;
        int kingY = king.currentYPosition;
        boolean kingWhite = king.isWhite;

        ArrayList<Piece> piecesToTry = new ArrayList<>();
        piecesToTry.add(new Knight(kingWhite, kingX, kingY));
        piecesToTry.add(new Queen(kingWhite, kingX, kingY));
        piecesToTry.add(new Rook(kingWhite, kingX, kingY));
        piecesToTry.add(new Bishop(kingWhite, kingX, kingY));
        piecesToTry.add(new Pawn(kingWhite, kingX, kingY));

        for (Piece piece : piecesToTry) {
            board.setPieceAt(piece, kingX, kingY);
            legalMoves = new ArrayList<>();
            legalMoves.addAll(getLegalMoves(board, piece, false));
            board.setPieceAt(king, kingX, kingY);

            for (ArrayList<Integer> move : legalMoves) {
                for (Piece enemyPiece : enemyPieces) {
                    if (enemyPiece.getClass() == piece.getClass()) {
                        if (enemyPiece.currentXPosition == move.get(0) && enemyPiece.currentYPosition == move.get(1)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public ArrayList<ArrayList<Integer>> getLegalMoves(Board board, Piece piece, boolean checkIfKingIsCheck) {
        ArrayList<ArrayList<Integer>> legalMoves = new ArrayList<>();
        if (checkIfKingIsCheck) {
            if (piece.getClass() == Bishop.class || piece.getClass() == Rook.class || piece.getClass() == Queen.class) {
                legalMoves.addAll(getLegalMultipleMoves(board, piece, checkIfKingIsCheck));
            } else if (piece.getClass() == Pawn.class) {
                legalMoves.addAll(getLegalPawnMoves(board, piece, checkIfKingIsCheck));
            }else {
                legalMoves.addAll(getLegalSingleMoves(board, piece, checkIfKingIsCheck));
            }
        } else {
            if (piece.getClass() == Bishop.class || piece.getClass() == Rook.class || piece.getClass() == Queen.class) {
                legalMoves.addAll(getLegalMultipleMoves(board, piece, checkIfKingIsCheck));
            } else if (piece.getClass() == Pawn.class) {
                legalMoves.addAll(getLegalPawnMoves(board, piece, checkIfKingIsCheck));
            } else {
                checkForKingAlreadyMoved(board, piece);
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
                checkMove(board, piece, legalMoves, checkIfKingIsInCheck, oldX, oldY, move);
                boolean canMoveAgain = false;
                move = new ArrayList<>();
                move.add(newX+direction.get(0));
                move.add(newY+direction.get(1));
                if (piece.isFirstMove && isWithinBounds(move)) {
                    canMoveAgain = true;
                }
                if (canMoveAgain && !isOccupied(board, move)) {
                    checkMove(board, piece, legalMoves, checkIfKingIsInCheck, oldX, oldY, move);
                }
            } else if (isOccupied(board, move) && direction != piece.directions.get(0)) {
                checkMove(board, piece, legalMoves, checkIfKingIsInCheck, oldX, oldY, move);
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
                newX += direction.get(0);
                newY += direction.get(1);
                move = new ArrayList<>();
                move.add(newX);
                move.add(newY);
            }
        }
        return legalMoves;
    }

    public boolean canShortCastle(Board board, Piece king) {
        if (king.isWhite){
            if (!isKingInCheck(board, true)){
                return board.getBoard().get(0).get(6) == null
                        && board.getBoard().get(0).get(5) == null
                        && king.isFirstMove;
            }

        }
        if (!king.isWhite) {
            if (!isKingInCheck(board, false )){
                return board.getBoard().get(7).get(6) == null
                        && board.getBoard().get(7).get(5) == null
                        && king.isFirstMove;
            }
        }
        return false;
    }

    public boolean canLongCastle(Board board, Piece king) {
        if (king.isWhite){
            if (!isKingInCheck(board, true)){
                return board.getBoard().get(0).get(1) == null
                        && board.getBoard().get(0).get(2) == null
                        && board.getBoard().get(0).get(3) == null
                        && king.isFirstMove;
            }
        }
        if (!king.isWhite){
                if (!isKingInCheck(board, false)){
                    return board.getBoard().get(7).get(1) == null
                            && board.getBoard().get(7).get(2) == null
                            && board.getBoard().get(7).get(3) == null
                            && king.isFirstMove;
                }
        }
        return false;
    }

    public void checkAndDoCastle(Piece king, Board board, ArrayList<Integer> coord) {
        if (isCastleMove(king, coord)){
            if (king.isWhite){
                if (coord.get(1) == 6 && canShortCastle(board, king)){
                    castleWhiteShort(board);
                }

                if (coord.get(1) == 2 && canLongCastle(board, king)){
                    castleWhiteLong(board);
                }
            }

            if (!king.isWhite){
                if (coord.get(1) == 6 && canShortCastle(board, king)){
                    castleBlackShort(board);
                }

                if (coord.get(1) == 2 && canLongCastle(board, king)) {
                    castleBlackLong(board);
                }
            }
        }
    }

    public boolean isCastleMove(Piece piece, ArrayList<Integer> coord){
        if (piece.getClass() == King.class && !piece.isFirstMove) {
            toggleCanCastle(piece);
        }

        if (piece.getClass() == King.class && piece.isWhite && piece.isFirstMove) {
            boolean isCastleMove = coord.equals(new ArrayList<>(Arrays.asList(0, 6)))
                    || coord.equals(new ArrayList<>(Arrays.asList(0, 2)));
            return isCastleMove;
        }

        if (piece.getClass() == King.class && !piece.isWhite && piece.isFirstMove) {
            return coord.equals(new ArrayList<>(Arrays.asList(7, 2)))
                    || coord.equals(new ArrayList<>(Arrays.asList(7, 6)));
        }
        return false;
    }

    public void castleWhiteLong(Board board) {
        Rook rook = (Rook) board.getBoard().get(0).get(0);
        board.getBoard().get(0).set(0, null);
        board.getBoard().get(0).set(3, rook);
    }

    public void castleWhiteShort(Board board) {
        Rook rook = (Rook) board.getBoard().get(0).get(7);
        board.getBoard().get(0).set(7, null);
        board.getBoard().get(0).set(5, rook);
    }

    public void castleBlackLong(Board board){
        Rook rook = (Rook) board.getBoard().get(7).get(0);
        board.getBoard().get(7).set(0, null);
        board.getBoard().get(7).set(3, rook);
    }

    public void castleBlackShort(Board board) {
        Rook rook = (Rook) board.getBoard().get(7).get(7);
        board.getBoard().get(7).set(7, null);
        board.getBoard().get(7).set(5, rook);
    }

    public ArrayList<ArrayList<Integer>> checkMove(Board board, Piece piece, ArrayList<ArrayList<Integer>> legalMoves, boolean checkIfKingIsInCheck, int x, int y, ArrayList<Integer> move) {
        int newX = move.get(0);
        int newY = move.get(1);

        Runnable check = () -> {
            if (checkIfKingIsInCheck) {
                if (!isKingInCheck(board, piece.isWhite) && !isOccupiedBySameColor(board, move, piece.isWhite)) {
                    Piece capturedPiece = board.movePiece(piece, newX, newY);
                    legalMoves.add(move);
                    board.undoMove(piece, capturedPiece, x, y, newX, newY);
                } else if (!isOccupiedBySameColor(board, move, piece.isWhite)) {
                    Piece capturedPiece = board.movePiece(piece, newX, newY);
                    if (!isKingInCheck(board, piece.isWhite)) {
                        legalMoves.add(move);
                    }
                    board.undoMove(piece, capturedPiece, x, y, newX, newY);
                }
            } else {
                if (!isOccupiedBySameColor(board, move, piece.isWhite)) {
                    Piece capturedPiece = board.movePiece(piece, newX, newY);
                    legalMoves.add(move);
                    board.undoMove(piece, capturedPiece, x, y, newX, newY);
                }
            }
        };

        if (isWithinBounds(move)) {
            if (isCastleMove(piece, move)) {
                if ((move.get(1) > piece.currentYPosition && canShortCastle(board, piece))
                || (move.get(1) < piece.currentYPosition && canLongCastle(board, piece))) {
                    check.run();
                }
            } else {
                check.run();
            }

        }
        return legalMoves;
    }

    public void toggleCanCastle(Piece king){
        if (king.directions.size() == 10){
            king.directions.remove(9
            );
            king.directions.remove(8);
        }
    }

    public boolean isMovePuttingOwnKingInCheck(Board board, Piece piece, ArrayList<Integer> move) {
        int oldX = piece.currentXPosition;
        int oldY = piece.currentYPosition;
        int newX = move.get(0);
        int newY = move.get(1);

        Piece capturedPiece = board.getPieceAt(newX, newY);
        board.setPieceAt(piece, newX, newY); // Temporarily move the piece to see if it puts own king in check

        boolean ownKingInCheck = isKingInCheck(board, piece.isWhite);

        piece.currentXPosition = oldX;
        piece.currentYPosition = oldY;
        board.undoMove(piece, capturedPiece, oldX, oldY, newX, newY); // Undo the temporary move

        return ownKingInCheck;
    }

    public Piece movePiece(Board board, Piece piece, ArrayList<Integer> move) {
        int currentX = piece.currentXPosition;
        int currentY = piece.currentYPosition;
        Piece capturedPiece = board.getPieceAt(move.get(0), move.get(1));
        if (capturedPiece != null) {
            ArrayList<Piece> enemyPieces = capturedPiece.isWhite ? board.getWhitePieces() : board.getBlackPieces();
            enemyPieces.remove(capturedPiece);
        }
        board.setPieceAt(null, currentX, currentY);
        if (piece.getClass() == Pawn.class && ((piece.isWhite && move.get(0) == 7) || (!piece.isWhite && move.get(0) == 0))) {
            promotePawn(board, piece, move.get(0), move.get(1));
        } else {
            checkAndDoCastle(piece, board, move);
            piece.currentXPosition = move.get(0);
            piece.currentYPosition = move.get(1);
            board.setPieceAt(piece, move.get(0), move.get(1));
        }
        piece.isFirstMove = false;
        return capturedPiece;
    }

    public void promotePawn(Board board, Piece pawn, int x, int y) {
        if (pawn.getClass() == Pawn.class && (x == 7 || x == 0)) {
            Queen newQueen = new Queen(pawn.isWhite, x, y);
            board.setPieceAt(newQueen, x, y);
            System.out.println("Pawn has been promoted to a Queen!");
        }
    }

    public boolean isGameOver(Board board) {
        boolean isWhiteTurn = board.getPlayer();
        Piece king = board.findKing(isWhiteTurn);
        return isKingInCheck(board, isWhiteTurn) ? isInCheckmate(board, king) : isStalemate(board);
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
        ArrayList<Piece> pieces = new ArrayList<>(isWhiteTurn ? board.getWhitePieces() : board.getBlackPieces());
        for (Piece piece : pieces) {
            ArrayList<ArrayList<Integer>> legalMoves = getLegalMoves(board, piece, true);
            if (!legalMoves.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public int[] getInputFromUser() {
        int[] coordinates = new int[4];
        System.out.println("Enter the chess positions (e.g., a3 a5):");
        String input = scanner.nextLine();

        if (!input.equals("")) {
            if (input.equals("lm")) {
                coordinates[1] = -1;
                return coordinates;
            }
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
        } else {
            coordinates[0] = -1;
        }
        return coordinates;
    }



    public void checkForKingAlreadyMoved(Board board, Piece piece) {
        if (piece.getClass() == King.class){
            if (!piece.isFirstMove){
                if (piece.directions.size() == 10){
                    piece.directions.remove(9);
                    piece.directions.remove(8);
                }
            }
        }

    }

    public void takeTurn(Board board, boolean isWhite) {
        System.out.println(board);
        System.out.println("Which piece would you like to move?");
        int[] coordinates = getInputFromUser();
        if(coordinates[0] == -1) {
            System.out.println("No input made!");
            takeTurn(board, isWhite);
        }
        if (coordinates[1] != -1) {

            int oldX = coordinates[0];
            int oldY = coordinates[1];
            int newX = coordinates[2];
            int newY = coordinates[3];

            Piece piece = board.getPieceAt(oldX, oldY);

            if (piece != null && piece.isWhite == isWhite) {
                ArrayList<ArrayList<Integer>> legalMoves = getLegalMoves(board, piece, true);
                ArrayList<Integer> move = new ArrayList<>();
                move.add(newX);
                move.add(newY);
                //checkForKingAlreadyMoved(board, isWhite);
                if (legalMoves.contains(move) && !isMovePuttingOwnKingInCheck(board, piece, move)) {
                    System.out.println(board);
                    Piece capturedPiece = movePiece(board, piece, move);  // This now handles all move logic
                    if (capturedPiece != null) {
                        System.out.println("Captured a piece!");

                    }
                    System.out.println("Turn ended!");
                    board.changeTurns();
                } else {
                    System.out.println("Illegal move or puts your own king in check. Please select a new move.");
                    takeTurn(board, isWhite);
                }
            } else if (piece == null) {
                System.out.println("That space is empty. Please select a piece to move.");
                takeTurn(board, isWhite);
            } else {
                System.out.println("Move your own piece. Please select a new move.");
                takeTurn(board, isWhite);
            }
        } else {
            System.out.println("Is In Check: " + isKingInCheck(board, board.getPlayer()));
            ArrayList<Piece> pieces = isWhite ? new ArrayList<>(board.getWhitePieces()) : new ArrayList<>(board.getBlackPieces());
            for (Piece piece : pieces) {
                ArrayList<ArrayList<Integer>> legalMoves = getLegalMoves(board, piece, true);
                for (ArrayList<Integer> move : legalMoves) {
                    char oldFile = (char) ('a' + piece.currentYPosition);
                    int oldRank = piece.currentXPosition + 1;
                    char file = (char) ('a' + move.get(1));
                    int rank = move.get(0)+1;
                    System.out.println(piece + ": " + oldFile + oldRank + " " + file + rank);
                }
            }
            takeTurn(board, isWhite);
        }
    }
}