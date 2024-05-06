package AI;

import Board.Board;
import Mechanics.Move;
import Pieces.Pawn;
import Pieces.Piece;

import java.util.ArrayList;

public class AI {
    public boolean isWhite;
    Move move = new Move();

    public AI(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public double[] minimax(Board board, int depth, double alpha, double beta, boolean isMaximizingPlayer, int initialDepth) {
        if (depth == 0 || move.isGameOver(board)) {
            return new double[]{evaluate(board), -1, -1, -1, -1};
        }

        ArrayList<Piece> pieces = isMaximizingPlayer ? new ArrayList<>(board.getBlackPieces()) : new ArrayList<>(board.getWhitePieces());
        double bestEval = isMaximizingPlayer ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        int bestPieceRow = -1;
        int bestPieceCol = -1;
        int bestMoveRow = -1;
        int bestMoveCol = -1;

        for (Piece piece : pieces) {
            System.out.println("Piece: " + piece);
            ArrayList<ArrayList<Integer>> legalMoves = move.getLegalMoves(board, piece, true);
            for (ArrayList<Integer> singleMove : legalMoves) {
                System.out.println("Move: (" + singleMove.get(0) + ", " + singleMove.get(1) + ")");
                int startX = piece.currentXPosition; // Store the original X
                int startY = piece.currentYPosition; // Store the original Y
                int endX = singleMove.get(0);        // Destination X
                int endY = singleMove.get(1);        // Destination Y

                // Check for and record a capture
                Piece capturedPiece = board.getPieceAt(endX, endY);

                // Execute move
                executeMove(board, piece, singleMove);
                System.out.println(board);

                // Recursive call
                double eval = minimax(board, depth - 1, alpha, beta, !isMaximizingPlayer, initialDepth)[0];

                // Undo move
                undoMove(board, piece, startX, startY, endX, endY, capturedPiece);

                // Update best move if necessary
                if ((isMaximizingPlayer && eval > bestEval) || (!isMaximizingPlayer && eval < bestEval)) {
                    bestEval = eval;
                    if (depth == initialDepth) {
                        bestPieceRow = startX;  // Return to the starting position
                        bestPieceCol = startY;  // Return to the starting position
                        bestMoveRow = endX;     // Move to destination
                        bestMoveCol = endY;     // Move to destination
                    }
                }

                // Alpha-beta pruning
                if (isMaximizingPlayer) {
                    alpha = Math.max(alpha, eval);
                } else {
                    beta = Math.min(beta, eval);
                }
                if (beta <= alpha) {
                    break; // Prune the remaining branches
                }
            }
            if (beta <= alpha) {
                break;
            }
        }
        return new double[]{bestEval, bestPieceRow, bestPieceCol, bestMoveRow, bestMoveCol};
    }


    private void executeMove(Board board, Piece piece, ArrayList<Integer> move) {
        int startX = piece.currentXPosition;
        int startY = piece.currentYPosition;
        int endX = move.get(0);
        int endY = move.get(1);

        // Check if there's a capture
        Piece capturedPiece = board.getPieceAt(endX, endY);
        System.out.println("Piece taken: " + capturedPiece);
        if (capturedPiece != null) {
            board.removePiece(capturedPiece);
        }

        // Move the piece on the board
        board.setPieceAt(null, startX, startY);  // Remove piece from the old location
        board.setPieceAt(piece, endX, endY);     // Place piece at the new location
    }

    private void undoMove(Board board, Piece piece, int startX, int startY, int endX, int endY, Piece capturedPiece) {
        // Move the piece back to its original position
        board.setPieceAt(null, endX, endY);  // Clear the end position
        board.setPieceAt(piece, startX, startY);  // Restore piece to its original position

        // Restore the captured piece if there was one
        if (capturedPiece != null) {
            board.setPieceAt(capturedPiece, endX, endY);
        }
    }

//

    public double evaluate(Board board) {
        double score = 0;

        // Example heuristic: Count material advantage (difference in piece values)
        ArrayList<Piece> whitePieces = board.getWhitePieces();
        ArrayList<Piece> blackPieces = board.getBlackPieces();
        double whiteScore = calculateMaterialScore(whitePieces);
        double blackScore = calculateMaterialScore(blackPieces);
        double materialScore = whiteScore - blackScore;

        // Example: Add the material score to the total score
        score += materialScore;

        //TODO later
        // Add more evaluation criteria as needed, such as piece positioning, king safety, etc.

        return score;
    }

    // Helper method to calculate material score
    private double calculateMaterialScore(ArrayList<Piece> pieces) {
        double score = 0;
        for (Piece piece : pieces) {
            score += piece.getHeuristicValue();
        }
        return score;
    }

    private void executeBestMove(Board board, int startX, int startY, int endX, int endY) {
        Piece piece = board.getPieceAt(startX, startY);
        System.out.println("Best Piece: (" + startX + ", " + startY + ")");
        System.out.println("Best Move: (" + endX + ", " + endY + ")");
        if (piece != null && piece.isWhite == isWhite) {
            Piece capturedPiece = board.getPieceAt(endX, endY);
            if (capturedPiece != null && capturedPiece.isWhite != piece.isWhite) {  // Ensure capturing an opponent's piece
                board.removePiece(capturedPiece);  // Remove from the board
            }
            ArrayList<Integer> move = new ArrayList<>();
            move.add(endX);
            move.add(endY);
            this.move.movePiece(board, piece, move);
        }
    }

    public void aiMove(Board board) {
        int initialDepth = 1;
        double[] result = minimax(board, initialDepth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true, initialDepth);
        double bestMoveScore = result[0];
        int bestPieceRow = (int) result[1];
        int bestPieceCol = (int) result[2];
        int bestMoveRow = (int) result[3];
        int bestMoveCol = (int) result[4];

        System.out.println("Best AI move score: " + bestMoveScore);
        System.out.println("best AI move: (" + bestPieceRow + ", " + bestPieceCol + ") to (" + bestMoveRow + ", " + bestMoveCol + ")");
        System.out.println(board);

        // Perform the best move found by the minimax algorithm
        if (bestPieceRow != -1 && bestPieceCol != -1 && bestMoveRow != -1 && bestMoveCol != -1) {
            executeBestMove(board, bestPieceRow, bestPieceCol, bestMoveRow, bestMoveCol);
        } else {
            System.out.println("No valid move found!");
        }
    }

}
