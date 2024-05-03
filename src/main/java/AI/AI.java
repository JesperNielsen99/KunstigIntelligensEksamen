package AI;

import Board.Board;
import Mechanics.Move;
import Pieces.Piece;

import java.util.ArrayList;

public class AI {
    public boolean isWhite;
    Move move = new Move();

    public AI(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public double[] minimax(Board board, int depth, double alpha, double beta, boolean isMaximizingPlayer, int initialDepth) {
        if (depth <= 0 || move.isGameOver(board)) {
            return new double[]{evaluate(board), -1, -1, -1, -1};
        }

        ArrayList<Piece> pieces = isMaximizingPlayer ? board.getBlackPieces() : board.getWhitePieces();
        double bestEval = isMaximizingPlayer ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        int bestPieceRow = -1;
        int bestPieceCol = -1;
        int bestMoveRow = -1;
        int bestMoveCol = -1;

        for (Piece piece : pieces) {
            System.out.println("Piece: " + piece);
            ArrayList<ArrayList<Integer>> legalMoves = move.getLegalMoves(board, piece, true);
            System.out.println("Legal moves for " + piece + ": " + legalMoves);
            for (ArrayList<Integer> move : legalMoves) {
                Board tempBoard = new Board(board); // Assuming you have a way to clone or copy the board
                int currentX = piece.currentXPosition;
                int currentY = piece.currentYPosition;
                this.move.movePiece(tempBoard, piece, move);
                System.out.println(tempBoard);
                double eval = minimax(tempBoard, (depth-1), alpha, beta, !isMaximizingPlayer, initialDepth)[0];
                System.out.println("Move: " + move + ", Eval: " + eval);
                if ((isMaximizingPlayer && eval > bestEval) || (!isMaximizingPlayer && eval < bestEval)) {
                    bestEval = eval;
                    System.out.println("Best Eval: " + bestEval);
                    System.out.println("Piece: " + board.getBoard().get(currentX).get(currentY));
                    if (depth == initialDepth) {
                        System.out.println("depth = init");
                        bestPieceRow = currentX; // Update best piece row
                        bestPieceCol = currentY; // Update best piece column
                        bestMoveRow = move.get(0); // Update best move row
                        bestMoveCol = move.get(1); // Update best move column
                    }
                }
                if (isMaximizingPlayer) {
                    alpha = Math.max(alpha, bestEval);
                } else {
                    beta = Math.min(beta, bestEval);
                }
                if (beta <= alpha) {
                    break;
                }
            }
            if (beta <= alpha) {
                break;
            }
        }
        return new double[]{bestEval, bestPieceRow, bestPieceCol, bestMoveRow, bestMoveCol};
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
        int initialDepth = 1;
        double[] result = minimax(board, initialDepth, Integer.MIN_VALUE, Integer.MAX_VALUE, true, initialDepth);
        double bestMoveScore = result[0];
        int bestPieceRow = (int) result[1];
        int bestPieceCol = (int) result[2];
        int bestMoveRow = (int) result[3];
        int bestMoveCol = (int) result[4];

        System.out.println("Best AI move score: " + bestMoveScore);
        System.out.println("best AI move: (" + bestPieceRow + ", " + bestPieceCol + ") to (" + bestMoveRow + ", " + bestMoveCol + ")");
        System.out.println(board);

        // Perform the best move found by the minimax algorithm
        // Assuming you have a method to make a move on the board
        Piece piece = board.getBoard().get(bestPieceRow).get(bestPieceCol);
        ArrayList<Integer> move = new ArrayList<>();
        move.add(bestMoveRow);
        move.add(bestMoveCol);
        this.move.movePiece(board, piece, move);
    }
}