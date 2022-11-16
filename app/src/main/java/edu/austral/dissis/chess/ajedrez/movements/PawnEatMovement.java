package edu.austral.dissis.chess.ajedrez.movements;

import edu.austral.dissis.chess.ajedrez.Movement;
import edu.austral.dissis.chess.ajedrez.MyPosition;

public class PawnEatMovement implements Movement {

    private int maxAmount;

    public PawnEatMovement (int maxAmount){
        this.maxAmount = maxAmount;
    }

    @Override
    public int getMaxAmount() {
        return maxAmount;
    }

    public boolean areFowardDiagonalOneWhite(MyPosition origin, MyPosition destination){
        boolean foward = false;
        int originX = origin.getPositionX();
        int originY = origin.getPositionY();
        int destinationX = destination.getPositionX();
        int destinationY = destination.getPositionY();
        if ((originX == destinationX + 1 || originX == destinationX - 1) && originY + 1 == destinationY) {
            foward = true;
        }
        return foward;
    }

    public boolean areFowardDiagonalOneBlack(MyPosition origin, MyPosition destination){
        boolean foward = false;
        int originX = origin.getPositionX();
        int originY = origin.getPositionY();
        int destinationX = destination.getPositionX();
        int destinationY = destination.getPositionY();
        if ((originX == destinationX + 1 || originX == destinationX - 1) && originY - 1 == destinationY) {
            foward = true;
        }
        return foward;
    }
}
