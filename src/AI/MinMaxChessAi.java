package AI;

import Pieces.ChessPiece;
import Pieces.PieceColor;
import Pieces.PieceType;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MinMaxChessAi {
    // This class is responsible for the AI logic. It will use the MinMax algorithm to determine the best move for the AI player.
    // The MinMax algorithm is a decision-making algorithm commonly used in two-player games. It evaluates all possible moves and their outcomes to determine the best move for the AI player.
    // The MinMax algorithm is based on the principle of minimizing the maximum possible loss. It works by recursively evaluating all possible moves and their outcomes, and choosing the move that minimizes the maximum possible loss for the AI player.

    private double[] parameters;

    public MinMaxChessAi() {
        // Initialize the AI with default parameters
        initializeAI(Difficulty.MEDIUM);
    }

    public void initializeAI(Difficulty difficulty) {
        // Initialize the AI based on the chosen difficulty
        switch (difficulty) {
            case EASY:
                parameters = new double[]{0.5, 0.3, 0.2}; // Example parameters for easy difficulty
                break;
            case MEDIUM:
                parameters = new double[]{0.4, 0.4, 0.2}; // Example parameters for medium difficulty
                break;
            case HARD:
                parameters = new double[]{0.3, 0.5, 0.2}; // Example parameters for hard difficulty
                break;
        }


    }

    //minimax algorithm with basic pruning
    public int minimax(int depth, int nodeIndex, boolean isMax, int []scores, int h) {
        if (depth == h) {
            return scores[nodeIndex];

        }

        // if current move is maximizer, find the maximum attainable value
        if (isMax){
            return max(minimax(depth+1, nodeIndex*2, false, scores, h), minimax(depth+1, nodeIndex*2 + 1, false, scores, h));
        }

        // else if is minimizer, find the minimum attainable value
        else{
            return min(minimax(depth+1, nodeIndex*2, true, scores, h), minimax(depth+1, nodeIndex*2 + 1, true, scores, h));
        }

    }


    //evaluation function
    public int evaluateBoard(ChessPiece[][] board) {
        int eval = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null) {

                    switch (piece.getType()) {
                        case PAWN:
                            eval += piece.getColor() == PieceColor.WHITE ? 10 : -10;
                            break;
                        case KNIGHT:
                            eval += piece.getColor() == PieceColor.WHITE ? 30 : -30;
                            break;
                        case BISHOP:
                            eval += piece.getColor() == PieceColor.WHITE ? 30 : -30;
                            break;
                        case ROOK:
                            eval += piece.getColor() == PieceColor.WHITE ? 50 : -50;
                            break;
                        case QUEEN:
                            eval += piece.getColor() == PieceColor.WHITE ? 90 : -90;
                            break;
                        case KING:
                            eval += piece.getColor() == PieceColor.WHITE ? 1000 : -1000;
                            break;
                    }
                }
            }
        }
        return eval;
    }

    //chess piece weights
    //(white pieces)pawn = 10, knight = 30, bishop = 30, rook = 50, queen = 90, king = 1000
    //(black pieces)pawn = -10, knight = -30, bishop = -30, rook = -50, queen = -90, king = -1000

    // Method to generate all possible moves for a given player on the current board
    public List<Move> generateMoves(ChessPiece[][] board, PieceColor playerColor) {
        List<Move> moves = new ArrayList<>();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null && piece.getColor() == playerColor) {
                    switch (piece.getType()) {
                        case PAWN:
                            // Generate all possible moves for the pawn
                            generatePawnMoves(board, moves, piece);
                            break;
                        case KNIGHT:
                            // Generate all possible moves for the knight
                            generateKnightMoves(board, moves, piece);
                            break;
                        case BISHOP:
                            // Generate all possible moves for the bishop
                            generateBishopMoves(board, moves, piece);
                            break;
                        case ROOK:
                            // Generate all possible moves for the rook
                            generateRookMoves(board, moves, piece);
                            break;
                        case QUEEN:
                            // Generate all possible moves for the queen
                            generateQueenMoves(board, moves, piece);
                            break;
                        case KING:
                            // Generate all possible moves for the king
                            generateKingMoves(board, moves, piece);
                            break;
                    }
                }
            }
        }
        return moves;
    }

    // Minimax algorithm with alpha-beta pruning
    public Move minimax(ChessPiece[][] board, int depth, boolean maximizingPlayer, double alpha, double beta) {
        if (depth == 0 || isGameOver(board)) {
            // Return the evaluation of the current board state if reached maximum depth or game over
            return new Move(null, null, evaluateBoard(board));
        }

        List<Move> possibleMoves = generateMoves(board, maximizingPlayer ? PieceColor.WHITE : PieceColor.BLACK);

        Move bestMove = null;
        if (maximizingPlayer) {
            double maxEval = Double.NEGATIVE_INFINITY;
            for (Move move : possibleMoves) {
                ChessPiece[][] newBoard = applyMove(board, move);
                double eval = minimax(newBoard, depth - 1, false, alpha, beta).getScore();
                if (eval > maxEval) {
                    maxEval = eval;
                    bestMove = move;
                }
                alpha = max(alpha, eval);
                if (beta <= alpha) {
                    break; // Beta cutoff
                }
            }
            return new Move(bestMove.getStart(), bestMove.getEnd(), maxEval);
        } else {
            double minEval = Double.POSITIVE_INFINITY;
            for (Move move : possibleMoves) {
                ChessPiece[][] newBoard = applyMove(board, move);
                double eval = minimax(newBoard, depth - 1, true, alpha, beta).getScore();
                if (eval < minEval) {
                    minEval = eval;
                    bestMove = move;
                }
                beta = min(beta, eval);
                if (beta <= alpha) {
                    break; // Alpha cutoff
                }
            }
            return new Move(bestMove.getStart(), bestMove.getEnd(), minEval);
        }
    }

    private boolean isGameOver(ChessPiece[][] board) {
        // Check if the game is over (e.g., checkmate, stalemate, etc.)
        return false;
    }


    //minimax algorithm

    // Method to make a move for the AI player
    private void generatePawnMoves(ChessPiece[][] board, List<Move> moves, ChessPiece pawn) {
        int row = pawn.getRow();
        int col = pawn.getCol();
        int direction = (pawn.getColor() == PieceColor.WHITE) ? 1 : -1;

        // Check if the pawn can move forward one square
        if (board[row + direction][col] == null) {
            moves.add(new Move(new Position(row, col), new Position(row + direction, col), 0));

            // Check if the pawn can move forward two squares from its starting position
            if ((row == 1 && direction == 1) || (row == 6 && direction == -1)) {
                if (board[row + 2 * direction][col] == null) {
                    moves.add(new Move(new Position(row, col), new Position(row + 2 * direction, col), 0));
                }
            }
        }

        // Check if the pawn can capture diagonally
        if (col > 0 && board[row + direction][col - 1] != null && board[row + direction][col - 1].getColor() != pawn.getColor()) {
            moves.add(new Move(new Position(row, col), new Position(row + direction, col - 1), 0));
        }
        if (col < 7 && board[row + direction][col + 1] != null && board[row + direction][col + 1].getColor() != pawn.getColor()) {
            moves.add(new Move(new Position(row, col), new Position(row + direction, col + 1), 0));
        }

        // Check if the pawn can perform en passant
        if (col > 0 && board[row][col - 1] != null && board[row][col - 1].getType() == PieceType.PAWN &&
                board[row][col - 1].getColor() != pawn.getColor() &&
                ((row == 4 && direction == -1) || (row == 3 && direction == 1))) {
            moves.add(new Move(new Position(row, col), new Position(row + direction, col - 1), 0));
        }
        if (col < 7 && board[row][col + 1] != null && board[row][col + 1].getType() == PieceType.PAWN &&
                board[row][col + 1].getColor() != pawn.getColor() &&
                ((row == 4 && direction == -1) || (row == 3 && direction == 1))) {
            moves.add(new Move(new Position(row, col), new Position(row + direction, col + 1), 0));
        }

        // Check if the pawn can promote
        if ((row == 6 && direction == -1) || (row == 1 && direction == 1)) {
            // Add promotion moves to queen, rook, bishop, and knight
            moves.add(new Move(new Position(row, col), new Position(row + direction, col), 0));
            moves.add(new Move(new Position(row, col), new Position(row + direction, col), 0));
            moves.add(new Move(new Position(row, col), new Position(row + direction, col), 0));
            moves.add(new Move(new Position(row, col), new Position(row + direction, col), 0));
        }
    }

    private void generateKnightMoves(ChessPiece[][] board, List<Move> moves, ChessPiece knight) {
        int[][] possibleMoves = {
                {-2, -1}, {-2, 1},   // Two squares up, one square left/right
                {-1, -2}, {-1, 2},   // One square up, two squares left/right
                {1, -2}, {1, 2},     // One square down, two squares left/right
                {2, -1}, {2, 1}      // Two squares down, one square left/right
        };

        for (int[] move : possibleMoves) {
            int newRow = knight.getRow() + move[0];
            int newCol = knight.getCol() + move[1];

            if (isValidMove(newRow, newCol) && (board[newRow][newCol] == null || board[newRow][newCol].getColor() != knight.getColor())) {
                moves.add(new Move(new Position(knight.getRow(), knight.getCol()), new Position(newRow, newCol), 0));
            }
        }
    }

    private boolean isValidMove(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    private void generateBishopMoves(ChessPiece[][] board, List<Move> moves, ChessPiece bishop) {
        int row = bishop.getRow();
        int col = bishop.getCol();

        // Define the directions in which a bishop can move diagonally
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        // Iterate over each direction
        for (int[] dir : directions) {
            int dr = dir[0];
            int dc = dir[1];
            int r = row + dr;
            int c = col + dc;

            // Continue moving diagonally until the edge of the board is reached or an obstacle is encountered
            while (r >= 0 && r < 8 && c >= 0 && c < 8) {
                if (board[r][c] == null) {
                    // If the square is empty, add a regular move
                    moves.add(new Move(new Position(row, col), new Position(r, c), 0));
                } else {
                    // If there's a piece on the square, check if it's an opponent's piece to capture
                    if (board[r][c].getColor() != bishop.getColor()) {
                        moves.add(new Move(new Position(row, col), new Position(r, c), 0));
                    }
                    break; // Bishop can't move beyond a capturing position
                }
                // Move diagonally further
                r += dr;
                c += dc;
            }
        }
    }

    private void generateRookMoves(ChessPiece[][] board, List<Move> moves, ChessPiece rook) {
        int row = rook.getRow();
        int col = rook.getCol();
        PieceColor color = rook.getColor();

        // Check moves in all four directions: up, down, left, right
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int dRow = dir[0];
            int dCol = dir[1];

            int newRow = row + dRow;
            int newCol = col + dCol;

            while (isValidMove(newRow, newCol)) {
                if (board[newRow][newCol] == null) {
                    // If the target square is empty, add a regular move
                    moves.add(new Move(new Position(row, col), new Position(newRow, newCol), 0));
                } else if (board[newRow][newCol].getColor() != color) {
                    // If the target square has opponent's piece, add a capture move and stop in this direction
                    moves.add(new Move(new Position(row, col), new Position(newRow, newCol), 0));
                    break;
                } else {
                    // If the target square has own piece, stop in this direction
                    break;
                }

                // Move to the next square in the same direction
                newRow += dRow;
                newCol += dCol;
            }
        }
    }

    private void generateQueenMoves(ChessPiece[][] board, List<Move> moves, ChessPiece queen) {
        // Implementation for generating queen moves
        // Generate all possible moves for the queen, which combines the moves of rooks and bishops
        generateRookMoves(board, moves, queen); // Generate rook-like moves
        generateBishopMoves(board, moves, queen); // Generate bishop-like moves
    }

    private void generateKingMoves(ChessPiece[][] board, List<Move> moves, ChessPiece king) {
        int[][] directions = {{1, 0}, {1, 1}, {0, 1}, {-1, 1}, {-1, 0}, {-1, -1}, {0, -1}, {1, -1}};
        int row = king.getRow();
        int col = king.getCol();

        // Generate regular king moves
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];
            if (isValidMove(newRow, newCol) && (board[newRow][newCol] == null || board[newRow][newCol].getColor() != king.getColor())) {
                moves.add(new Move(new Position(row, col), new Position(newRow, newCol), 0));
            }
        }

        // Generate castling moves
        // Implement castling logic here
    }

    public ChessPiece[][] applyMove(ChessPiece[][] board, Move move) {
        // Create a copy of the board to avoid modifying the original board
        ChessPiece[][] newBoard = deepCopyBoard(board);

        // Get the start and end positions of the move
        Position start = move.getStart();
        Position end = move.getEnd();

        // Get the piece being moved
        ChessPiece piece = newBoard[start.getRow()][start.getCol()];

        // Move the piece to the end position
        newBoard[end.getRow()][end.getCol()] = piece;
        newBoard[start.getRow()][start.getCol()] = null;

        // Update the piece's position
        piece.setRow(end.getRow());
        piece.setCol(end.getCol());

        return newBoard;
    }

    // Helper method to create a deep copy of the chessboard
    private ChessPiece[][] deepCopyBoard(ChessPiece[][] board) {
        ChessPiece[][] newBoard = new ChessPiece[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = board[i][j];
                if (piece != null) {
                    //newBoard[i][j] = new ChessPiece(piece.getType(), piece.getColor(), piece.getRow(), piece.getCol());
                }
            }
        }
        return newBoard;
    }

    //Choosing the difficulty level
    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    public class Position {
        private int row;
        private int col;

        // Constructor
        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        // Getters and setters
        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getCol() {
            return col;
        }

        public void setCol(int col) {
            this.col = col;
        }

        // Override toString method for better representation
        @Override
        public String toString() {
            return "(" + row + ", " + col + ")";
        }
    }

    public class Move {
        private Position start;
        private Position end;
        private double score; // Evaluation score assigned to the move

        // Constructor
        public Move(Position start, Position end, double score) {
            this.start = start;
            this.end = end;
            this.score = score;
        }

        // Getters and setters
        public Position getStart() {
            return start;
        }

        public void setStart(Position start) {
            this.start = start;
        }

        public Position getEnd() {
            return end;
        }

        public void setEnd(Position end) {
            this.end = end;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        // Override toString method for better representation
        @Override
        public String toString() {
            return "Move from " + start + " to " + end + " with score " + score;
        }
    }


}
