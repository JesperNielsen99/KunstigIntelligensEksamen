package AI;

import Board.Board;
import Mechanics.Move;
import Pieces.Piece;

import java.util.ArrayList;

public class AI {
    boolean isWhite;
    Move move = new Move();

    public AI(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public double[] minimax(Board board, int depth, double alpha, double beta, boolean isMaximizingPlayer) {
        if (depth <= 0 || move.isGameOver(board)) {
            return new double[]{evaluate(board), -1, -1};
        }

        if (isMaximizingPlayer) {
            double maxEval = Double.NEGATIVE_INFINITY;
            // Variables to track the best move found
            int bestMoveRow = -1;
            int bestMoveCol = -1;

            for (Piece piece : board.getBlackPieces()) {
                ArrayList<ArrayList<Integer>> legalMoves = move.getLegalMoves(board, piece, true);
                for (ArrayList<Integer> move : legalMoves) {
                    Board tempBoard = new Board(board); // Assuming you have a way to clone or copy the board
                    this.move.movePiece(board, piece, move);
                    double[] result = minimax(tempBoard, depth - 1, alpha, beta, false);
                    double eval = result[0];
                    if (eval > maxEval) {
                        maxEval = eval;
                        bestMoveRow = move.get(0); // Update best move row
                        bestMoveCol = move.get(1); // Update best move column
                    }
                    alpha = Math.max(alpha, result[0]);
                    if (beta <= alpha) {
                        break;
                    }
                }
                if (beta <= alpha) {
                    break;
                }
            }
            return new double[]{maxEval, bestMoveRow, bestMoveCol};
        } else {
            double minEval = Double.POSITIVE_INFINITY;
            // Variables to track the best move found
            int bestMoveRow = -1;
            int bestMoveCol = -1;
            for (Piece piece : board.getWhitePieces()) {
                ArrayList<ArrayList<Integer>> legalMoves = move.getLegalMoves(board, piece, true);
                for (ArrayList<Integer> move : legalMoves) {
                    Board tempBoard = new Board(board);
                    this.move.movePiece(board, piece, move);
                    double[] result = minimax(tempBoard, depth - 1, alpha, beta, true);
                    double eval = result[0];
                    if (eval < minEval) {
                        minEval = eval;
                        bestMoveRow = move.get(0); // Update best move row
                        bestMoveCol = move.get(1); // Update best move column
                    }
                    beta = Math.min(beta, result[1]);
                    if (beta <= alpha) {
                        break;
                    }
                }
                if (beta <= alpha) {
                    break;
                }
            }
            return new double[]{minEval, bestMoveRow, bestMoveCol};
        }
    }

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

    public void aiMove(Board board) {
        double[] result = minimax(board, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
        double bestMoveScore = result[0];
        int bestMoveRow = (int) result[1];
        int bestMoveCol = (int) result[2];

        System.out.println("Best AI move score: " + bestMoveScore);
        System.out.println("best AI move: (" + bestMoveRow + ", " + bestMoveCol + ")");

        // Perform the best move found by the minimax algorithm
        // Assuming you have a method to make a move on the board
        Piece piece = board.getBoard().get(bestMoveRow).get(bestMoveCol);
        ArrayList<Integer> move = new ArrayList<>();
        move.add(bestMoveRow);
        move.add(bestMoveCol);
        this.move.movePiece(board, piece, move);
    }
}
