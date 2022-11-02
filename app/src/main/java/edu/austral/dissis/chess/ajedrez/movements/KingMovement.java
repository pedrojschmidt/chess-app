package edu.austral.dissis.chess.ajedrez.movements;

import edu.austral.dissis.chess.ajedrez.Movement;
import edu.austral.dissis.chess.ajedrez.MyPosition;

public class KingMovement implements Movement {

    private int maxAmount;

    public KingMovement (int maxAmount){
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
        if (originX == destinationX && originY + 1 == destinationY) {
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
        if (originX == destinationX && originY - 1 == destinationY) {
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
        if (originX + 1 == destinationX && originY == destinationY) {
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
        if (originX - 1 == destinationX && originY == destinationY) {
            cross = true;
        }
        return cross;
    }

    public boolean areDiagonal(MyPosition origin, MyPosition destination){
        boolean diagonal = false;
        int difRow = Math.abs(origin.getPositionX() - destination.getPositionX());
        int difCol = Math.abs(origin.getPositionY() - destination.getPositionY());
        if (difRow == difCol) {
            diagonal = true;
        }
        return diagonal;
    }

    public boolean areDiagonalUpRight(MyPosition origin, MyPosition destination) {
        boolean diagonal = false;
        int oX = origin.getPositionX();
        int oY = origin.getPositionY();
        int dX = destination.getPositionX();
        int dY = destination.getPositionY();
        for (int i = 1; i <= maxAmount; i++) {
            boolean upRight = oX + i == dX && oY + i == dY;
            if (upRight) {
                diagonal = true;
                break;
            }
        }
        return diagonal;
    }

    public boolean areDiagonalUpLeft(MyPosition origin, MyPosition destination) {
        boolean diagonal = false;
        int oX = origin.getPositionX();
        int oY = origin.getPositionY();
        int dX = destination.getPositionX();
        int dY = destination.getPositionY();
        for (int i = 1; i <= maxAmount; i++) {
            boolean upLeft = oX - i == dX && oY + i == dY;
            if (upLeft) {
                diagonal = true;
                break;
            }
        }
        return diagonal;
    }

    public boolean areDiagonalDownRight(MyPosition origin, MyPosition destination) {
        boolean diagonal = false;
        int oX = origin.getPositionX();
        int oY = origin.getPositionY();
        int dX = destination.getPositionX();
        int dY = destination.getPositionY();
        for (int i = 1; i <= maxAmount; i++) {
            boolean downRight = oX + i == dX && oY - i == dY;
            if (downRight) {
                diagonal = true;
                break;
            }
        }
        return diagonal;
    }

    public boolean areDiagonalDownLeft(MyPosition origin, MyPosition destination) {
        boolean diagonal = false;
        int oX = origin.getPositionX();
        int oY = origin.getPositionY();
        int dX = destination.getPositionX();
        int dY = destination.getPositionY();
        for (int i = 1; i <= maxAmount; i++) {
            boolean downLeft = oX - i == dX && oY - i == dY;
            if (downLeft) {
                diagonal = true;
                break;
            }
        }
        return diagonal;
    }
}
