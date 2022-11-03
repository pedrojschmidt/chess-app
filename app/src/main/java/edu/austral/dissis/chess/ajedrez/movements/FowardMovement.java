package edu.austral.dissis.chess.ajedrez.movements;
import edu.austral.dissis.chess.ajedrez.Movement;
import edu.austral.dissis.chess.ajedrez.MyPosition;

public class FowardMovement implements Movement {

    private int maxAmount;

    public FowardMovement (int maxAmount){
        this.maxAmount = maxAmount;
    }

    @Override
    public int getMaxAmount() {
        return maxAmount;
    }

    public boolean areFowardWhite(MyPosition origin, MyPosition destination){
        boolean foward = false;
        int originX = origin.getPositionX();
        int originY = origin.getPositionY();
        int destinationX = destination.getPositionX();
        int destinationY = destination.getPositionY();
        for (int i = 1; i <= maxAmount; i++) {
            if (originX == destinationX && originY + i == destinationY) {
                foward = true;
            }
        }
        return foward;
    }

    public boolean areFowardBlack(MyPosition origin, MyPosition destination){
        boolean foward = false;
        int originX = origin.getPositionX();
        int originY = origin.getPositionY();
        int destinationX = destination.getPositionX();
        int destinationY = destination.getPositionY();
        for (int i = 1; i <= maxAmount; i++) {
            if (originX == destinationX && originY - i == destinationY) {
                foward = true;
            }
        }
        return foward;
    }
}
