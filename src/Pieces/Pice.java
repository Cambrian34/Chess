package Pieces;


import Pieces.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Pice extends StackPane {
    public final int SIZE = 8; // Size of the chessboard (8x8)

    //String path to the image
    String White_Pawn = "file:art/wpawn.png";
    String Black_Pawn = "file:art/bpawn.png";
    String White_Rook = "file:art/wrook.png";
    String Black_Rook = "file:art/brook.png";
    String White_Knight = "file:art/wknight.png";
    String Black_Knight = "file:art/bknight.png";
    String White_Bishop = "file:art/wbishop.png";
    String Black_Bishop = "file:art/bbishop.png";
    String White_Queen = "file:art/wqueen.png";
    String Black_Queen = "file:art/bqueen.png";
    String White_King = "file:art/wking.png";
    String Black_King = "file:art/bking.png";




    private ChessPiece[][] board;
    private int TILE_SIZE = 50;

    public Pice(PieceColor type, int x, int y) {
        this.type = type;

        move(x, y);

        Ellipse bg = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        bg.setFill(Color.BLACK);

        bg.setStroke(Color.BLACK);
        bg.setStrokeWidth(TILE_SIZE * 0.03);

        bg.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        bg.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2 + TILE_SIZE * 0.07);

        Ellipse ellipse = new Ellipse(TILE_SIZE * 0.3125, TILE_SIZE * 0.26);
        ellipse.setFill(type == PieceColor.BLACK
                ? Color.valueOf("#c40003") : Color.valueOf("#fff9f4"));

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(TILE_SIZE * 0.03);

        ellipse.setTranslateX((TILE_SIZE - TILE_SIZE * 0.3125 * 2) / 2);
        ellipse.setTranslateY((TILE_SIZE - TILE_SIZE * 0.26 * 2) / 2);

        getChildren().addAll(bg, ellipse);

        setOnMousePressed(e -> {
            mouseX = e.getSceneX();
            mouseY = e.getSceneY();
        });
        setOnMouseDragged(e -> {
            relocate(e.getSceneX() - mouseX + oldX, e.getSceneY() - mouseY + oldY);
        });
    }

    private PieceColor type;

    private double mouseX, mouseY;
    private double oldX, oldY;



    public double getOldX() {
        return oldX;
    }

    public double getOldY() {
        return oldY;
    }

    public void move(int x, int y) {
        oldX = x * TILE_SIZE;
        oldY = y * TILE_SIZE;
        relocate(oldX, oldY);
    }

    public void abortMove() {
        relocate(oldX, oldY);
    }


    public PieceColor getColr() {
        return type;
    }
}