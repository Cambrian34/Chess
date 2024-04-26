package Movement;

import javafx.scene.Node;

public class Mouse {

    int x, y;
    boolean pressed;

    public  void makeDraggable(Node piece){
        piece.setOnMousePressed(e -> {
            boolean pressed = true;
        });

        piece.setOnMouseDragged(e -> {
            //get
            x = (int) e.getSceneX();
            y = (int) e.getSceneY();

        });
        piece.setOnMouseReleased(e -> {
            //get release position
            x = (int) e.getSceneX();
            y = (int) e.getSceneY();
            boolean pressed = false;
        });
    }
}
