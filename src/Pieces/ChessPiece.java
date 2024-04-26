package Pieces;

import Board.Tile;
import Movement.Draggablemaker;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public abstract class ChessPiece{
    public PieceType type;
    public PieceColor color;
    public Image image;
    private int row;
    private int col;
    String id;
    protected ArrayList<Tile> possiblemoves = new ArrayList<Tile>();


    public ChessPiece(PieceType type, PieceColor color, String imagePath, int row, int col) {
       // super(50, 50);
        this.type = type;
        this.color = color;
        this.image = new Image(imagePath);
        this.row = row;
        this.col = col;


    }
    //set an id for the piece
    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }


    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public PieceType getType() {
        return type;
    }

    public PieceColor getColor() {
        return color;
    }

    //setter for type and color
    public void setType(PieceType type) {
        this.type = type;
    }

    public void setColor(PieceColor color) {
        this.color = color;
    }


    public Image getImage() {
        return image;
    }

    public abstract ArrayList<Tile> move(Tile[][] pos, int row, int col);  //Abstract Function. Must be overridden

    public abstract boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessPiece[][] board);



}

