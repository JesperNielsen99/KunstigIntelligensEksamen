package AI;

import Board.Board;
import Mechanics.Move;
import Pieces.King;
import Pieces.Pawn;
import Pieces.Piece;

import java.util.ArrayList;

public class AI {
    public boolean isWhite;
    Move move = new Move();
    int pruning = 0;
    int initialDepth = 5;

    public AI(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public double[] minimax(Board board, int depth, double alpha, double beta, boolean isMaximizingPlayer, int initialDepth) {
        if (depth == 0 || board.findKing(isWhite) == null || board.findKing(!isWhite) == null || move.isGameOver(board)) {
            return new double[]{evaluate(board), -1, -1, -1, -1};
        }

        ArrayList<Piece> pieces = new ArrayList<>();
        if (!isWhite) {
            pieces = isMaximizingPlayer ? new ArrayList<>(board.getBlackPieces()) : new ArrayList<>(board.getWhitePieces());
        } else {
            pieces = isMaximizingPlayer ? new ArrayList<>(board.getWhitePieces()) : new ArrayList<>(board.getBlackPieces());
        }
        double bestEval = isMaximizingPlayer ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        int bestPieceRow = -1;
        int bestPieceCol = -1;
        int bestMoveRow = -1;
        int bestMoveCol = -1;

        for (Piece piece : pieces) {
            ArrayList<ArrayList<Integer>> legalMoves = move.getLegalMoves(board, piece, true);
            for (ArrayList<Integer> singleMove : legalMoves) {
                int startX = piece.currentXPosition; // Store the original X
                int startY = piece.currentYPosition; // Store the original Y
                int endX = singleMove.get(0);        // Destination X
                int endY = singleMove.get(1);        // Destination Y

                // Execute move
                Piece capturedPiece = simulateMove(board, piece, endX, endY);
                pruning++;

                // Recursive call
                double eval = minimax(board, depth - 1, alpha, beta, !isMaximizingPlayer, initialDepth)[0];

                // Undo move
                undoMove(board, piece, capturedPiece, startX, startY, endX, endY);

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


    private Piece simulateMove(Board board, Piece piece, int endX, int endY) {
        return board.movePiece(piece, endX, endY);
    }

    private void undoMove(Board board, Piece piece, Piece capturedPiece, int startX, int startY, int endX, int endY) {
        board.undoMove(piece, capturedPiece, startX, startY, endX, endY);
    }

    public double evaluate(Board board) {
        double score = 0;

        // Example heuristic: Count material advantage (difference in piece values)
        ArrayList<Piece> whitePieces = board.getWhitePieces();
        ArrayList<Piece> blackPieces = board.getBlackPieces();
        double whiteScore = calculateMaterialScore(whitePieces);
        double blackScore = calculateMaterialScore(blackPieces);
        double materialScore = isWhite? (whiteScore-blackScore) : (blackScore-whiteScore);

        // Example: Add the material score to the total score
        score += materialScore;

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
        System.out.println("Pruining: " + pruning);
        Piece piece = board.getPieceAt(startX, startY);
        ArrayList<Integer> move = new ArrayList<>();
        move.add(endX);
        move.add(endY);
        if (piece != null && piece.isWhite == isWhite) {
            this.move.movePiece(board, piece, move);
        }
    }

    public void aiMove(Board board) {
        double[] result = minimax(board, initialDepth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, true, initialDepth);
        double bestMoveScore = result[0];
        int bestPieceRow = (int) result[1];
        int bestPieceCol = (int) result[2];
        int bestMoveRow = (int) result[3];
        int bestMoveCol = (int) result[4];

        char oldFile = (char) ('a' + bestPieceCol);
        int oldRank = bestPieceRow + 1;
        char file = (char) ('a' + bestMoveCol);
        int rank = bestMoveRow+1;
        System.out.println("Best AI move score: " + bestMoveScore);
        System.out.println("Best AI move: " + oldFile + oldRank +  ", " + file + rank);

        if (bestPieceRow != -1 && bestPieceCol != -1 && bestMoveRow != -1 && bestMoveCol != -1) {
            executeBestMove(board, bestPieceRow, bestPieceCol, bestMoveRow, bestMoveCol);
            board.changeTurns();
        } else {
            System.out.println("No valid move found!");
        }
    }
}
