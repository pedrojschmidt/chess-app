package edu.austral.dissis.chess.ajedrez.movements;
import edu.austral.dissis.chess.ajedrez.Movement;
import edu.austral.dissis.chess.ajedrez.MyPosition;

public class CrossMovement implements Movement {

    private int maxAmount;

    public CrossMovement (int maxAmount){
        this.maxAmount = maxAmount;
    }

    @Override
    public int getMaxAmount() {
        return maxAmount;
    }

    public boolean areCross(MyPosition origin, MyPosition destination){
        boolean cross = false;
        int originX = origin.getPositionX();
        int originY = origin.getPositionY();
        int destinationX = destination.getPositionX();
        int destinationY = destination.getPositionY();
        if (originX == destinationX || originY == destinationY) {
            cross = true;
        }
        return cross;
    }

    public boolean areCrossUp(MyPosition origin, MyPosition destination){
        boolean cross = false;
        int originX = origin.getPositionX();
        int originY = origin.getPositionY();
        int destinationX = destination.getPositionX();
        int destinationY = destination.getPositionY();
        if (originX == destinationX && originY < destinationY) {
            cross = true;
        }
        return cross;
    }

    public boolean areCrossDown(MyPosition origin, MyPosition destination){
        boolean cross = false;
        int originX = origin.getPositionX();
        int originY = origin.getPositionY();
        int destinationX = destination.getPositionX();
        int destinationY = destination.getPositionY();
        if (originX == destinationX && originY > destinationY) {
            cross = true;
        }
        return cross;
    }

    public boolean areCrossRight(MyPosition origin, MyPosition destination){
        boolean cross = false;
        int originX = origin.getPositionX();
        int originY = origin.getPositionY();
        int destinationX = destination.getPositionX();
        int destinationY = destination.getPositionY();
        if (originX < destinationX && originY == destinationY) {
            cross = true;
        }
        return cross;
    }

    public boolean areCrossLeft(MyPosition origin, MyPosition destination){
        boolean cross = false;
        int originX = origin.getPositionX();
        int originY = origin.getPositionY();
        int destinationX = destination.getPositionX();
        int destinationY = destination.getPositionY();
        if (originX > destinationX && originY == destinationY) {
            cross = true;
        }
        return cross;
    }
}
