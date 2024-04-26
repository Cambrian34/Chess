package Pieces;

import Board.Tile;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

import java.util.ArrayList;

public class King extends ChessPiece {
    public King(PieceColor color, String imagePath, int row, int col) {
        super(PieceType.KING, color, imagePath, row, col);
        Image image = new Image(imagePath);
        ImagePattern imagePattern = new ImagePattern(image);
        //this.setFill(imagePattern);
    }

    @Override
    public ArrayList<Tile> move(Tile[][] pos, int row, int col) {
        //King can move only one step. So all the adjacent 8 cells have been considered.
        possiblemoves.clear();
        int posx[]={row, row, row +1, row +1, row +1, row -1, row -1, row -1};
        int posy[]={col -1, col +1, col -1, col, col +1, col -1, col, col +1};
        for(int i=0;i<8;i++)
            if((posx[i]>=0&&posx[i]<8&&posy[i]>=0&&posy[i]<8))
                if((pos[posx[i]][posy[i]].getPiece()==null||pos[posx[i]][posy[i]].getPiece().getColor()!=this.getColor()))
                    possiblemoves.add(pos[posx[i]][posy[i]]);
        return possiblemoves;
    }



    @Override
    public boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessPiece[][] board) {
        int dx = Math.abs(destCol - sourceCol);
        int dy = Math.abs(destRow - sourceRow);
        return (dx <= 1 && dy <= 1); // Valid move if the destination is within one square horizontally, vertically, or diagonally
    }
}

