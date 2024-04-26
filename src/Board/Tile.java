package Board;

import Pieces.ChessPiece;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Tile extends Rectangle {

    private final int TILE_SIZE = 50;
    private ChessPiece piece;

    public boolean select = false;
    private boolean ispossibleMove;
    private int x,y;

    //if tile has a chess piece
    public boolean hasPiece() {
        return piece != null;
    }

    public ChessPiece getPiece() {
        return this.piece;
    }

    public Tile(int x1, int y1, ChessPiece piece) {
        //set the width and height of the tile
        setWidth(50);
        setHeight(50);
        this.x = x1;
        this.y = y1;

        //alternate colors
        setFill((x + y) % 2 == 0 ? Color.WHITE : Color.BLACK);

        //set the piece
        if(piece!= null) {
            setPiece(piece);
        }

    }

    //set the piece

    public void setPiece(ChessPiece piece1) {
        this.piece = piece1;

        ImageView img = new ImageView(piece.getImage());
        img.setFitHeight(TILE_SIZE);
        img.setFitWidth(TILE_SIZE);

        // Assuming 'this' represents a Rectangle
        this.setFill(new ImagePattern(img.getImage()));
    }
    //remove the piece
    public void removePiece() {
        this.piece = null;
        // Calculate the sum of row and column indices to determine the fill color
        int sumIndices = x+y;

        // Set the fill color based on whether the sum of indices is even or odd
        this.setFill(sumIndices % 2 == 0 ? Color.WHITE : Color.BLACK);
    }


    //select the tile
    public void select() {
        select = true;
        this.setStroke(Color.BLUE);
    }

    //deselect the tile
    public void deselect() {
        select = false;
        this.setStroke(null);
    }

    //is selected
    public boolean isSelected() {
        return select;
    }

    //possible move
    public void setpossibleMove() {
        this.setStroke(Color.GREEN);
        ispossibleMove = true;
    }

    //not possible move
    public void unsetpossibleMove() {
        this.setStroke(null);
        ispossibleMove = false;
    }

    //is possible move
    public boolean ispossibleMove() {
        return ispossibleMove;
    }

    //is check
    public void setCheck() {
        this.setStroke(Color.RED);
    }

    //unset check
    public void unsetCheck() {
        this.setStroke(null);
    }

    //is check
    public void setCheckMate() {
        this.setStroke(Color.RED);
    }

}