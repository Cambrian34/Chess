package Pieces;

import Board.Tile;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;

public class Knight extends ChessPiece {
    public Knight(PieceColor color, String imagePath, int row, int col) {
        super(PieceType.KNIGHT, color, imagePath, row, col);
        Image image = new Image(imagePath);
        ImagePattern imagePattern = new ImagePattern(image);
        //this.setFill(imagePattern);
    }

    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessPiece[][] board) {
        int[][] moves = {{-2, -1}, {-2, 1}, {-1, -2}, {-1, 2}, {1, -2}, {1, 2}, {2, -1}, {2, 1}};

        // Check if the destination is within the board bounds
        if (destRow < 0 || destRow >= 8 || destCol < 0 || destCol >= 8) {
            return false;
        }

        // Check if the move is one of the valid knight moves
        for (int[] move : moves) {
            if (destRow == sourceRow + move[0] && destCol == sourceCol + move[1]) {
                // Check if the destination is empty or contains an opponent's piece
                return board[destRow][destCol] == null || board[destRow][destCol].getColor() != getColor();
            }
        }
        return false;
    }


    public ArrayList<Tile> move(Tile state[][], int row, int col)
    {
        possiblemoves.clear();
        int[] posx ={row +1, row +1, row +2, row +2, row -1, row -1, row -2, row -2};
        int[] posy ={col -2, col +2, col -1, col +1, col -2, col +2, col -1, col +1};
        for(int i=0;i<8;i++)
            if((posx[i]>=0&&posx[i]<8&&posy[i]>=0&&posy[i]<8))
                if((state[posx[i]][posy[i]].getPiece()==null||state[posx[i]][posy[i]].getPiece().getColor()!=this.getColor()))
                {
                    possiblemoves.add(state[posx[i]][posy[i]]);
                }
        return possiblemoves;
    }
}
