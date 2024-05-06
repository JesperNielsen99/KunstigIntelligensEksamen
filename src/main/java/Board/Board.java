package Board;

import Pieces.*;

import java.util.ArrayList;

public class Board {

    ArrayList<ArrayList<Piece>> board = new ArrayList<>();
    ArrayList<Piece> whitePieces = new ArrayList<>();
    ArrayList<Piece> blackPieces = new ArrayList<>();
    boolean whitePlayer = true;

    public Board() {
    }

    public Board(Board board) {
        for (int i = 0; i < board.getBoard().size(); i++) {
            ArrayList<Piece> newRow = new ArrayList<>(); // Create a new row for this.board
            for (int j = 0; j < board.getBoard().get(i).size(); j++) {
                Piece piece = board.getBoard().get(i).get(j);
                newRow.add(piece); // Add each piece from the original board to the new row
            }
            this.board.add(newRow); // Add the new row to this.board
        }
        fillColorArrays();
        this.whitePlayer = board.getPlayer();
    }

    public void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            ArrayList<Piece> row = new ArrayList<>();
            for (int j = 0; j < 8; j++) {
                row.add(null);
            }
            board.add(row);
        }

        // Setup Pawns
        for (int i = 0; i < 8; i++) {
            // Assuming black is at the top (0) and white at the bottom (7)
            // Adjust if your board orientation is different
            board.get(1).set(i, new Pawn(true, 1, i)); // White Pawns
            board.get(6).set(i, new Pawn(false, 6, i)); // Black Pawns
        }




        // Setup Rooks
        board.get(0).set(0, new Rook(true, 0, 0)); // White Rook
        board.get(0).set(7, new Rook(true, 0, 7)); // White Rook
        board.get(7).set(0, new Rook(false, 7, 0)); // Black Rook
        board.get(7).set(7, new Rook(false, 7, 7)); // Black Rook

        // Setup Queen
        board.get(0).set(3, new Queen(true, 0, 3)); // White
        board.get(7).set(3, new Queen(false, 7, 3)); // Black

        //Setup Bishop
        board.get(0).set(2, new Bishop(true, 0, 2)); // White
        board.get(0).set(5, new Bishop(true, 0, 5)); // White
        board.get(7).set(2, new Bishop(false, 7, 2)); // Black
        board.get(7).set(5, new Bishop(false, 7, 5)); // Black

        //Setup Knight (N)
        board.get(0).set(1, new Knight(true, 0, 1)); // White
        board.get(0).set(6, new Knight(true, 0, 6)); // White
        board.get(7).set(1, new Knight(false, 7, 1)); // Black
        board.get(7).set(6, new Knight(false, 7, 6)); // Black

        //Setup King
        board.get(0).set(4, new King(true, 0, 4)); // White

        board.get(7).set(4, new King(false, 7, 4)); // Black
        fillColorArrays();
    }

    public ArrayList<ArrayList<Piece>> getBoard() {
        return board;
    }

    public ArrayList<Piece> getBlackPieces() {
        return blackPieces;
    }

    public ArrayList<Piece> getWhitePieces() {
        return whitePieces;
    }

    public Piece findKing(boolean isWhite) {
        if (isWhite) {
            for (Piece piece : whitePieces) {
                if (piece.getClass() == King.class) {
                    return piece;
                }
            }
        } else {
            for (Piece piece : blackPieces) {
                if (piece.getClass() == King.class) {
                    return piece;
                }
            }
        }
        return null;
    }

    public void changeTurns() {
        whitePlayer = !whitePlayer;
    }

    public boolean getPlayer() {
        return whitePlayer;
    }

    //public void updatePiece() {}

    private void fillColorArrays() {
        for (ArrayList<Piece> row : board) {
            for (Piece piece : row) {
                if (piece != null) {
                    if (piece.isWhite) {
                        whitePieces.add(piece);
                    } else {
                        blackPieces.add(piece);
                    }
                }
            }
        }
    }

    //    <<<<<<<<<<<<< NYT >>>>>>>>>>>>>>>>>>>>>>>>>
    public Piece getPieceAt(int x, int y) {
        // Check if coordinates are out of bounds
        if (x < 0 || x >= 8 || y < 0 || y >= 8) {
            return null; // Return null if out of bounds
        }
        return board.get(x).get(y); // Return the piece at the specified location
    }

    public void removePiece(Piece piece) {
        if (piece == null) {
            return; // If there's no piece, there's nothing to remove
        }
        // Get the current coordinates of the piece
        int x = piece.currentXPosition;
        int y = piece.currentYPosition;

        // Set the board position to null to remove the piece
        board.get(x).set(y, null);

        // Also remove the piece from the respective list of pieces
        if (piece.isWhite) {
            whitePieces.remove(piece);
        } else {
            blackPieces.remove(piece);
        }
    }

    public void setPieceAt(Piece piece, int x, int y) {
        // Check if the coordinates are out of bounds
        if (x < 0 || x >= 8 || y < 0 || y >= 8) {
            throw new IndexOutOfBoundsException("Coordinates are out of the board's bounds.");
        }

        // Get the current piece at the location (if any)
        Piece currentPiece = getPieceAt(x, y);

        // If there is a piece currently at the location, remove it
        if (currentPiece != null) {
            removePiece(currentPiece);
        }

        // Place the new piece at the specified location
        board.get(x).set(y, piece);

        // Update the piece's position if it is not null
        if (piece != null) {
            piece.currentXPosition = x;
            piece.currentYPosition = y;

            // Add the piece to the correct list of pieces (white or black)
            if (piece.isWhite) {
                if (!whitePieces.contains(piece)) {
                    whitePieces.add(piece);
                }
            } else {
                if (!blackPieces.contains(piece)) {
                    blackPieces.add(piece);
                }
            }
        }
    }


//    <<<<<<<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    public String toString() {
        String boardString = "------------------\n";
        for (int i = 0; i < 8; i++) {
            boardString += 8-i + "|";
            for (int j = 0; j < 8; j++) {
                if (board.get(8-i-1).get(j) == null) {
                    boardString += " |";
                } else {
                    boardString += board.get(8-i-1).get(j).toString() + "|";
                }
            }
            boardString += "\n------------------\n";
        }
        boardString += "  a b c d e f g h";
        return boardString;
    }


}