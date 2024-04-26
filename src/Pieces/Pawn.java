package Pieces;

import Board.Tile;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;

public class Pawn extends ChessPiece {
    private boolean hasMoved =false; // Flag to track if the pawn has moved before

    public Pawn(PieceColor color, String imagePath, int row, int col) {
        super(PieceType.PAWN, color, imagePath, row, col);
        this.hasMoved = false; // Initialize to false since the pawn hasn't moved yet
        Image image = new Image(imagePath);
        ImagePattern imagePattern = new ImagePattern(image);
        //this.setFill(imagePattern);
    }

    @Override
    public ArrayList<Tile> move(Tile[][] pos, int row, int col) {
        possiblemoves.clear();
        if(getColor()==PieceColor.WHITE)
        {
            if(row ==0)
                return possiblemoves;
            if(pos[row -1][col].getPiece()==null)
            {
                possiblemoves.add(pos[row -1][col]);
                if(row ==6)
                {
                    if(pos[4][col].getPiece()==null)
                        possiblemoves.add(pos[4][col]);
                }
            }
            if((col >0)&&(pos[row -1][col -1].getPiece()!=null)&&(pos[row -1][col -1].getPiece().getColor()!=this.getColor()))
                possiblemoves.add(pos[row -1][col -1]);
            if((col <7)&&(pos[row -1][col +1].getPiece()!=null)&&(pos[row -1][col +1].getPiece().getColor()!=this.getColor()))
                possiblemoves.add(pos[row -1][col +1]);
        }else
        {
            if(row ==8)
                return possiblemoves;
            if(pos[row +1][col].getPiece()==null)
            {
                possiblemoves.add(pos[row +1][col]);
                if(row ==1)
                {
                    if(pos[3][col].getPiece()==null)
                        possiblemoves.add(pos[3][col]);
                }
            }
            if((col >0)&&(pos[row +1][col -1].getPiece()!=null)&&(pos[row +1][col -1].getPiece().getColor()!=this.getColor()))
                possiblemoves.add(pos[row +1][col -1]);
            if((col <7)&&(pos[row +1][col +1].getPiece()!=null)&&(pos[row +1][col +1].getPiece().getColor()!=this.getColor()))
                possiblemoves.add(pos[row +1][col +1]);
        }
        return possiblemoves;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessPiece[][] board) {
        int direction = getColor() == PieceColor.WHITE ? -1 : 1; // Direction of movement (up or down the board)

        // Check if the destination is within the bounds of the board
        if (destRow < 0 || destRow >= 8 || destCol < 0 || destCol >= 8) {
            return false;
        }

        // Regular forward move (one step)
        if (destCol == sourceCol && board[destRow][destCol] == null) {
            if (destRow == sourceRow + direction) {
                hasMoved = true; // Set hasMoved to true after the first move
                return true;
            }
            // Allow initial two-step move if pawn hasn't moved yet
            if (!hasMoved && destRow == sourceRow + 2 * direction && board[sourceRow + direction][destCol] == null) {
                hasMoved = true;
                return true;
            }
        }

        // Diagonal capture (one step)
        if (Math.abs(destCol - sourceCol) == 1 && destRow == sourceRow + direction) {
            ChessPiece destPiece = board[destRow][destCol];
            return destPiece != null && destPiece.getColor() != getColor();
        }

        // Invalid move otherwise
        return false;
    }

    public boolean isFirstMove() {
        return !hasMoved;
    }
    //setter
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }
}
