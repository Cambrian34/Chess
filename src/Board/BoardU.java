package Board;

import javafx.scene.layout.GridPane;

public class BoardU {

    //creates a 8x8 board using the tile class
    public BoardU(){

    }

    //create the empty board
    public void createBoard(){
        GridPane pane = new GridPane();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Tile emptytile = new Tile(i,j,null);
                pane.add(emptytile,i,j);

            }

        }
    }

}
