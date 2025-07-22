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
    private int x, y;

    public Tile(int x, int y, ChessPiece piece) {
        setWidth(TILE_SIZE);
        setHeight(TILE_SIZE);
        this.x = x;
        this.y = y;

        relocate(x * TILE_SIZE, y * TILE_SIZE);

        setFill((x + y) % 2 == 0 ? Color.rgb(240, 217, 181) : Color.rgb(181, 136, 99));

        if (piece != null) {
            setPiece(piece);
        }
    }

    public boolean hasPiece() {
        return piece != null;
    }

    public ChessPiece getPiece() {
        return this.piece;
    }

    public int getXPos() {
        return x;
    }

    public int getYPos() {
        return y;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
        if (piece != null) {
            ImageView img = new ImageView(piece.getImage());
            img.setFitHeight(TILE_SIZE);
            img.setFitWidth(TILE_SIZE);
            this.setFill(new ImagePattern(img.getImage()));
        } else {
            setFill((x + y) % 2 == 0 ? Color.rgb(240, 217, 181) : Color.rgb(181, 136, 99));
        }
    }

    public void removePiece() {
        setPiece(null);
    }

    public void select() {
        select = true;
        setStroke(Color.BLUE);
        setStrokeWidth(3);
    }

    public void deselect() {
        select = false;
        setStroke(null);
    }

    public boolean isSelected() {
        return select;
    }

    public void setpossibleMove() {
        setFill(Color.rgb(135, 152, 106, 0.7));
        ispossibleMove = true;
    }

    public void unsetpossibleMove() {
        setFill((x + y) % 2 == 0 ? Color.rgb(240, 217, 181) : Color.rgb(181, 136, 99));
        if(hasPiece()){
            ImageView img = new ImageView(piece.getImage());
            img.setFitHeight(TILE_SIZE);
            img.setFitWidth(TILE_SIZE);
            this.setFill(new ImagePattern(img.getImage()));
        }
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