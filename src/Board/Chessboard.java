package Board;

import Pieces.*;

import java.util.ArrayList;

public class Chessboard {
    public final int SIZE = 8;
    private final Tile[][] board;

    public Chessboard() {
        // Create the Tile objects ONCE when the board is constructed.
        board = new Tile[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = new Tile(col, row, null);
            }
        }
        // Then, place the pieces on these tiles.
        initializeBoard();
    }

    public void initializeBoard() {
        // First, clear all pieces from the existing tiles.
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col].removePiece();
            }
        }

        // Now, set the pieces in their starting positions on the existing tiles.
        String White_Pawn = "art/wpawn.png";
        String Black_Pawn = "art/bpawn.png";
        String White_Rook = "art/wrook.png";
        String Black_Rook = "art/brook.png";
        String White_Knight = "art/wknight.png";
        String Black_Knight = "art/bknight.png";
        String White_Bishop = "art/wbishop.png";
        String Black_Bishop = "art/bbishop.png";
        String White_Queen = "art/wqueen.png";
        String Black_Queen = "art/bqueen.png";
        String White_King = "art/wking.png";
        String Black_King = "art/bking.png";

        for (int i = 0; i < SIZE; i++) {
            board[1][i].setPiece(new Pawn(PieceColor.BLACK, Black_Pawn, i, 1));
            board[6][i].setPiece(new Pawn(PieceColor.WHITE, White_Pawn, i, 6));
        }

        board[0][0].setPiece(new Rook(PieceColor.BLACK, Black_Rook, 0, 0));
        board[0][7].setPiece(new Rook(PieceColor.BLACK, Black_Rook, 7, 0));
        board[7][0].setPiece(new Rook(PieceColor.WHITE, White_Rook, 0, 7));
        board[7][7].setPiece(new Rook(PieceColor.WHITE, White_Rook, 7, 7));

        board[0][1].setPiece(new Knight(PieceColor.BLACK, Black_Knight, 1, 0));
        board[0][6].setPiece(new Knight(PieceColor.BLACK, Black_Knight, 6, 0));
        board[7][1].setPiece(new Knight(PieceColor.WHITE, White_Knight, 1, 7));
        board[7][6].setPiece(new Knight(PieceColor.WHITE, White_Knight, 6, 7));

        board[0][2].setPiece(new Bishop(PieceColor.BLACK, Black_Bishop, 2, 0));
        board[0][5].setPiece(new Bishop(PieceColor.BLACK, Black_Bishop, 5, 0));
        board[7][2].setPiece(new Bishop(PieceColor.WHITE, White_Bishop, 2, 7));
        board[7][5].setPiece(new Bishop(PieceColor.WHITE, White_Bishop, 5, 7));

        board[0][3].setPiece(new Queen(PieceColor.BLACK, Black_Queen, 3, 0));
        board[7][3].setPiece(new Queen(PieceColor.WHITE, White_Queen, 3, 7));

        board[0][4].setPiece(new King(PieceColor.BLACK, Black_King, 4, 0));
        board[7][4].setPiece(new King(PieceColor.WHITE, White_King, 4, 7));
    }

    public Tile getTile(int row, int col) {
        if (row >= 0 && row < SIZE && col >= 0 && col < SIZE) {
            return board[row][col];
        }
        return null;
    }

    public Tile[][] getBoard() {
        return board;
    }

    public void movePiece(Tile from, Tile to) {
        if (from.hasPiece()) {
            ChessPiece pieceToMove = from.getPiece();
            pieceToMove.setCol(to.getXPos());
            pieceToMove.setRow(to.getYPos());
            to.setPiece(pieceToMove);
            from.removePiece();
        }
    }

    public boolean isCheckmate(PieceColor color) {
        if (!isKingInCheck(color)) {
            return false;
        }

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Tile startTile = board[row][col];
                if (startTile.hasPiece() && startTile.getPiece().getColor() == color) {
                    ArrayList<Tile> possibleMoves = startTile.getPiece().move(board, row, col);
                    for (Tile endTile : possibleMoves) {
                        ChessPiece originalEndPiece = endTile.getPiece();
                        movePiece(startTile, endTile); // Make the move
                        if (!isKingInCheck(color)) {
                            // Found a move to get out of check
                            movePiece(endTile, startTile); // Move back
                            if (originalEndPiece != null) endTile.setPiece(originalEndPiece);
                            return false;
                        }
                        // Undo the move
                        movePiece(endTile, startTile);
                        if (originalEndPiece != null) endTile.setPiece(originalEndPiece);
                    }
                }
            }
        }
        return true; // No move found to escape check
    }

    public boolean isStalemate(PieceColor color) {
        if (isKingInCheck(color)) {
            return false;
        }

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Tile tile = board[row][col];
                if (tile.hasPiece() && tile.getPiece().getColor() == color) {
                    ArrayList<Tile> moves = tile.getPiece().move(board, row, col);
                    if (!moves.isEmpty()) {
                        return false; // Found at least one legal move
                    }
                }
            }
        }
        return true; // No legal moves found
    }

    public boolean isKingInCheck(PieceColor kingColor) {
        Tile kingTile = findKing(kingColor);
        if (kingTile == null) return false;

        PieceColor opponentColor = (kingColor == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Tile tile = board[row][col];
                if (tile.hasPiece() && tile.getPiece().getColor() == opponentColor) {
                    if (tile.getPiece().move(board, row, col).contains(kingTile)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Tile findKing(PieceColor color) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Tile tile = board[row][col];
                if (tile.hasPiece() && tile.getPiece().getType() == PieceType.KING && tile.getPiece().getColor() == color) {
                    return tile;
                }
            }
        }
        return null;
    }
}
/*



import Pieces.*;

public class Chessboard {
    public final int SIZE = 8; // Size of the chessboard (8x8)

    private Tile[][] board;

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




    private ChessPiece[][] board2;

    public Chessboard() {
        initializeBoard2();
    }

    public void initializeBoard2() {

        board2 = new ChessPiece[SIZE][SIZE];

        // Initialize the tile with pieces in their starting positions

        // Add pawns
        for (int i = 0; i < SIZE; i++) {
            board2[1][i]= new Pawn(PieceColor.BLACK, Black_Pawn, 1, i);
            board2[6][i]= new Pawn(PieceColor.WHITE, White_Pawn, 6, i);
        }
        // Add rooks
        board2[0][0] = new Rook(PieceColor.BLACK, Black_Rook, 0, 0);
        board2[0][7] = new Rook(PieceColor.BLACK, Black_Rook, 0, 7);
        board2[7][0] = new Rook(PieceColor.WHITE, White_Rook, 7, 0);
        board2[7][7] = new Rook(PieceColor.WHITE, White_Rook, 7, 7);
        // Add knights
        board2[0][1] = new Knight(PieceColor.BLACK, Black_Knight, 0, 1);
        board2[0][6] = new Knight(PieceColor.BLACK, Black_Knight, 0, 6);
        board2[7][1] = new Knight(PieceColor.WHITE, White_Knight, 7, 1);
        board2[7][6] = new Knight(PieceColor.WHITE, White_Knight, 7, 6);
        // Add bishops
        board2[0][2] = new Bishop(PieceColor.BLACK, Black_Bishop, 0, 2);
        board2[0][5] = new Bishop(PieceColor.BLACK, Black_Bishop, 0, 5);
        board2[7][2] = new Bishop(PieceColor.WHITE, White_Bishop, 7, 2);
        board2[7][5] = new Bishop(PieceColor.WHITE, White_Bishop, 7, 5);
        // Add queens
        board2[0][3] = new Queen(PieceColor.BLACK, Black_Queen, 0, 3);
        board2[7][3] = new Queen(PieceColor.WHITE, White_Queen, 7, 3);
        // Add kings
        board2[0][4] = new King(PieceColor.BLACK, Black_King, 0, 4);
        board2[7][4] = new King(PieceColor.WHITE, White_King, 7, 4);







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



    public ChessPiece getPiece(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return null; // Return null if coordinates are out of bounds
        }
        return board[row][col].getPiece(); // Otherwise, return the piece at the specified position
    }

    public ChessPiece getPieceAt(int row, int col) {
        return board[row][col].getPiece();
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
    public void initializeBoard() {
        board = new Tile[SIZE][SIZE];
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                board[row][col] = new Tile(col, row, null);
            }
        }

        // Initialize pieces
        String White_Pawn = "art/wpawn.png";
        String Black_Pawn = "art/bpawn.png";
        String White_Rook = "art/wrook.png";
        String Black_Rook = "art/brook.png";
        String White_Knight = "art/wknight.png";
        String Black_Knight = "art/bknight.png";
        String White_Bishop = "art/wbishop.png";
        String Black_Bishop = "art/bbishop.png";
        String White_Queen = "art/wqueen.png";
        String Black_Queen = "art/bqueen.png";
        String White_King = "art/wking.png";
        String Black_King = "art/bking.png";

        for (int i = 0; i < SIZE; i++) {
            board[1][i].setPiece(new Pawn(PieceColor.BLACK, Black_Pawn, i, 1));
            board[6][i].setPiece(new Pawn(PieceColor.WHITE, White_Pawn, i, 6));
        }

        board[0][0].setPiece(new Rook(PieceColor.BLACK, Black_Rook, 0, 0));
        board[0][7].setPiece(new Rook(PieceColor.BLACK, Black_Rook, 7, 0));
        board[7][0].setPiece(new Rook(PieceColor.WHITE, White_Rook, 0, 7));
        board[7][7].setPiece(new Rook(PieceColor.WHITE, White_Rook, 7, 7));

        board[0][1].setPiece(new Knight(PieceColor.BLACK, Black_Knight, 1, 0));
        board[0][6].setPiece(new Knight(PieceColor.BLACK, Black_Knight, 6, 0));
        board[7][1].setPiece(new Knight(PieceColor.WHITE, White_Knight, 1, 7));
        board[7][6].setPiece(new Knight(PieceColor.WHITE, White_Knight, 6, 7));

        board[0][2].setPiece(new Bishop(PieceColor.BLACK, Black_Bishop, 2, 0));
        board[0][5].setPiece(new Bishop(PieceColor.BLACK, Black_Bishop, 5, 0));
        board[7][2].setPiece(new Bishop(PieceColor.WHITE, White_Bishop, 2, 7));
        board[7][5].setPiece(new Bishop(PieceColor.WHITE, White_Bishop, 5, 7));

        board[0][3].setPiece(new Queen(PieceColor.BLACK, Black_Queen, 3, 0));
        board[7][3].setPiece(new Queen(PieceColor.WHITE, White_Queen, 3, 7));

        board[0][4].setPiece(new King(PieceColor.BLACK, Black_King, 4, 0));
        board[7][4].setPiece(new King(PieceColor.WHITE, White_King, 4, 7));
    }

    public Tile getTile(int row, int col) {
        return board[row][col];
    }

    public Tile[][] getBoard() {
        return board;
    }

    public void movePiece(Tile from, Tile to) {
        if (from.hasPiece()) {
            to.setPiece(from.getPiece());
            from.removePiece();
        }
    }

    public boolean isCheckmate(PieceColor color) {
        if (!isKingInCheck(color)) {
            return false;
        }

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Tile tile = board[row][col];
                if (tile.hasPiece() && tile.getPiece().getColor() == color) {
                    for (Tile possibleMove : tile.getPiece().move(board, row, col)) {
                        ChessPiece originalPiece = possibleMove.getPiece();
                        movePiece(tile, possibleMove);
                        if (!isKingInCheck(color)) {
                            movePiece(possibleMove, tile); // Move back
                            if (originalPiece != null) possibleMove.setPiece(originalPiece);
                            return false;
                        }
                        movePiece(possibleMove, tile); // Move back
                        if (originalPiece != null) possibleMove.setPiece(originalPiece);
                    }
                }
            }
        }
        return true;
    }

    public boolean isStalemate(PieceColor color) {
        if (isKingInCheck(color)) {
            return false;
        }

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Tile tile = board[row][col];
                if (tile.hasPiece() && tile.getPiece().getColor() == color) {
                    if (!tile.getPiece().move(board, row, col).isEmpty()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isKingInCheck(PieceColor kingColor) {
        Tile kingTile = findKing(kingColor);
        if (kingTile == null) return false;

        PieceColor opponentColor = (kingColor == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Tile tile = board[row][col];
                if (tile.hasPiece() && tile.getPiece().getColor() == opponentColor) {
                    if (tile.getPiece().move(board, row, col).contains(kingTile)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Tile findKing(PieceColor color) {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                Tile tile = board[row][col];
                if (tile.hasPiece() && tile.getPiece().getType() == PieceType.KING && tile.getPiece().getColor() == color) {
                    return tile;
                }
            }
        }
        return null;
    }


}*/
