import AI.Particpant;

public class Textbased {

    String[][] board;

    private Particpant[] players;
    private int currentTurn; // Keep track of the current turn
    private String currentPlayer;


    //
    public Textbased() {

        initializeBoard();


    }

    //initialize the board
    private void initializeBoard() {
        board = new String[][] {
                {"R", "N", "B", "Q", "K", "B", "N", "R"},
                {"P", "P", "P", "P", "P", "P", "P", "P"},
                {".", ".", ".", ".", ".", ".", ".", "."},
                {".", ".", ".", ".", ".", ".", ".", "."},
                {".", ".", ".", ".", ".", ".", ".", "."},
                {".", ".", ".", ".", ".", ".", ".", "."},
                {"p", "p", "p", "p", "p", "p", "p", "p"},
                {"r", "n", "b", "q", "k", "b", "n", "r"}
        };
    }

    //Print the board
    public void printBoard() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
    //Move the piece in the string array
    public void movePiece(int sourceRow, int sourceCol, int destRow, int destCol) {
        board[destRow][destCol] = board[sourceRow][sourceCol];
        board[sourceRow][sourceCol] = ".";
    }
    //Check if the move is valid
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol) {
        String piece = board[sourceRow][sourceCol];
        String destinationPiece = board[destRow][destCol];

        // Check if the source square contains a piece
        if (piece.equals(".")) {
            return false; // No piece at source square
        }

        // Check if the destination square contains the player's own piece
        if (!destinationPiece.equals(".") && destinationPiece.toUpperCase().equals(piece.toUpperCase())) {
            return false; // Destination square occupied by player's own piece
        }

        // Implement rules for each type of chess piece
        switch (piece.toUpperCase()) {
            case "P":
                return isValidPawnMove(sourceRow, sourceCol, destRow, destCol);
            case "R":
                return isValidRookMove(sourceRow, sourceCol, destRow, destCol);
            case "N":
                return isValidKnightMove(sourceRow, sourceCol, destRow, destCol);
            case "B":
                return isValidBishopMove(sourceRow, sourceCol, destRow, destCol);
            case "Q":
                return isValidQueenMove(sourceRow, sourceCol, destRow, destCol);
            case "K":
                return isValidKingMove(sourceRow, sourceCol, destRow, destCol);
            default:
                return false; // Invalid piece
        }
    }


    private boolean isValidPawnMove(int sourceRow, int sourceCol, int destRow, int destCol) {
        // Check if the destination square is within the board bounds
        if (destRow < 0 || destRow >= 8 || destCol < 0 || destCol >= 8) {
            return false;
        }

        // Get the direction of movement based on the color of the pawn (upward for white, downward for black)
        int direction = (board[sourceRow][sourceCol].equals("P")) ? -1 : 1;

        // Check if the pawn is moving forward
        if (destCol != sourceCol) {
            // Check if the pawn is capturing diagonally
            if (Math.abs(destCol - sourceCol) == 1 && destRow - sourceRow == direction) {
                // Check if there is a piece to capture
                String destPiece = board[destRow][destCol];
                if (!destPiece.equals(".") && !destPiece.toUpperCase().equals(board[sourceRow][sourceCol].toUpperCase())) {
                    return true;
                }
            }
            // Handle en passant here if needed
            // Implement en passant logic
            return false;
        } else {
            // Check if the pawn is moving forward one or two squares
            if (destRow - sourceRow == direction) {
                // Check if the destination square is empty
                if (board[destRow][destCol].equals(".")) {
                    return true;
                }
            } else if (destRow - sourceRow == 2 * direction && sourceRow == (board[sourceRow][sourceCol].equals("P") ? 6 : 1)) {
                // Check if the pawn is moving forward two squares from its starting position
                // Check if the squares in between are empty
                return board[sourceRow + direction][sourceCol].equals(".") && board[destRow][destCol].equals(".");
            }
        }

        return false;
    }


    private boolean isValidRookMove(int sourceRow, int sourceCol, int destRow, int destCol) {
        // Check if the destination square is within the board bounds
        if (destRow < 0 || destRow >= 8 || destCol < 0 || destCol >= 8) {
            return false;
        }

        // Check if the rook is moving along a rank or file
        if (sourceRow == destRow || sourceCol == destCol) {
            // Check if there are any pieces blocking the path
            if (sourceRow == destRow) {
                // Rook is moving along the same row (horizontal movement)
                int minCol = Math.min(sourceCol, destCol);
                int maxCol = Math.max(sourceCol, destCol);
                for (int col = minCol + 1; col < maxCol; col++) {
                    if (!board[sourceRow][col].equals(".")) {
                        return false; // Piece blocking the path
                    }
                }
            } else {
                // Rook is moving along the same column (vertical movement)
                int minRow = Math.min(sourceRow, destRow);
                int maxRow = Math.max(sourceRow, destRow);
                for (int row = minRow + 1; row < maxRow; row++) {
                    if (!board[row][sourceCol].equals(".")) {
                        return false; // Piece blocking the path
                    }
                }
            }
            // Check if the destination square is empty or contains an opponent's piece
            String destPiece = board[destRow][destCol];
            return destPiece.equals(".") || !destPiece.toUpperCase().equals(board[sourceRow][sourceCol].toUpperCase());
        }

        return false; // Invalid rook move (not along a rank or file)
    }


    private boolean isValidKnightMove(int sourceRow, int sourceCol, int destRow, int destCol) {
        // Check if the destination square is within the board bounds
        if (destRow < 0 || destRow >= 8 || destCol < 0 || destCol >= 8) {
            return false;
        }

        // Calculate the absolute differences in rows and columns
        int rowDiff = Math.abs(destRow - sourceRow);
        int colDiff = Math.abs(destCol - sourceCol);

        // Check if the move follows the L-shaped pattern of a knight (2 squares in one direction and 1 square perpendicular)
        return (rowDiff == 2 && colDiff == 1) || (rowDiff == 1 && colDiff == 2);
    }


    private boolean isValidBishopMove(int sourceRow, int sourceCol, int destRow, int destCol) {
        // Check if the destination square is within the board bounds
        if (destRow < 0 || destRow >= 8 || destCol < 0 || destCol >= 8) {
            return false;
        }

        // Check if the move is along a diagonal (absolute differences in rows and columns are equal)
        int rowDiff = Math.abs(destRow - sourceRow);
        int colDiff = Math.abs(destCol - sourceCol);
        if (rowDiff != colDiff) {
            return false; // Not along a diagonal
        }

        // Determine the direction of movement along the diagonal (up or down, left or right)
        int rowDirection = (destRow - sourceRow) > 0 ? 1 : -1;
        int colDirection = (destCol - sourceCol) > 0 ? 1 : -1;

        // Check if there are any pieces blocking the diagonal path
        int currentRow = sourceRow + rowDirection;
        int currentCol = sourceCol + colDirection;
        while (currentRow != destRow && currentCol != destCol) {
            if (!board[currentRow][currentCol].equals(".")) {
                return false; // Piece blocking the path
            }
            currentRow += rowDirection;
            currentCol += colDirection;
        }

        // Check if the destination square is empty or contains an opponent's piece
        String destPiece = board[destRow][destCol];
        return destPiece.equals(".") || !destPiece.toUpperCase().equals(board[sourceRow][sourceCol].toUpperCase());
    }


    private boolean isValidQueenMove(int sourceRow, int sourceCol, int destRow, int destCol) {
        // Check if the destination square is within the board bounds
        if (destRow < 0 || destRow >= 8 || destCol < 0 || destCol >= 8) {
            return false;
        }

        // Check if the move is along a rank, file, or diagonal
        if (sourceRow == destRow || sourceCol == destCol) {
            // Rook-like movement (along rank or file)
            return isValidRookMove(sourceRow, sourceCol, destRow, destCol);
        } else {
            // Bishop-like movement (along diagonal)
            return isValidBishopMove(sourceRow, sourceCol, destRow, destCol);
        }
    }


    private boolean isValidKingMove(int sourceRow, int sourceCol, int destRow, int destCol) {
        // Check if the destination square is within the board bounds
        if (destRow < 0 || destRow >= 8 || destCol < 0 || destCol >= 8) {
            return false;
        }

        // Calculate the absolute differences in rows and columns
        int rowDiff = Math.abs(destRow - sourceRow);
        int colDiff = Math.abs(destCol - sourceCol);

        // Check if the move is within the king's range (one square in any direction)
        return (rowDiff <= 1 && colDiff <= 1);
    }


    // Add methods for handling turns
    public Particpant getCurrentPlayer() {
        // Implement logic to determine the current player based on the turn number
        return players[currentTurn % 2]; // Alternates between players 0 and 1


    }

    // Add methods for checking for checkmate and stalemate
    public boolean isCheckmate() {
        // Get the current player
        Particpant currentPlayer = getCurrentPlayer();

        // Find the king's position
        int kingRow = -1, kingCol = -1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].equalsIgnoreCase(currentPlayer.isWhite ? "K" : "k")) {
                    kingRow = i;
                    kingCol = j;
                    break;
                }
            }
        }

        // Check if the king is in check
        if (!isKingInCheck(kingRow, kingCol)) {
            return false; // King is not in check, so it's not checkmate
        }

        // Check if the king has any legal moves to get out of check
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (isValidMove(kingRow, kingCol, i, j)) {
                    // If there's any legal move that gets the king out of check, return false (not checkmate)
                    return false;
                }
            }
        }

        // If there are no legal moves to get the king out of check, it's checkmate
        return true;
    }

    private boolean isKingInCheck(int kingRow, int kingCol) {
        // Iterate over the board and check if any opponent's piece can legally capture the king
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // Skip empty squares and squares with the current player's pieces
                if (!board[i][j].equals(".") && (i != kingRow || j != kingCol)) {
                    // Check if the piece at position (i, j) can legally move to capture the king
                    if (isValidMove(i, j, kingRow, kingCol)) {
                        return true; // The king is in check
                    }
                }
            }
        }
        return false; // The king is not in check
    }

    private boolean isStalemate(String currentPlayer) {
        // Iterate over the board and find all the current player's pieces
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                // Check if the piece at position (i, j) belongs to the current player
                if (!board[i][j].equals(".") && Character.isUpperCase(board[i][j].charAt(0)) && board[i][j].equalsIgnoreCase(currentPlayer)) {
                    // Check if there are any legal moves available for this piece
                    for (int m = 0; m < board.length; m++) {
                        for (int n = 0; n < board[m].length; n++) {
                            // Check if the piece at position (i, j) can legally move to position (m, n)
                            if (isValidMove(i, j, m, n)) {
                                // A legal move is found, so it's not stalemate
                                return false;
                            }
                        }
                    }
                }
            }
        }
        // If no legal moves are found for any piece of the current player, it's stalemate
        return true;
    }

    // Add methods for implementing special moves
    private boolean canCastle(String currentPlayer) {
        // Determine the row of the back rank based on the current player
        int backRank = (currentPlayer.equalsIgnoreCase("white")) ? 0 : 7;

        // Check if the king and rooks are in their initial positions
        String king = (currentPlayer.equalsIgnoreCase("white")) ? "K" : "k";
        String rook1 = (currentPlayer.equalsIgnoreCase("white")) ? "R" : "r";
        String rook2 = (currentPlayer.equalsIgnoreCase("white")) ? "R" : "r";
        int kingColumn = 4; // Column index of the king
        int rook1Column = 0; // Column index of the left rook
        int rook2Column = 7; // Column index of the right rook

        // Check if the king is in its initial position
        if (!board[backRank][kingColumn].equals(king)) {
            return false;
        }

        // Check if the left rook is in its initial position
        if (!board[backRank][rook1Column].equals(rook1)) {
            return false;
        }

        // Check if the right rook is in its initial position
        if (!board[backRank][rook2Column].equals(rook2)) {
            return false;
        }

        // Check if the squares between the king and the rooks are empty
        for (int col = rook1Column + 1; col < kingColumn; col++) {
            if (!board[backRank][col].equals(".")) {
                return false;
            }
        }
        for (int col = kingColumn + 1; col < rook2Column; col++) {
            if (!board[backRank][col].equals(".")) {
                return false;
            }
        }

        // Check if the king is not in check
        if (isKingInCheck(backRank, kingColumn)) {
            return false;
        }

        // Check if any squares that the king moves over or ends up in are under attack
        // You can implement this part by temporarily moving the king to each potential destination square and checking if it's under attack

        // If all conditions are met, castling is allowed
        return true;
    }

    /*public boolean canEnPassant(Particpant player, int sourceRow, int sourceCol, int destRow, int destCol) {
        // Implement logic to check if en passant is allowed for the player in the specified move
    }*/

    // Add method for updating the board and checking move validity
    public boolean makeMove(int sourceRow, int sourceCol, int destRow, int destCol, String currentPlayer) {
        // Check if it's the current player's turn
        if (!getCurrentPlayer().equals(currentPlayer)) {
            System.out.println("It's not " + currentPlayer + "'s turn.");
            return false;
        }

        // Check if the move is valid
        if (!isValidMove(sourceRow, sourceCol, destRow, destCol)) {
            System.out.println("Invalid move.");
            return false;
        }

        // Make the move
        movePiece(sourceRow, sourceCol, destRow, destCol);
        displayBoard();

        // Check for castling



        // Check for checkmate
        if (isCheckmate()) {
            System.out.println("Checkmate! " + currentPlayer + " wins.");
        }

        // Check for stalemate
        if (isStalemate(currentPlayer)) {
            System.out.println("Stalemate! The game is a draw.");
        }

        // Update the current player
        updateCurrentPlayer();

        return true;
    }

    // Add method for updating the current player
    private void updateCurrentPlayer() {
        currentPlayer = (currentPlayer.equals("White")) ? "Black" : "White";
        System.out.println("It's now " + currentPlayer + "'s turn.");
    }


    // Add method for displaying the text-based UI
    public void displayBoard() {
        System.out.println("  a b c d e f g h");
        System.out.println(" -----------------");
        for (int i = 0; i < board.length; i++) {
            System.out.print(8 - i + "|");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + "|");
            }
            System.out.println(" " + (8 - i));
            System.out.println(" -----------------");
        }
        System.out.println("  a b c d e f g h");
    }



}
