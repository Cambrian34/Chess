package Movement;

import javafx.scene.Node;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.net.URI;

public class Draggable {
    Node piece;
    //create virtual coordinates for the piece 8x8
    //50  50 dimensions
    int[][] grid = new int[8][8];

    //possible moves for knight
    private static final int[][] KNIGHT_MOVES = {
            {2, 1}, {2, -1}, {-2, 1}, {-2, -1},
            {1, 2}, {1, -2}, {-1, 2}, {-1, -2}
    };

    private int starty;
    private int startx;

    public Draggable(Node piece) {
        this.piece = piece;
        makeDraggable(piece);

    }
    //create 50x50 grid

    private void makeDraggable(Node node) {
        node.setOnMousePressed(e -> {
            startx = (int) (e.getSceneX() - node.getTranslateX());
            starty = (int) (e.getSceneY() - node.getTranslateY());

        });

        node.setOnMouseDragged(e -> {
            node.setTranslateX(e.getSceneX() - startx);
            node.setTranslateY(e.getSceneY() - starty);
        });

        node.setOnMouseReleased(e -> {
            //snap to grid
            int x = (int) node.getTranslateX() / 50;
            int y = (int) node.getTranslateY() / 50;
            node.setTranslateX(x * 50);
            node.setTranslateY(y * 50);

            //make click sound when piece is moved
            //sound for piece movement
            String sound = "moveclick.mp3";
            //URI is not hierarchical
            //use try catch block
            URI uri = new File(sound).toURI();


            Media soundFile = new Media(uri.toString());
            MediaPlayer mediaPlayer = new MediaPlayer(soundFile);
            mediaPlayer.play();
        });

    }


}
