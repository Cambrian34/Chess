package Pieces;

import Board.Tile;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;

public class Queen extends ChessPiece {
    public Queen(PieceColor color, String imagePath, int row, int col) {
        super(PieceType.QUEEN, color, imagePath, row, col);
        Image image = new Image(imagePath);
        ImagePattern imagePattern = new ImagePattern(image);
        //this.setFill(imagePattern);
    }

    @Override
    public ArrayList<Tile> move(Tile[][] pos, int row, int col) {
        possiblemoves.clear();

        //Checking possible moves in vertical direction
        int tempx= row -1;
        while(tempx>=0)
        {
            if(pos[tempx][col].getPiece()==null)
                possiblemoves.add(pos[tempx][col]);
            else if(pos[tempx][col].getPiece().getColor()==this.getColor())
                break;
            else
            {
                possiblemoves.add(pos[tempx][col]);
                break;
            }
            tempx--;
        }

        tempx= row +1;
        while(tempx<8)
        {
            if(pos[tempx][col].getPiece()==null)
                possiblemoves.add(pos[tempx][col]);
            else if(pos[tempx][col].getPiece().getColor()==this.getColor())
                break;
            else
            {
                possiblemoves.add(pos[tempx][col]);
                break;
            }
            tempx++;
        }


        //Checking possible moves in horizontal Direction
        int tempy= col -1;
        while(tempy>=0)
        {
            if(pos[row][tempy].getPiece()==null)
                possiblemoves.add(pos[row][tempy]);
            else if(pos[row][tempy].getPiece().getColor()==this.getColor())
                break;
            else
            {
                possiblemoves.add(pos[row][tempy]);
                break;
            }
            tempy--;
        }
        tempy= col +1;
        while(tempy<8)
        {
            if(pos[row][tempy].getPiece()==null)
                possiblemoves.add(pos[row][tempy]);
            else if(pos[row][tempy].getPiece().getColor()==this.getColor())
                break;
            else
            {
                possiblemoves.add(pos[row][tempy]);
                break;
            }
            tempy++;
        }

        //Checking for possible moves in diagonal direction
        tempx= row +1;tempy= col -1;
        while(tempx<8&&tempy>=0)
        {
            if(pos[tempx][tempy].getPiece()==null)
                possiblemoves.add(pos[tempx][tempy]);
            else if(pos[tempx][tempy].getPiece().getColor()==this.getColor())
                break;
            else
            {
                possiblemoves.add(pos[tempx][tempy]);
                break;
            }
            tempx++;
            tempy--;
        }
        tempx= row -1;tempy= col +1;
        while(tempx>=0&&tempy<8)
        {
            if(pos[tempx][tempy].getPiece()==null)
                possiblemoves.add(pos[tempx][tempy]);
            else if(pos[tempx][tempy].getPiece().getColor()==this.getColor())
                break;
            else
            {
                possiblemoves.add(pos[tempx][tempy]);
                break;
            }
            tempx--;
            tempy++;
        }
        tempx= row -1;tempy= col -1;
        while(tempx>=0&&tempy>=0)
        {
            if(pos[tempx][tempy].getPiece()==null)
                possiblemoves.add(pos[tempx][tempy]);
            else if(pos[tempx][tempy].getPiece().getColor()==this.getColor())
                break;
            else
            {
                possiblemoves.add(pos[tempx][tempy]);
                break;
            }
            tempx--;
            tempy--;
        }
        tempx= row +1;tempy= col +1;
        while(tempx<8&&tempy<8)
        {
            if(pos[tempx][tempy].getPiece()==null)
                possiblemoves.add(pos[tempx][tempy]);
            else if(pos[tempx][tempy].getPiece().getColor()==this.getColor())
                break;
            else
            {
                possiblemoves.add(pos[tempx][tempy]);
                break;
            }
            tempx++;
            tempy++;
        }
        return possiblemoves;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessPiece[][] board) {
        if (sourceRow == destRow || sourceCol == destCol || Math.abs(destRow - sourceRow) == Math.abs(destCol - sourceCol)) {
            // Check if there are any pieces blocking the path
            int dx = (destCol - sourceCol) == 0 ? 0 : (destCol - sourceCol) / Math.abs(destCol - sourceCol);
            int dy = (destRow - sourceRow) == 0 ? 0 : (destRow - sourceRow) / Math.abs(destRow - sourceRow);
            int x = sourceCol + dx;
            int y = sourceRow + dy;
            while (x != destCol || y != destRow) {
                if (board[y][x] != null) {
                    return false; // Path is blocked
                }
                x += dx;
                y += dy;
            }
            return true; // Move is valid
        }
        return false; // Move is not in a straight line or diagonal
    }
}

