package edu.austral.dissis.chess.ajedrez;

public class MyPosition {

    private int positionX;
    private int positionY;

    public MyPosition(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    @Override
    public boolean equals(Object other){
        MyPosition pos = (MyPosition) other;
        if (pos != null) {
            if (pos.getPositionX() == positionX && pos.getPositionY() == positionY){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

}
