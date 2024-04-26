package AI;

import Board.Tile;

import java.util.ArrayList;

public class AIThread extends Thread{
    private boolean running = true; // Flag to control when the AI should stop

   @Override
    public void run() {
        while (running) {
            makeRandomMove();

            // Add a delay to avoid overwhelming the main thread
            try {
                Thread.sleep(1000); // Example delay of 1 second
            } catch (InterruptedException e) {
                // Handle interruptions if needed
            }
        }
    }

    // Method to stop the AI thread
    public void stopThread() {
        running = false;
    }
    Tile [][] board = new Tile[8][8];
    //set current board info from array[][] of tiles
    public void setBoard(Tile[][] tiles){
        //copy the board
        for(int i=0;i<8;i++)
            System.arraycopy(tiles[i], 0, board[i], 0, 8);


    }




       // Method to make a random move
    private void makeRandomMove() {
        // Generate random moves based on the current board state
        //the player is white
        // Iterate through the board to find a random piece to move, piece must be black

    }

}
