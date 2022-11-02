package edu.austral.dissis.chess.ajedrez.movements;
import edu.austral.dissis.chess.ajedrez.Movement;
import edu.austral.dissis.chess.ajedrez.MyPosition;

public class JumpMovement implements Movement {

    private int amount1;
    private int amount2;

    public JumpMovement(int amount1, int amount2) {
        this.amount1 = amount1;
        this.amount2 = amount2;
    }

    @Override
    public int getMaxAmount() {
        return 0;
    }

    public boolean areJump(MyPosition origin, MyPosition destination){
        boolean jump = false;
        int originX = origin.getPositionX();
        int originY = origin.getPositionY();
        int destinationX = destination.getPositionX();
        int destinationY = destination.getPositionY();
        int difRow = originX - destinationX;
        int difCol = originY - destinationY;
        if (((Math.abs(difRow) == amount1) && (Math.abs(difCol) == amount2)) || ((Math.abs(difRow) == amount2) && (Math.abs(difCol) == amount1))) {
            jump = true;
        }
        return jump;
    }
}
