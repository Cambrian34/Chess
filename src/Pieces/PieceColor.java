package Pieces;

public enum PieceColor {
    WHITE(-1), BLACK(1);

    public final int direction;

    PieceColor(int direction) {
        this.direction = direction;
    }

}
