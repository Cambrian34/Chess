package AI;

import Pieces.PieceColor;

public class Particpant {

    public boolean isWhite;
    private String name;
        private PieceColor color;

        public Particpant(String name, PieceColor color) {
            this.name = name;
            this.color = color;
        }

        public String getName() {
            return name;
        }

        public boolean getColor() {
            if (color == PieceColor.WHITE) {
                return true;
            } else {
                return false;
            }

        }



}
