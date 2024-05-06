package AI;

import Board.Board;
import Mechanics.Move;
import Pieces.Piece;

import java.util.ArrayList;

public class AI {
    public boolean isWhite;
    Move move = new Move();
    public Board board;

    public AI(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public double[] minimax(Board board, int depth, double alpha, double beta, boolean isMaximizingPlayer, int initialDepth) {
        if (depth == 0 || move.isGameOver(board)) {
            return new double[]{evaluate(board), -1, -1, -1, -1};
        }

        ArrayList<Piece> pieces = isMaximizingPlayer ? board.getWhitePieces() : board.getBlackPieces();
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

                // Check for and record a capture
                Piece capturedPiece = board.getPieceAt(endX, endY);

                // Execute move
                executeMove(board, piece, singleMove);

                // Recursive call
                double eval = minimax(board, depth - 1, alpha, beta, !isMaximizingPlayer, initialDepth)[0];

                // Undo move
                undoMove(board, piece, startX, startY, endX, endY, capturedPiece);

                // Update best move if necessary
                if ((isMaximizingPlayer && eval > bestEval) || (!isMaximizingPlayer && eval < bestEval)) {
                    bestEval = eval;
                    bestPieceRow = startX;  // Return to the starting position
                    bestPieceCol = startY;  // Return to the starting position
                    bestMoveRow = endX;     // Move to destination
                    bestMoveCol = endY;     // Move to destination
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
        if (capturedPiece != null) {
            board.removePiece(capturedPiece);
        }


        // Move the piece on the board
        board.setPieceAt(null, startX, startY);  // Remove piece from the old location
        board.setPieceAt(piece, endX, endY);     // Place piece at the new location

        // Update the piece's position
        piece.currentXPosition = endX;
        piece.currentYPosition = endY;
    }

    private void undoMove(Board board, Piece piece, int startX, int startY, int endX, int endY, Piece capturedPiece) {
        // Move the piece back to its original position
        board.setPieceAt(null, endX, endY);  // Clear the end position
        board.setPieceAt(piece, startX, startY);  // Restore piece to its original position
        piece.currentXPosition = startX;
        piece.currentYPosition = startY;

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

    public void aiMove() {
        int initialDepth = 1;  // Set this to your desired depth
        double[] result = minimax(board, initialDepth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false, initialDepth);
        double bestMoveScore = result[0];
        int bestPieceRow = (int) result[1];
        int bestPieceCol = (int) result[2];
        int bestMoveRow = (int) result[3];
        int bestMoveCol = (int) result[4];

        System.out.println("Best AI move score: " + bestMoveScore);
        System.out.println("best AI move: (" + bestPieceRow + ", " + bestPieceCol + ") to (" + bestMoveRow + ", " + bestMoveCol + ")");


        // Perform the best move found by the minimax algorithm
        if (bestPieceRow != -1 && bestPieceCol != -1 && bestMoveRow != -1 && bestMoveCol != -1) {
            executeBestMove(bestPieceRow, bestPieceCol, bestMoveRow, bestMoveCol);
            System.out.println("AI executed move from (" + bestPieceRow + ", " + bestPieceCol + ") to (" + bestMoveRow + ", " + bestMoveCol + ") with score: " + bestMoveScore);
        } else {
            System.out.println("AI found no valid move.");
        }
    }

    private void executeBestMove(int startX, int startY, int endX, int endY) {

        Piece piece = board.getPieceAt(startX, startY);
        if (piece != null) {
            ArrayList<Integer> move = new ArrayList<>();
            move.add(endX);
            move.add(endY);
            this.move.movePiece(board, piece, move);
        }
    }


    public void aiMove(Board board) {
        int initialDepth = 1;  // Set this to your desired depth
        double[] result = minimax(board, initialDepth, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, false, initialDepth);
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
            Piece piece = board.getBoard().get(bestPieceRow).get(bestPieceCol);
            ArrayList<Integer> move = new ArrayList<>();
            move.add(bestMoveRow);
            move.add(bestMoveCol);
            this.move.movePiece(board, piece, move);
        } else {
            System.out.println("No valid move found!");
        }
    }

//    public double[] minimax(Board board, int depth, double alpha, double beta, boolean isMaximizingPlayer) {
//        if (depth <= 0 || move.isGameOver(board)) {
//            return new double[]{evaluate(board), -1, -1};
//        }
//        ArrayList<Piece> aiPieces = new ArrayList<>();
//        ArrayList<Piece> opponentPieces = new ArrayList<>();
//        if (isWhite) {
//            aiPieces = board.getWhitePieces();
//            opponentPieces = board.getBlackPieces();
//        } else {
//            aiPieces = board.getBlackPieces();
//            opponentPieces = board.getWhitePieces();
//        }
//
//        double bestEval = isMaximizingPlayer ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
//        // Variables to track the best move found
//        int bestPieceRow = -1;
//        int bestPieceCol = -1;
//        int bestMoveRow = -1;
//        int bestMoveCol = -1;
//
//        for (Piece piece : isMaximizingPlayer ? aiPieces : opponentPieces) {
//            System.out.println("(" + bestPieceRow + ", " + bestPieceCol + ")");
//            if (bestPieceRow >= 0 && bestPieceCol >= 0) {
//                System.out.println(board);
//                System.out.println("Piece: " + board.getBoard().get(bestPieceRow).get(bestPieceCol));
//                System.out.println("Absolute Piece: " + piece);
//            }
//            ArrayList<ArrayList<Integer>> legalMoves = move.getLegalMoves(board, piece, true);
//            for (ArrayList<Integer> move : legalMoves) {
//                Board tempBoard = new Board(board); // Assuming you have a way to clone or copy the board
//                int currentX = piece.currentXPosition;
//                int currentY = piece.currentYPosition;
//                this.move.movePiece(tempBoard, piece, move);
//                double eval = minimax(tempBoard, depth - 1, alpha, beta, !isMaximizingPlayer)[0];
//                if ((isMaximizingPlayer && eval > bestEval) || (!isMaximizingPlayer && eval < bestEval)) {
//                    bestEval = eval;
//                    bestPieceRow = currentX; // Update best piece row
//                    bestPieceCol = currentY; // Update best piece column
//                    bestMoveRow = move.get(0); // Update best move row
//                    bestMoveCol = move.get(1); // Update best move column
//                }
//                if (isMaximizingPlayer) {
//                    alpha = Math.max(alpha, bestEval);
//                } else {
//                    beta = Math.min(beta, bestEval);
//                }
//                if (beta <= alpha) {
//                    break;
//                }
//            }
//            if (beta <= alpha) {
//                break;
//            }
//        }
//        return new double[]{bestEval, bestPieceRow, bestPieceCol, bestMoveRow, bestMoveCol};
//    }


//    public double[] minimax(Board board, int depth, double alpha, double beta, boolean isMaximizingPlayer, int initialDepth) {
//        if (depth == 0 || move.isGameOver(board)) {
//            return new double[]{evaluate(board), -1, -1, -1, -1};
//        }
//
//        ArrayList<Piece> pieces = isMaximizingPlayer ? board.getWhitePieces() : board.getBlackPieces();
//        double bestEval = isMaximizingPlayer ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
//        int bestPieceRow = -1;
//        int bestPieceCol = -1;
//        int bestMoveRow = -1;
//        int bestMoveCol = -1;
//
//        for (Piece piece : pieces) {
//            ArrayList<ArrayList<Integer>> legalMoves = move.getLegalMoves(board, piece, true);
//            for (ArrayList<Integer> move : legalMoves) {
//                Board tempBoard = new Board(board);
//                int currentX = piece.currentXPosition;
//                int currentY = piece.currentYPosition;
//                this.move.movePiece(tempBoard, piece, move);
//                double[] evalResult = minimax(tempBoard, depth - 1, alpha, beta, !isMaximizingPlayer, initialDepth);
//                double eval = evalResult[0];
//
//                if ((isMaximizingPlayer && eval > bestEval) || (!isMaximizingPlayer && eval < bestEval)) {
//                    bestEval = eval;
//                    // Update the best move only if at the topmost depth of the recursion
//                    if (depth == initialDepth) {
//                        bestPieceRow = currentX;
//                        bestPieceCol = currentY;
//                        bestMoveRow = move.get(0);
//                        bestMoveCol = move.get(1);
//                    }
//                }
//
//                if (isMaximizingPlayer) {
//                    alpha = Math.max(alpha, eval);
//                } else {
//                    beta = Math.min(beta, eval);
//                }
//                if (beta <= alpha) {
//                    break;
//                }
//            }
//            if (beta <= alpha) {
//                break;
//            }
//        }
//        return new double[]{bestEval, bestPieceRow, bestPieceCol, bestMoveRow, bestMoveCol};
//    }






//    public void aiMove(Board board) {
//        double[] result = minimax(board, 3, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
//        double bestMoveScore = result[0];
//        int bestPieceRow = (int) result[1];
//        int bestPieceCol = (int) result[2];
//        int bestMoveRow = (int) result[3];
//        int bestMoveCol = (int) result[4];
//
//        System.out.println("Best AI move score: " + bestMoveScore);
//        System.out.println("best AI move: (" + bestPieceRow + ", " + bestPieceCol + ") to (" + bestMoveRow + ", " + bestMoveCol + ")");
//        System.out.println(board);
//
//        // Perform the best move found by the minimax algorithm
//        // Assuming you have a method to make a move on the board
//        Piece piece = board.getBoard().get(bestPieceRow).get(bestPieceCol);
//        ArrayList<Integer> move = new ArrayList<>();
//        move.add(bestMoveRow);
//        move.add(bestMoveCol);
//        this.move.movePiece(board, piece, move);
//    }
}
