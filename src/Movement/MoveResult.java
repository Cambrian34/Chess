package Movement;

import Pieces.Pice;

public class MoveResult {

    private MoveType type;

    public MoveType getType() {
        return type;
    }

    private Pice piece;

    public Pice getPiece() {
        return piece;
    }

    public MoveResult(MoveType type) {
        this(type, null);
    }

    public MoveResult(MoveType type, Pice piece) {
        this.type = type;
        this.piece = piece;
    }
}