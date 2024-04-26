package Board;


import Pieces.*;

public class Chessboard {
    public final int SIZE = 8; // Size of the chessboard (8x8)



    //images for the pieces
    /*
    //add an image for each piece
   // Image White_Pawn = new Image("file:art/wpawn.png", 40, 40, true, true);
    //Image Black_Pawn = new Image("file:art/bpawn.png", 40, 40, true, true);
    //Image White_Rook = new Image("file:art/wrook.png", 40, 40, true, true);
    Image Black_Rook = new Image("file:art/brook.png", 40, 40, true, true);

    Image White_Knight = new Image("file:art/wknight.png", 40, 40, true, true);

    Image Black_Knight = new Image("file:art/bknight.png", 40, 40, true, true);

    Image White_Bishop = new Image("file:art/wbishop.png", 40, 40, true, true);

    Image Black_Bishop8 = new Image("file:art/bbishop.png", 40, 40, true, true);

    Image White_Queen = new Image("file:art/wqueen.png", 40, 40, true, true);

    Image Black_Queen = new Image("file:art/bqueen.png", 40, 40, true, true);

    Image White_King = new Image("file:art/wking.png", 40, 40, true, true);

    Image Black_King = new Image("file:art/bking.png", 40, 40, true, true);
*/
    //String path to the image
    String White_Pawn = "file:art/wpawn.png";
    String Black_Pawn = "file:art/bpawn.png";
    String White_Rook = "file:art/wrook.png";
    String Black_Rook = "file:art/brook.png";
    String White_Knight = "file:art/wknight.png";
    String Black_Knight = "file:art/bknight.png";
    String White_Bishop = "file:art/wbishop.png";
    String Black_Bishop = "file:art/bbishop.png";
    String White_Queen = "file:art/wqueen.png";
    String Black_Queen = "file:art/bqueen.png";
    String White_King = "file:art/wking.png";
    String Black_King = "file:art/bking.png";




    private ChessPiece[][] board;

    public Chessboard() {
        initializeBoard();
    }

    public void initializeBoard() {

        board = new ChessPiece[SIZE][SIZE];

        // Initialize the tile with pieces in their starting positions

        // Add pawns
        for (int i = 0; i < SIZE; i++) {
            board[1][i]= new Pawn(PieceColor.BLACK, Black_Pawn, 1, i);
            board[6][i]= new Pawn(PieceColor.WHITE, White_Pawn, 6, i);
        }
        // Add rooks
        board[0][0] = new Rook(PieceColor.BLACK, Black_Rook, 0, 0);
        board[0][7] = new Rook(PieceColor.BLACK, Black_Rook, 0, 7);
        board[7][0] = new Rook(PieceColor.WHITE, White_Rook, 7, 0);
        board[7][7] = new Rook(PieceColor.WHITE, White_Rook, 7, 7);
        // Add knights
        board[0][1] = new Knight(PieceColor.BLACK, Black_Knight, 0, 1);
        board[0][6] = new Knight(PieceColor.BLACK, Black_Knight, 0, 6);
        board[7][1] = new Knight(PieceColor.WHITE, White_Knight, 7, 1);
        board[7][6] = new Knight(PieceColor.WHITE, White_Knight, 7, 6);
        // Add bishops
        board[0][2] = new Bishop(PieceColor.BLACK, Black_Bishop, 0, 2);
        board[0][5] = new Bishop(PieceColor.BLACK, Black_Bishop, 0, 5);
        board[7][2] = new Bishop(PieceColor.WHITE, White_Bishop, 7, 2);
        board[7][5] = new Bishop(PieceColor.WHITE, White_Bishop, 7, 5);
        // Add queens
        board[0][3] = new Queen(PieceColor.BLACK, Black_Queen, 0, 3);
        board[7][3] = new Queen(PieceColor.WHITE, White_Queen, 7, 3);
        // Add kings
        board[0][4] = new King(PieceColor.BLACK, Black_King, 0, 4);
        board[7][4] = new King(PieceColor.WHITE, White_King, 7, 4);







    }

    public boolean movePiece(int sourceRow, int sourceCol, int destRow, int destCol) {
        if (!isValidMove(sourceRow, sourceCol, destRow, destCol)) {
            return false;
        }

        // Move the piece to the destination
        board[destRow][destCol] = board[sourceRow][sourceCol];
        board[sourceRow][sourceCol] = null;

        return true;
    }

    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol) {
        // Check if source and destination squares are within the tile bounds
        if (sourceRow < 0 || sourceRow >= SIZE || sourceCol < 0 || sourceCol >= SIZE ||
                destRow < 0 || destRow >= SIZE || destCol < 0 || destCol >= SIZE) {
            return false;
        }

        // Check if there is a piece at the source square
        ChessPiece sourcePiece = board[sourceRow][sourceCol];
        if (sourcePiece == null) {
            return false;
        }

        // Check if the destination square is empty or contains an opponent's piece
        ChessPiece destPiece = board[destRow][destCol];
        if (destPiece != null && destPiece.getColor() == sourcePiece.getColor()) {
            return false;
        }

        // Check if the move is valid for the specific piece
        return sourcePiece.isValidMove(sourceRow, sourceCol, destRow, destCol, board);
    }

    public boolean isCheckmate(PieceColor color) {
        // Find the position of the king of the given color
        int kingRow = -1;
        int kingCol = -1;
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                ChessPiece piece = board[row][col];
                if (piece instanceof King && piece.getColor() == color) {
                    kingRow = row;
                    kingCol = col;
                    break;
                }
            }
        }

        if (kingRow == -1 || kingCol == -1) {
            // King not found - this should not happen in a valid game state
            return false;
        }

        // Check if the king is under attack
        if (isUnderAttack(kingRow, kingCol, color)) {
            // Check if the king can move to any safe squares
            for (int row = kingRow - 1; row <= kingRow + 1; row++) {
                for (int col = kingCol - 1; col <= kingCol + 1; col++) {
                    if (isValidMove(kingRow, kingCol, row, col)) {
                        // King can escape, so it's not checkmate
                        return false;
                    }
                }
            }

            // King cannot escape and is under attack - it's checkmate
            return true;
        }

        // King is not under attack - it's not checkmate
        return false;
    }

    private boolean isUnderAttack(int kingRow, int kingCol, PieceColor color) {
        // Iterate through all pieces of the opposite color
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null && piece.getColor() != color) {
                    // Check if the piece can attack the king
                    if (piece.isValidMove(row, col, kingRow, kingCol, board)) {
                        return true;
                    }
                }
            }
        }

        // King is not under attack
        return false;
    }

    public boolean isStalemate(PieceColor color) {
        // Iterate through all pieces of the given color
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                ChessPiece piece = board[row][col];
                if (piece != null && piece.getColor() == color) {
                    // Check if the piece has any legal moves
                    for (int destRow = 0; destRow < SIZE; destRow++) {
                        for (int destCol = 0; destCol < SIZE; destCol++) {
                            if (isValidMove(row, col, destRow, destCol)) {
                                // Found a legal move - it's not a stalemate
                                return false;
                            }
                        }
                    }
                }
            }
        }

        // No legal moves for any piece of the given color - it's a stalemate
        return true;
    }


    public ChessPiece getPiece(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return null; // Return null if coordinates are out of bounds
        }
        return board[row][col]; // Otherwise, return the piece at the specified position
    }

    public ChessPiece getPieceAt(int row, int col) {
        return board[row][col];
    }

    public boolean movePiece(ChessPiece selectedPiece, int destRow, int destCol) {
        if (selectedPiece == null) {
            return false; // No piece selected
        }

        int sourceRow = selectedPiece.getRow();
        int sourceCol = selectedPiece.getCol();

        if (!isValidMove(sourceRow, sourceCol, destRow, destCol)) {
            return false; // Invalid move
        }

        // Check if the destination square is occupied by an opponent's piece
        ChessPiece destPiece = board[destRow][destCol];
        if (destPiece != null && destPiece.getColor() != selectedPiece.getColor()) {
            // Capture the opponent's piece
            // You may need to update the GUI to reflect the capture
        }

        // Move the piece to the destination
        board[destRow][destCol] = selectedPiece;
        board[sourceRow][sourceCol] = null;
        selectedPiece.setRow(destRow);
        selectedPiece.setCol(destCol);

        return true; // Move successful
    }


    //get path to the image





}