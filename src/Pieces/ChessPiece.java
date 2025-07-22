package Pieces;

import Board.Tile;
import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class ChessPiece {
    public PieceType type;
    public PieceColor color;
    public Image image;
    private int row;
    private int col;
    protected ArrayList<Tile> possiblemoves = new ArrayList<>();

    public ChessPiece(PieceType type, PieceColor color, String imagePath, int row, int col) {
        this.type = type;
        this.color = color;
        this.row = row;
        this.col = col;

        // Correctly load the image as a resource from the classpath.
        // This ensures it works even when the project is packaged into a JAR file.
        try {
            // Prepending "/" makes the path absolute from the root of the classpath.
            this.image = new Image(getClass().getResourceAsStream("/" + imagePath));
        } catch (Exception e) {
            System.err.println("Failed to load image: " + imagePath);
            e.printStackTrace();
        }
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

    public Image getImage() {
        return image;
    }

    public abstract ArrayList<Tile> move(Tile[][] pos, int row, int col);

    public abstract boolean isValidMove(int sourceRow, int sourceCol, int destRow, int destCol, ChessPiece[][] board);
}


