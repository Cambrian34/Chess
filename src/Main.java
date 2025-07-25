import Board.Chessboard;
import Board.Tile;
import Pieces.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class Main extends Application {

    private Chessboard chessboard;
    private GridPane chessboardPane;
    private PieceColor currentPlayer = PieceColor.WHITE;
    private Tile selectedTile = null;
    private ArrayList<Tile> highlightedTiles = new ArrayList<>();
    private Label turnLabel;
    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Chess Game");
        primaryStage.setScene(createTitleScene());
        primaryStage.show();
    }

    private Scene createTitleScene() {
        AnchorPane titlePane = new AnchorPane();
        titlePane.setPrefSize(600, 400);

        try {
            Image bgImage = new Image(getClass().getResourceAsStream("/art/Backgrd.jpeg"));
            BackgroundImage bg = new BackgroundImage(bgImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, new BackgroundSize(600, 400, false, false, false, false));
            titlePane.setBackground(new Background(bg));
        } catch (Exception e) {
            System.err.println("Cannot load background image: /art/Backgrd.jpeg");
            titlePane.setStyle("-fx-background-color: #333;");
        }

        Label titleLabel = new Label("Chess");
        titleLabel.setFont(Font.font("Impact", FontWeight.BOLD, 70));
        titleLabel.setTextFill(Color.web("#EAEAEA"));
        titleLabel.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.75), 5, 0.5, 2, 2);");
        AnchorPane.setTopAnchor(titleLabel, 50.0);
        AnchorPane.setLeftAnchor(titleLabel, 0.0);
        AnchorPane.setRightAnchor(titleLabel, 0.0);
        titleLabel.setAlignment(Pos.CENTER);

        Button startGameButton = createMenuButton("Start Game");
        startGameButton.setOnAction(e -> primaryStage.setScene(createGameScene()));

        Button howToPlayButton = createMenuButton("How to Play");
        howToPlayButton.setOnAction(e -> showInfoDialog("How to Play", "Select a piece and click a highlighted square to move.\nCapture all of your opponent's pieces to win!"));

        Button infoButton = createMenuButton("Info");
        infoButton.setOnAction(e -> showInfoDialog("Info", "Alistair Chambers"));

        Button exitButton = createMenuButton("Exit");
        exitButton.setOnAction(e -> Platform.exit());

        HBox bottomButtons = new HBox(20, infoButton, exitButton);
        bottomButtons.setAlignment(Pos.CENTER);

        VBox menuVBox = new VBox(15, startGameButton, howToPlayButton, bottomButtons);
        menuVBox.setAlignment(Pos.CENTER);

        titlePane.getChildren().addAll(titleLabel, menuVBox);
        AnchorPane.setBottomAnchor(menuVBox, 40.0);
        AnchorPane.setLeftAnchor(menuVBox, 0.0);
        AnchorPane.setRightAnchor(menuVBox, 0.0);

        return new Scene(titlePane);
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        button.setPrefWidth(200);
        button.setStyle("-fx-background-color: #E0E0E0; -fx-border-color: #B0B0B0; -fx-border-width: 2; -fx-background-radius: 5; -fx-border-radius: 5;");
        return button;
    }

    private void showInfoDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private Scene createGameScene() {
        BorderPane gamePane = new BorderPane();
        gamePane.setStyle("-fx-background-color: #333;");

        chessboardPane = createChessboardPane();
        gamePane.setCenter(chessboardPane);

        turnLabel = new Label("White's Turn");
        turnLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        turnLabel.setTextFill(Color.WHITE);

        Button pauseButton = new Button();
        try {
            ImageView pauseIcon = new ImageView(new Image(getClass().getResourceAsStream("/art/PauseBtn.png")));
            pauseIcon.setFitHeight(30);
            pauseIcon.setFitWidth(30);
            pauseButton.setGraphic(pauseIcon);
        } catch (Exception e) {
            pauseButton.setText("Pause");
        }
        pauseButton.setStyle("-fx-background-color: transparent;");
        pauseButton.setOnAction(e -> showPauseMenu());

        HBox topBox = new HBox(150, new Label(), turnLabel, pauseButton); // Spacers
        topBox.setAlignment(Pos.CENTER);
        topBox.setPadding(new Insets(10));
        gamePane.setTop(topBox);

        return new Scene(gamePane, 640, 680);
    }

    private void showPauseMenu() {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.initStyle(StageStyle.UNDECORATED);

        Label pauseTitle = new Label("Paused");
        pauseTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Button resumeButton = createMenuButton("Resume");
        resumeButton.setOnAction(e -> dialog.close());

        Button resignButton = createMenuButton("Resign");
        resignButton.setOnAction(e -> {
            dialog.close();
            showGameOverDialog((currentPlayer == PieceColor.WHITE ? "Black" : "White") + " Wins by Resignation!");
        });

        Button exitButton = createMenuButton("Exit to Title");
        exitButton.setOnAction(e -> {
            dialog.close();
            primaryStage.setScene(createTitleScene());
        });

        VBox pauseVBox = new VBox(15, pauseTitle, resumeButton, resignButton, exitButton);
        pauseVBox.setAlignment(Pos.CENTER);
        pauseVBox.setPadding(new Insets(20));
        pauseVBox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.9); -fx-border-color: black; -fx-border-width: 2;");

        Scene dialogScene = new Scene(pauseVBox);
        dialog.setScene(dialogScene);
        dialog.showAndWait();
    }

    private GridPane createChessboardPane() {
        chessboard = new Chessboard();
        GridPane pane = new GridPane();
        pane.setAlignment(Pos.CENTER);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Tile tile = chessboard.getTile(row, col);
                tile.setOnMouseClicked(event -> handleTileClick(tile));
                pane.add(tile, col, row);
            }
        }
        return pane;
    }

    private void handleTileClick(Tile tile) {
        if (selectedTile == null) {
            if (tile.hasPiece() && tile.getPiece().getColor() == currentPlayer) {
                selectedTile = tile;
                tile.select();
                highlightValidMoves(tile);
            }
        } else {
            if (highlightedTiles.contains(tile)) {
                PieceColor movingPlayerColor = selectedTile.getPiece().getColor();
                ChessPiece pieceThatMoved = selectedTile.getPiece();
                ChessPiece capturedPiece = tile.getPiece();
                chessboard.movePiece(selectedTile, tile);

                if (chessboard.isKingInCheck(movingPlayerColor)) {
                    chessboard.movePiece(tile, selectedTile);
                    if (capturedPiece != null) {
                        tile.setPiece(capturedPiece);
                    }
                } else {
                    checkForPawnPromotion(tile, pieceThatMoved);
                    switchPlayer();
                }
            }
            selectedTile.deselect();
            clearHighlights();
            selectedTile = null;
        }
    }

    private void checkForPawnPromotion(Tile endTile, ChessPiece movedPiece) {
        if (movedPiece.getType() == PieceType.PAWN) {
            int endRow = endTile.getYPos();
            if ((movedPiece.getColor() == PieceColor.WHITE && endRow == 0) || (movedPiece.getColor() == PieceColor.BLACK && endRow == 7)) {
                showPromotionDialog(endTile);
            }
        }
    }

    private void showPromotionDialog(Tile pawnTile) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        dialog.initStyle(StageStyle.UNDECORATED);

        PieceColor color = pawnTile.getPiece().getColor();

        Button queenButton = new Button("Queen");
        queenButton.setOnAction(e -> {
            pawnTile.setPiece(new Queen(color, (color == PieceColor.WHITE ? "art/wqueen.png" : "art/bqueen.png"), pawnTile.getXPos(), pawnTile.getYPos()));
            dialog.close();
        });
        Button rookButton = new Button("Rook");
        rookButton.setOnAction(e -> {
            pawnTile.setPiece(new Rook(color, (color == PieceColor.WHITE ? "art/wrook.png" : "art/brook.png"), pawnTile.getXPos(), pawnTile.getYPos()));
            dialog.close();
        });
        Button bishopButton = new Button("Bishop");
        bishopButton.setOnAction(e -> {
            pawnTile.setPiece(new Bishop(color, (color == PieceColor.WHITE ? "art/wbishop.png" : "art/bbishop.png"), pawnTile.getXPos(), pawnTile.getYPos()));
            dialog.close();
        });
        Button knightButton = new Button("Knight");
        knightButton.setOnAction(e -> {
            pawnTile.setPiece(new Knight(color, (color == PieceColor.WHITE ? "art/wknight.png" : "art/bknight.png"), pawnTile.getXPos(), pawnTile.getYPos()));
            dialog.close();
        });

        HBox buttonBox = new HBox(10, queenButton, rookButton, bishopButton, knightButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));
        buttonBox.setStyle("-fx-background-color: white; -fx-border-color: black;");

        Scene dialogScene = new Scene(buttonBox);
        dialog.setScene(dialogScene);
        dialog.showAndWait(); // This pauses execution until a choice is made
    }


    private void highlightValidMoves(Tile originTile) {
        ChessPiece piece = originTile.getPiece();
        if (piece != null) {
            ArrayList<Tile> moves = piece.move(chessboard.getBoard(), originTile.getYPos(), originTile.getXPos());
            highlightedTiles.addAll(moves);
            for (Tile tile : highlightedTiles) {
                tile.setpossibleMove();
            }
        }
    }

    private void clearHighlights() {
        for (Tile tile : highlightedTiles) {
            tile.unsetpossibleMove();
        }
        highlightedTiles.clear();
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == PieceColor.WHITE) ? PieceColor.BLACK : PieceColor.WHITE;
        turnLabel.setText(currentPlayer.toString() + "'s Turn");

        if (chessboard.isCheckmate(currentPlayer)) {
            showGameOverDialog((currentPlayer == PieceColor.WHITE ? "Black" : "White") + " Wins by Checkmate!");
        } else if (chessboard.isStalemate(currentPlayer)) {
            showGameOverDialog("Stalemate! The game is a draw.");
        }
    }

    private void resetGame() {
        primaryStage.setScene(createGameScene());
    }

    private void showGameOverDialog(String message) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);

        VBox dialogVBox = new VBox(20);
        dialogVBox.setAlignment(Pos.CENTER);
        dialogVBox.setPadding(new Insets(20));
        dialogVBox.getChildren().add(new Label(message));

        Button playAgainButton = new Button("Play Again");
        playAgainButton.setOnAction(e -> {
            dialog.close();
            resetGame();
        });

        Button exitButton = new Button("Exit to Title");
        exitButton.setOnAction(e -> {
            dialog.close();
            primaryStage.setScene(createTitleScene());
        });

        HBox buttonBox = new HBox(15, playAgainButton, exitButton);
        buttonBox.setAlignment(Pos.CENTER);
        dialogVBox.getChildren().add(buttonBox);

        Scene dialogScene = new Scene(dialogVBox, 350, 150);
        dialog.setScene(dialogScene);
        dialog.setTitle("Game Over");
        dialog.showAndWait();
    }
}

/*
public class Main extends Application {



    //turn
    private Turn turn = Turn.WHITE;
    //Pause button
    //location: art/PauseBtn.png
    String PauseBtn = "art/PauseBtn.png";
    ImageView pauseb = new ImageView(PauseBtn);
    private Chessboard chessboard;
    private GridPane boardPane;

    private GridPane chessboardPane;
    //
    private ChessPiece selectedPiece = null;
    private int selectedRow = -1;
    private int selectedCol = -1;
    //current player
    private PieceColor currentPlayer = PieceColor.WHITE;

    public static final int TILE_SIZE = 100;
    public static final int WIDTH = 8;
    public static final int HEIGHT = 8;

    private Tile[][] board;

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();

    //current piece
     ChessPiece currentPiece;

     //pane
    GridPane pane = new GridPane();




    private ArrayList<Tile> destination = new ArrayList<>();
    private boolean test = true;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {





        //boardPane.setGridLinesVisible(true);
        //chessboardPane.setGridLinesVisible(true);
        //create a boardpane under the boardPane for the chessboard checkered pattern
        //a stackpane to hold the boardPane and the chessboard
        StackPane boardPane2 = new StackPane();
        //boardPane2.getChildren().addAll(chessboardPane, boardPane);


        Button pause = new Button();
        Popup popup = new Popup();
        Button resume = new Button("Resume");
        Button resign = new Button("Resign");
        Button exit = new Button("Exit to Title Screen");
        HBox hbox = new HBox(resume, resign, exit);
        popup.getContent().addAll(hbox);
        pause.setOnAction(e -> popup.show(primaryStage));
        resume.setOnAction(e -> popup.hide());
        //resign.setOnAction(e ->
        //primaryStage.setScene(new Scene(boardPane2, 400, 400))
        // );

        pauseb.setFitWidth(50);
        pauseb.setFitHeight(50);
        pauseb.setPreserveRatio(true);
        //set image to the pause button
        pause.setGraphic(pauseb);

        //4 buttons: start game,How to play , Info, exit
        Button startGame = new Button("Start Game");
        startGame.setOnAction(e -> primaryStage.setScene(new Scene(boardPane2, 400, 400)));
        //startGame.setOnAction(e -> primaryStage.setScene(new Scene(boardPane2, 400, 400)));
        Button HowToPlay = new Button("How to play");
        Button Info = new Button("Info");
        Button Exit = new Button("Exit");
        //Exit.setStyle();
        HBox hbox1 = new HBox(Info, Exit);
        //add padding
        hbox1.setSpacing(7);
        VBox vbox = new VBox(startGame, HowToPlay, hbox1);
        vbox.setSpacing(20);
        // set vbox to bottom left
        vbox.setLayoutX(400);
        vbox.setLayoutY(275);

        //import an image from /art/Backgrd.jpeg
        //set size of the image to the size of the window
        Image image = new Image("art/Backgrd.jpeg", 600, 400, false, true);
        
        

        //make the background for the title screen
        BackgroundImage bg = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
        Background background = new Background(bg);
        //vbox.setBackground(background);
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(vbox);
        //set vbox to bottom right
        vbox.setAlignment(javafx.geometry.Pos.BOTTOM_RIGHT);


        //Add title text - Chess
        TextField title = new TextField("Chess");
        //not fully visible due to the background
        title.setEditable(false);
        //title.setStyle(";");
        title.setLayoutX(200);
        title.setLayoutY(50);
        //remove the border
        title.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-font-size: 50px; -fx-font-weight: bold; -fx-text-fill: GREY; -fx-font-family:'Impact' ;");
        anchorPane.getChildren().add(title);
        //set background to BackgroundImage
        anchorPane.setBackground(background);








        Group root = new Group(anchorPane);

        Scene TitleScene = new Scene(root, 600, 400);

        

        //exit button: exit to title screen and close the pop up window
        exit.setOnAction(e -> {
            primaryStage.setScene(TitleScene);
            popup.hide();
        });


        Group boardPane3 = new Group(boardPane2);
        //add pause button to the top right
        //boardPane3.getChildren().add(1, vbox3);

        Scene scene = new Scene(boardPane3, 600, 400);
        primaryStage.setTitle("Chess Game");
        //primaryStage.setScene(scene);

        startGame.setOnAction(e -> {
            chessboardPane = createContent();
            HBox vb = new HBox(chessboardPane,pause);
            primaryStage.setScene(new Scene(vb, 600, 400));
        });



        primaryStage.setScene(TitleScene);
        primaryStage.show();
    }




    //grid pane for the chessboard
    public GridPane createContent() {
        String White_Pawn = "art/wpawn.png";
        String Black_Pawn = "art/bpawn.png";
        String White_Rook = "art/wrook.png";
        String Black_Rook = "art/brook.png";
        String White_Knight = "art/wknight.png";
        String Black_Knight = "art/bknight.png";
        String White_Bishop = "art/wbishop.png";
        String Black_Bishop = "art/bbishop.png";
        String White_Queen = "art/wqueen.png";
        String Black_Queen = "art/bqueen.png";
        String White_King = "art/wking.png";
        String Black_King = "art/bking.png";
        GridPane panecre = new GridPane();
        panecre.setPrefSize(400, 400);
        panecre.setStyle("-fx-background-color: #d3d3d3;");
        //set line
        panecre.setGridLinesVisible(true);
    //board state
        try {
            board = new Tile[8][8];
            for (int y = 0; y < 8; y++) {
                for (int x = 0; x < 8; x++) {
                    Tile tile = new Tile(x, y, null);


                    //set the pieces on the board
                    //black pawns
                    if (y == 1) {
                        if (!tile.hasPiece()) {
                            tile.setPiece(new Pawn(PieceColor.BLACK, Black_Pawn, x, y));
                            onclickmov(tile);
                        }
                    }
                    //white pawns
                    if (y == 6) {
                        if (!tile.hasPiece()) {
                            tile.setPiece(new Pawn(PieceColor.WHITE, White_Pawn, x, y));
                            onclickmov(tile);
                        }
                    }

                    //black knights
                    if (y == 0 && (x == 1 || x == 6)) {
                        //check if the tile has a piece
                        if (!tile.hasPiece()) {
                            tile.setPiece(new Knight(PieceColor.BLACK, Black_Knight, x, y));
                            onclickmov(tile);
                        }
                    }
                    //white knights
                    if (y == 7 && (x == 1 || x == 6)) {
                        if (!tile.hasPiece()) {
                            tile.setPiece(new Knight(PieceColor.WHITE, White_Knight, x, y));
                            onclickmov(tile);
                        }
                    }
                    //add black rooks
                    if (y == 0 && (x == 0 || x == 7)) {
                        if (!tile.hasPiece()) {
                            tile.setPiece(new Rook(PieceColor.BLACK, Black_Rook, x, y));
                            onclickmov(tile);
                        }
                    }
                    //add white rooks
                    if (y == 7 && (x == 0 || x == 7)) {
                        if (!tile.hasPiece()) {
                            tile.setPiece(new Rook(PieceColor.WHITE, White_Rook, x, y));
                            onclickmov(tile);
                        }
                    }
                    //add black bishops
                    if (y == 0 && (x == 2 || x == 5)) {
                        if (!tile.hasPiece()) {
                            tile.setPiece(new Bishop(PieceColor.BLACK, Black_Bishop, x, y));
                            onclickmov(tile);
                        }
                    }
                    //add white bishops
                    if (y == 7 && (x == 2 || x == 5)) {
                        if (!tile.hasPiece()) {
                            tile.setPiece(new Bishop(PieceColor.WHITE, White_Bishop, x, y));
                            onclickmov(tile);
                        }
                    }
                    //add black queen
                    if (y == 0 && x == 3) {
                        if (!tile.hasPiece()) {
                            tile.setPiece(new Queen(PieceColor.BLACK, Black_Queen, x, y));
                            onclickmov(tile);
                        }
                    }
                    //add white queen
                    if (y == 7 && x == 3) {
                        if (!tile.hasPiece()) {
                            tile.setPiece(new Queen(PieceColor.WHITE, White_Queen, x, y));
                            onclickmov(tile);
                        }
                    }
                    //add black king
                    if (y == 0 && x == 4) {
                        if (!tile.hasPiece()) {
                            tile.setPiece(new King(PieceColor.BLACK, Black_King, x, y));
                            onclickmov(tile);
                        }
                    }
                    //add white king
                    if (y == 7 && x == 4) {
                        if (!tile.hasPiece()) {
                            tile.setPiece(new King(PieceColor.WHITE, White_King, x, y));
                            onclickmov(tile);
                        }
                    }


                    panecre.add(tile, x, y);
                    board[x][y] = tile;
                }
            }
        }
        catch (Exception e){

        }


        //set the pieces on the board


        return panecre;
    }

    private void onclickmov(Tile tile) {
        //event handler for the mouse click event
    	if (currentPlayer == PieceColor.WHITE) {
    	    currentPlayer = PieceColor.BLACK;
    	} else {
    	    currentPlayer = PieceColor.WHITE;
    	}
        tile.setOnMouseClicked(event -> {
            //check whose turn it is
                    if (tile.hasPiece() && tile.getPiece().getColor() == currentPlayer) {


                        int row;
                        int col;
                        //can select or deselect a piece

                        if (!tile.isSelected()) {

                            //check if the tile has a piece
                            if (tile.hasPiece()) {
                                tile.select();
                                //get the piece
                                currentPiece = tile.getPiece();
                                //check if user clicked on the piece again then deselect
                                if (selectedPiece == currentPiece) {
                                    tile.deselect();
                                    clearHighlightedTiles();
                                    selectedPiece = null;
                                    selectedRow = -1;
                                    selectedCol = -1;
                                    return;
                                }
                                //get the possible moves
                                switch (currentPiece.getType()) {
                                    case PAWN:

                                        //col +1 or col -1 or col +2 or col -2
                                        //check location of the piece
                                        row = GridPane.getRowIndex(tile);
                                        col = GridPane.getColumnIndex(tile);

                                        //check for second tile click within range row +1 or row -1 or row +2 or row -2
                                        //if tile is white piece
                                        if (currentPiece.getColor() == PieceColor.WHITE) {
                                            //check if the piece is at the starting position
                                            if (row == 6) {
                                                //check if the tile is empty
                                                if (board[col][row - 1].getPiece() == null) {
                                                    //check if the tile is empty
                                                    if (board[col][row - 2].getPiece() == null) {
                                                        //add the possible moves
                                                        destination.add(board[col][row - 1]);
                                                        destination.add(board[col][row - 2]);
                                                    } else {
                                                        destination.add(board[col][row - 1]);
                                                    }
                                                } else {
                                                    destination.add(board[col][row - 1]);
                                                }
                                            } else {
                                                //check if the tile is empty
                                                if (board[col][row - 1].getPiece() == null) {
                                                    destination.add(board[col][row - 1]);
                                                }
                                            }
                                        }
                                        //if tile is black piece
                                        else {
                                            //check if the piece is at the starting position
                                            if (row == 1) {
                                                //check if the tile is empty
                                                if (board[col][row + 1].getPiece() == null) {
                                                    //check if the tile is empty
                                                    if (board[col][row + 2].getPiece() == null) {
                                                        //add the possible moves
                                                        destination.add(board[col][row + 1]);
                                                        destination.add(board[col][row + 2]);
                                                    } else {
                                                        destination.add(board[col][row + 1]);
                                                    }
                                                } else {
                                                    destination.add(board[col][row + 1]);
                                                }
                                            } else {
                                                //check if the tile is empty
                                                if (board[col][row + 1].getPiece() == null) {
                                                    destination.add(board[col][row + 1]);
                                                }
                                            }
                                        }


                                        // Set up mouse click event handlers outside the loop
                                        for (Tile t : destination) {
                                            t.setpossibleMove();
                                            t.setOnMouseClicked(event1 -> {
                                                Tile clickedTile = (Tile) event1.getSource();
                                                // Move the piece to the clicked tile
                                                clickedTile.setPiece(currentPiece);
                                                tile.unsetpossibleMove();
                                                tile.deselect();
                                                tile.removePiece();
                                                clearHighlightedTiles();
                                                clickedTile.unsetpossibleMove();
                                                // Update the board

                                                // Clear destination array
                                                destination.clear();

                                                // Remove event handlers for remaining tiles
                                                for (Tile otherTile : destination) {
                                                    otherTile.setOnMouseClicked(null);
                                                    tile.unsetpossibleMove();
                                                    tile.deselect();
                                                }

                                            });



                                }
                                        destination.clear();




                                        break;
                                    case ROOK:
                                        //show the possible moves
                                        //check location of the piece
                                        row = GridPane.getRowIndex(tile);
                                        col = GridPane.getColumnIndex(tile);

                                        // if it is at certain location, it can move to certain locations
                                        //else it wouldnt be able to move
                                        destination = tile.getPiece().move(board, row, col);
                                        for (Tile t : destination) {
                                            t.setpossibleMove();
                                            t.setOnMouseClicked(event1 -> {
                                                // Move the piece to the destination
                                                t.setPiece(currentPiece);
                                                tile.unsetpossibleMove();
                                                tile.deselect();
                                                tile.removePiece();
                                                clearHighlightedTiles();
                                                t.unsetpossibleMove();
                                                //update the board

                                                //clear destination array
                                                destination.clear();


                                            });
                                        }


                                        break;
                                    case KNIGHT:
                                        //show the possible moves
                                        //l shape

                                        //check location of the piece
                                        //whcih grid is the piece on
                                        row = GridPane.getRowIndex(tile);
                                        col = GridPane.getColumnIndex(tile);
                                        // if it is at certain location, it can move to certain locations
                                        //else it wouldnt be able to move

                                        //moves
                                        int[][] knightMoves = {
                                                {2, 1}, {2, -1}, {1, 2}, {1, -2},
                                                {-2, 1}, {-2, -1}, {-1, 2}, {-1, -2}
                                        };

                                        destination = tile.getPiece().move(board, row, col);
                                        //print array list current board
                                        for (int i = 0; i < board.length; i++) {
                                            for (int j = 0; j < board[i].length; j++) {
                                                Tile til = board[j][i];
                                                System.out.println("Tile at position (" + i + ", " + j + "):");
                                                System.out.println("Piece: " + til.getPiece()); // Assuming there's a getPiece() method in Tile class
                                                // Print other variables using their respective getter methods
                                            }
                                        }

                                        for (Tile t : destination) {
                                            t.setpossibleMove();
                                            t.setOnMouseClicked(event1 -> {
                                                // Move the piece to the destination
                                                t.setPiece(currentPiece);
                                                tile.unsetpossibleMove();
                                                tile.deselect();
                                                tile.removePiece();
                                                clearHighlightedTiles();
                                                t.unsetpossibleMove();
                                                //update the board


                                                //clear destination array
                                                destination.clear();



                                            });
                                        }


                                        break;
                                    case BISHOP:
                                        row = GridPane.getRowIndex(tile);
                                        col = GridPane.getColumnIndex(tile);
                                        // if it is at certain location, it can move to certain locations
                                        //else it wouldnt be able to move
                                        destination = tile.getPiece().move(board, row, col);
                                        for (Tile t : destination) {
                                            t.setpossibleMove();
                                            t.setOnMouseClicked(event1 -> {
                                                // Move the piece to the destination
                                                t.setPiece(currentPiece);
                                                tile.unsetpossibleMove();
                                                tile.deselect();
                                                tile.removePiece();
                                                clearHighlightedTiles();
                                                t.unsetpossibleMove();
                                                //update the board

                                                //clear destination array
                                                destination.clear();

                                            });
                                        }


                                        break;
                                    case QUEEN:
                                        row = GridPane.getRowIndex(tile);
                                        col = GridPane.getColumnIndex(tile);
                                        destination = tile.getPiece().move(board, row, col);
                                        for (Tile t : destination) {
                                            t.setpossibleMove();
                                            t.setOnMouseClicked(event1 -> {
                                                // Move the piece to the destination
                                                t.setPiece(currentPiece);
                                                tile.unsetpossibleMove();
                                                tile.deselect();
                                                tile.removePiece();
                                                clearHighlightedTiles();
                                                t.unsetpossibleMove();
                                                //update the board

                                                //clear destination array
                                                destination.clear();

                                            });
                                        }
                                        break;
                                    case KING:
                                        row = GridPane.getRowIndex(tile);
                                        col = GridPane.getColumnIndex(tile);
                                        //king
                                        destination = tile.getPiece().move(board, row, col);

                                        //show the possible moves
                                        for (Tile t : destination) {
                                            t.setpossibleMove();
                                            t.setOnMouseClicked(event1 -> {
                                                // Move the piece to the destination
                                                t.setPiece(currentPiece);
                                                tile.unsetpossibleMove();
                                                tile.deselect();
                                                tile.removePiece();
                                                clearHighlightedTiles();
                                                t.unsetpossibleMove();
                                                //update the board

                                                //clear destination array
                                                destination.clear();

                                            });
                                        }

                                        break;
                                }
                            }
                            //change the current player
                            if (currentPlayer == PieceColor.WHITE) {
                                currentPlayer = PieceColor.BLACK;
                            } else {
                                currentPlayer = PieceColor.WHITE;
                            }

                        } else {
                            tile.deselect();
                            clearHighlightedTiles();


                        }
                    }

        });

    }



    // Method to clear previous highlighting
    private void clearHighlightedTiles() {
        // Iterate through all tiles and clear any highlighting
        for (Node node : pane.getChildren()) {
            if (node instanceof Tile) {
                ((Tile) node).unsetpossibleMove();
                ((Tile) node).deselect();

            }
        }
    }


    private void drawBoard() {


        // Draw the checkered pattern on the chessboardPane
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ImageView imageView = new ImageView();
                // Alternate between black and white squares
                if ((row + col) % 2 == 0) {
                    imageView.setImage(new Image("file:art/Solid_white.png"));
                } else {
                    imageView.setImage(new Image("file:art/Solid_black.png"));
                }
                // Set the size of the ImageView
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                // Add the ImageView to the chessboardPane
                chessboardPane.add(imageView, col, row);
            }
        }

        // Draw the chess pieces on the boardPane
        for (int row = 0; row < chessboard.SIZE; row++) {
            for (int col = 0; col < chessboard.SIZE; col++) {
                ImageView imageView = new ImageView();
                ChessPiece piece = chessboard.getPiece(row, col);

                if (piece != null) {
                    imageView.setImage(piece.getImage());

                }
                // Set the size of the ImageView
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                //make draggable
                Draggable dm = new Draggable(imageView);
                assert piece != null;
                //dm.makeDraggable(imageView);
                // Add the ImageView to the boardPane

                boardPane.add(imageView, col, row);
            }
        }
    }

    private String getPieceImage(ChessPiece piece) {
        // Return the image path for the given chess piece
        if (piece == null) {
            return "empty_square_image_path";
        }

        switch (piece.getType()) {
            case PAWN:
                return piece.getColor() == PieceColor.WHITE ? "file:art/wpawn.png" : "file:art/bpawn.png";
            case ROOK:
                return piece.getColor() == PieceColor.WHITE ? "file:art/wrook.png" : "file:art/brook.png";
            case KNIGHT:
                return piece.getColor() == PieceColor.WHITE ? "file:art/wknight.png" : "file:art/bknight.png";
            case BISHOP:
                return piece.getColor() == PieceColor.WHITE ? "file:art/wbishop.png" : "file:art/bbishop.png";
            case QUEEN:
                return piece.getColor() == PieceColor.WHITE ? "file:art/wqueen.png" : "file:art/bqueen.png";
            case KING:
                return piece.getColor() == PieceColor.WHITE ? "file:art/wking.png" : "file:art/bking.png";
            default:
                return "empty_square_image_path";
        }
    }





}*/
