public abstract class Player {


    public String Color;

    public boolean isAi = false;


    public Player(String color, boolean isTurn, boolean isAi) {

        this.Color = color;
        this.isAi = isAi;

    }

    public void setAI() {
        isAi = true;
    }

    public boolean isAI() {
        return isAi;
    }

    public String getColor() {
        return Color;
    }

    public void setColor(String color) {
        Color = color;
    }


}
