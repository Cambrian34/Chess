package Movement;

import javafx.scene.Node;

public  class Draggablemaker {

    private  double mousex,mousey;
    boolean pressed;


    public  void makeDraggable(Node piece){
        piece.setOnMousePressed(e -> {
            mousex = e.getX();
            mousey = e.getY();
        });

        piece.setOnMouseDragged(e -> {
            piece.setLayoutX(e.getSceneX() - mousex);
            piece.setLayoutY(e.getSceneY() - mousey);

        });
    }



}
