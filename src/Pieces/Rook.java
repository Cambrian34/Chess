package Pieces;

import Board.Tile;
import Movement.Draggablemaker;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Rook extends ChessPiece {

    private boolean firstClick = true; // Track first and second click

    public Rook(PieceColor color, String imagePath, int row, int col) {
        super(PieceType.ROOK, color, imagePath, row, col);
        Image image = new Image(imagePath);
        ImagePattern imagePattern = new ImagePattern(image);
        //this.setFill(imagePattern);

        // Event handler for click


    }
    //Valid move rook
    @Override
    public ArrayList<Tile> move(Tile[][] pos, int row, int col) {
        //Rook can move only horizontally or vertically
        ArrayList<Tile> possiblemoves = new ArrayList<Tile>();
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
        return possiblemoves;
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessPiece[][] board) {
        // Check if the destination is within the board bounds
        if (destRow < 0 || destRow >= 8 || destCol < 0 || destCol >= 8) {
            return false;
        }

        // Rook moves either horizontally or vertically (either change in rows or change in columns should be zero)
        if (destRow == sourceRow || destCol == sourceCol) {
            int step = (destRow == sourceRow) ? ((destCol - sourceCol > 0) ? 1 : -1) : ((destRow - sourceRow > 0) ? 1 : -1);

            // Check if the path to the destination is clear
            if (destRow == sourceRow) {
                for (int col = sourceCol + step; col != destCol; col += step) {
                    if (board[sourceRow][col] != null) {
                        return false;
                    }
                }
            } else {
                for (int row = sourceRow + step; row != destRow; row += step) {
                    if (board[row][sourceCol] != null) {
                        return false;
                    }
                }
            }

            // Check if the destination is empty or contains an opponent's piece
            return board[destRow][destCol] == null || board[destRow][destCol].getColor() != getColor();
        }
        return false;
    }






}



