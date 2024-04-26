import Pieces.ChessPiece;

import Pieces.PieceColor;
import Pieces.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;

/*
public class CreateChessBoard extends GridPane {

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



    ArrayList<ChessPiece> Pieces = new ArrayList<>();
    ArrayList<ChessPiece> simPieces = new ArrayList<>();


    CreateChessBoard() {
        super();
        //set gridlines to visible
        setGridLinesVisible(true);

        //set size to 8x8
        // Create squares and add to grid
        for (int i = 0; i < 8; i++) {
            // Add numbers using Text
            Text number = new Text(20, i * 50 + 30, String.valueOf(8 - i));
            this.add(number, 8, i);
            // Add letters using Text
            Text letter = new Text(i * 50 + 20, 390, String.valueOf((char) ('a' + i)));
            this.add(letter, i, 8);
        }

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                Rectangle square = new Rectangle(50, 50); // Example size 50x50 pixels
                square.setFill((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
                this.add(square, col, row);
            }
        }

        initializePieces();




    }

    private void initializePieces() {
        //Pawn
        for (int i = 0; i < 8; i++) {
            Pawn wp = new Pawn( PieceColor.WHITE, White_Pawn, i, 6);
            Pieces.add(wp);
            this.add(wp, i, 6);
            Pawn bp = new Pawn( PieceColor.BLACK, Black_Pawn, i, 1);
            this.add(bp, i, 1);
        }
        //Rook
        Rook br= new Rook(PieceColor.BLACK,Black_Rook,0,0);
        Pieces.add(br);
        this.add(br,0,0);
        Rook br2= new Rook(PieceColor.BLACK,Black_Rook,7,0);
        Pieces.add(br2);
        this.add(br2,7,0);
        Rook wr= new Rook(PieceColor.WHITE,White_Rook,0,7);
        Pieces.add(wr);
        this.add(wr,0,7);
        Rook wr2= new Rook(PieceColor.WHITE,White_Rook,7,7);
        Pieces.add(wr2);
        this.add(wr2,7,7);

        // Knights
        Knight bk1 = new Knight(PieceColor.BLACK, Black_Knight, 1, 0);
        Pieces.add(bk1);
        this.add(bk1, 1, 0);
        Knight bk2 = new Knight(PieceColor.BLACK, Black_Knight, 6, 0);
        Pieces.add(bk2);
        this.add(bk2, 6, 0);
        Knight wk1 = new Knight(PieceColor.WHITE, White_Knight, 1, 7);
        Pieces.add(wk1);
        this.add(wk1, 1, 7);
        Knight wk2 = new Knight(PieceColor.WHITE, White_Knight, 6, 7);
        Pieces.add(wk2);
        this.add(wk2, 6, 7);

        // Bishops
        Bishop bb1 = new Bishop(PieceColor.BLACK, Black_Bishop, 2, 0);
        Pieces.add(bb1);
        this.add(bb1, 2, 0);
        Bishop bb2 = new Bishop(PieceColor.BLACK, Black_Bishop, 5, 0);
        Pieces.add(bb2);
        this.add(bb2, 5, 0);
        Bishop wb1 = new Bishop(PieceColor.WHITE, White_Bishop, 2, 7);
        Pieces.add(wb1);
        this.add(wb1, 2, 7);
        Bishop wb2 = new Bishop(PieceColor.WHITE, White_Bishop, 5, 7);
        Pieces.add(wb2);
        this.add(wb2, 5, 7);

        // Queens
        Queen bq = new Queen(PieceColor.BLACK, Black_Queen, 3, 0);
        Pieces.add(bq);
        this.add(bq, 3, 0);
        Queen wq = new Queen(PieceColor.WHITE, White_Queen, 3, 7);
        Pieces.add(wq);
        this.add(wq, 3, 7);

        // Kings
        King bk = new King(PieceColor.BLACK, Black_King, 4, 0);
        Pieces.add(bk);
        this.add(bk, 4, 0);
        King wk = new King(PieceColor.WHITE, White_King, 4, 7);
        Pieces.add(wk);
        this.add(wk, 4, 7);






    }

    //get ArrayList of pieces
    public ArrayList<ChessPiece> getPieces() {
        return Pieces;
    }


    private void placePiece(ChessPiece piece,String imagePath, int col, int row) {
        ChessPiece p;
        //this.add(p, col, row);
    }

    public Rectangle getNodeByRowColumnIndex(final int row, final int column) {
        Rectangle result = null; // Initialize result to null
        for (javafx.scene.Node child : this.getChildren()) {
            if (GridPane.getRowIndex(child) == row && GridPane.getColumnIndex(child) == column) {
                // Check if the child's row and column match
                result = (Rectangle) child; // Cast the Node to a Rectangle
                break; // Exit the loop once you find the match
            }
        }
        return result;
    }





}


 */