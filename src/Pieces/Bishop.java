package Pieces;

import Board.Tile;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;

public class Bishop extends ChessPiece {
    public Bishop(PieceColor color, String imagePath, int row, int col) {
        super(PieceType.BISHOP, color, imagePath, row, col);
        Image image = new Image(imagePath);
        ImagePattern imagePattern = new ImagePattern(image);
        //this.setFill(imagePattern);
    }

    @Override
    public ArrayList<Tile> move(Tile[][] state, int row, int col) {
        possiblemoves.clear();
        int tempx= row +1,tempy= col -1;
        while(tempx<8&&tempy>=0)
        {
            if(state[tempx][tempy].getPiece()==null)
            {
                possiblemoves.add(state[tempx][tempy]);
            }
            else if(state[tempx][tempy].getPiece().getColor()==this.getColor())
                break;
            else
            {
                possiblemoves.add(state[tempx][tempy]);
                break;
            }
            tempx++;
            tempy--;
        }
        tempx= row -1;tempy= col +1;
        while(tempx>=0&&tempy<8)
        {
            if(state[tempx][tempy].getPiece()==null)
                possiblemoves.add(state[tempx][tempy]);
            else if(state[tempx][tempy].getPiece().getColor()==this.getColor())
                break;
            else
            {
                possiblemoves.add(state[tempx][tempy]);
                break;
            }
            tempx--;
            tempy++;
        }
        tempx= row -1;tempy= col -1;
        while(tempx>=0&&tempy>=0)
        {
            if(state[tempx][tempy].getPiece()==null)
                possiblemoves.add(state[tempx][tempy]);
            else if(state[tempx][tempy].getPiece().getColor()==this.getColor())
                break;
            else
            {
                possiblemoves.add(state[tempx][tempy]);
                break;
            }
            tempx--;
            tempy--;
        }
        tempx= row +1;tempy= col +1;
        while(tempx<8&&tempy<8)
        {
            if(state[tempx][tempy].getPiece()==null)
                possiblemoves.add(state[tempx][tempy]);
            else if(state[tempx][tempy].getPiece().getColor()==this.getColor())
                break;
            else
            {
                possiblemoves.add(state[tempx][tempy]);
                break;
            }
            tempx++;
            tempy++;
        }
        return possiblemoves;

    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessPiece[][] board) {
        // Check if the destination is within the board bounds
        if (destRow < 0 || destRow >= 8 || destCol < 0 || destCol >= 8) {
            return false;
        }

        // Bishop moves diagonally (the change in rows should be equal to the change in columns)
        if (Math.abs(destRow - sourceRow) == Math.abs(destCol - sourceCol)) {
            int rowDirection = (destRow - sourceRow > 0) ? 1 : -1;
            int colDirection = (destCol - sourceCol > 0) ? 1 : -1;

            // Check if the path to the destination is clear
            for (int i = 1; i < Math.abs(destRow - sourceRow); i++) {
                if (board[sourceRow + i * rowDirection][sourceCol + i * colDirection] != null) {
                    return false;
                }
            }

            // Check if the destination is empty or contains an opponent's piece
            return board[destRow][destCol] == null || board[destRow][destCol].getColor() != getColor();
        }
        return false;
    }
}

