package edu.austral.dissis.chess.ajedrez.pieces;
import edu.austral.dissis.chess.ajedrez.*;
import edu.austral.dissis.chess.ajedrez.movements.AllWayMovement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Queen implements Piece {

    private MyPosition myPosition;
    private final String name = "queen";
    private Color color;
    private boolean alive;
    //Sirve como piece id
    private final MyPosition initialMyPosition;

    private AllWayMovement movement;

    public Queen(MyPosition initialMyPosition, Color color, boolean alive, AllWayMovement movement) {
        this.myPosition = initialMyPosition;
        this.color = color;
        this.alive = alive;
        this.movement = movement;
        this.initialMyPosition = initialMyPosition;
    }

    @Override
    public boolean move(MyPosition myPosition, Board board) {
        boolean aux = checkAvailablePosition(myPosition, board);
        //chequea que la posicion essté disponible
        return moveAux(myPosition, board, aux);
    }

    @Override
    public boolean moveInCheck(MyPosition myPosition, Board board) {
        boolean aux = checkAvailablePositionInCheck(myPosition, board);
        return moveAux(myPosition, board, aux);
    }

    private boolean moveAux(MyPosition myPosition, Board board, boolean aux) {
        if (aux) {
            List<Piece> sameColorPieces = board.getPiecesByColor(color);
            List<Piece> otherColorPieces = getOtherColorPieces(sameColorPieces, board);
            for (Piece piece: otherColorPieces) {
                MyPosition otherColorMyPosition = piece.getPosition();
                if (otherColorMyPosition != null) {
                    if (otherColorMyPosition.equals(myPosition)) {
                        board.removeOccupiedPosition(otherColorMyPosition);
                        piece.setAlive(false);
                        piece.setPosition(null);
                    }
                }
            }
            setPosition(myPosition);
        }
        return aux;
    }

    public boolean checkAvailablePositionInCheck (MyPosition myPosition, Board board) {
        List<MyPosition> availableMyPositions = getAvailablePositions(board);
        List<MyPosition> availableMyPositionsInCheck = getAvailablePositionsInCheck(board, availableMyPositions);
        if (availableMyPositionsInCheck.contains(myPosition)) {
            return true;
        }
        return false;
    }

    @Override
    public List<MyPosition> getAvailablePositionsConsideringCheck(Board board, List<MyPosition> availableMyPositions) {
        List<MyPosition> posiblePositions = new ArrayList<>();
        for (MyPosition availableMyPosition: availableMyPositions) {
            if (!putsInCheck(availableMyPosition, board)) {
                posiblePositions.add(availableMyPosition);
            }
        }
        return posiblePositions;
    }

    private boolean putsInCheck(MyPosition availableMyPosition, Board board){
        MyPosition originalPosition = getPosition();
        myPosition = availableMyPosition;
        board.manageEmptyAndOccupiedListsRemove(originalPosition);
        board.manageEmptyAndOccupiedListsAdd(availableMyPosition);
        //capaz tengo que hacer algo más acá (depende de como haga el isCheck())
        if (board.isCheck()) {
            myPosition = originalPosition;
            board.manageEmptyAndOccupiedListsRemove(availableMyPosition);
            board.manageEmptyAndOccupiedListsAdd(originalPosition);
            return true;
        } else {
            myPosition = originalPosition;
            board.manageEmptyAndOccupiedListsRemove(availableMyPosition);
            board.manageEmptyAndOccupiedListsAdd(originalPosition);
            return false;
        }
    }

    @Override
    public List<MyPosition> getAvailablePositionsInCheck(Board board, List<MyPosition> availableMyPositions) {
        List<MyPosition> posiblePositions = new ArrayList<>();
        for (MyPosition availableMyPosition: availableMyPositions) {
            if (removesCheck(availableMyPosition, board)) {
                posiblePositions.add(availableMyPosition);
            }
        }
        return posiblePositions;
    }

    private boolean removesCheck(MyPosition availableMyPosition, Board board){
        MyPosition originalPosition = getPosition();
        myPosition = availableMyPosition;
        board.manageEmptyAndOccupiedListsRemove(originalPosition);
        board.manageEmptyAndOccupiedListsAdd(availableMyPosition);
        //capaz tengo que hacer algo más acá (depende de como haga el isCheck())
        if (board.isCheck()) {
            myPosition = originalPosition;
            board.manageEmptyAndOccupiedListsRemove(availableMyPosition);
            board.manageEmptyAndOccupiedListsAdd(originalPosition);
            return false;
        } else {
            myPosition = originalPosition;
            board.manageEmptyAndOccupiedListsRemove(availableMyPosition);
            board.manageEmptyAndOccupiedListsAdd(originalPosition);
            return true;
        }
    }

    public boolean checkAvailablePosition (MyPosition myPosition, Board board) {
        List<MyPosition> availableMyPositions = getAvailablePositions(board);
        List<MyPosition> availableMyPositionsConsideringCheck = getAvailablePositionsConsideringCheck(board, availableMyPositions);
        if (availableMyPositionsConsideringCheck.contains(myPosition)) {
            return true;
        }
        return false;
    }

    // devuelve todas las posiciones a las que se puede mover teniendo en cuenta su tipo de movimiento
    // tiene que chequear que sea diagonal, y que: o no haya ninguna pieza, o haya una pieza del otro color
    @Override
    public List<MyPosition> getAvailablePositions(Board board) {
        // Empiezo con todas las posiciones y a medida que voy chequeando, elimino las que no se puede
        List<MyPosition> posiblePositionsAuxes = board.getPositions();
        List<MyPosition> sameColorMyPositions = fillSameColorPositions(board.getPiecesByColor(color));
        List<MyPosition> ocupiedMyPositions = board.getOccupiedPositions();

        List<MyPosition> diagonalUpRight = new ArrayList<>(); //guarda bien
        List<MyPosition> diagonalUpLeft = new ArrayList<>(); //guarda al reves
        List<MyPosition> diagonalDownRight = new ArrayList<>(); //guarda bien
        List<MyPosition> diagonalDownLeft = new ArrayList<>(); //guarda al reves

        //llena las listas de las 4 diagonales. FUNCIONA
        for(MyPosition myPositionAux : posiblePositionsAuxes){
            if(movement.areDiagonalUpRight(this.myPosition, myPositionAux)){
                diagonalUpRight.add(myPositionAux);
            }else if(movement.areDiagonalUpLeft(this.myPosition, myPositionAux)){
                diagonalUpLeft.add(myPositionAux);
            }else if(movement.areDiagonalDownRight(this.myPosition, myPositionAux)){
                diagonalDownRight.add(myPositionAux);
            }else if(movement.areDiagonalDownLeft(this.myPosition, myPositionAux)){
                diagonalDownLeft.add(myPositionAux);
            }
        }

        // Al invertir las diagonales izquierdas, quedan todas en orden de más cerca a más lejos de la posición del bishop. FUNCIONA
        Collections.reverse(diagonalUpLeft);
        Collections.reverse(diagonalDownLeft);

        List<MyPosition> crossUp = new ArrayList<>();
        List<MyPosition> crossDown = new ArrayList<>();
        List<MyPosition> crossRight = new ArrayList<>();
        List<MyPosition> crossLeft = new ArrayList<>();

        //llena las listas de las 4 rectas. FUNCIONA
        for(MyPosition myPositionAux : posiblePositionsAuxes){
            if(movement.areCrossUp(this.myPosition, myPositionAux)){
                crossUp.add(myPositionAux);
            }else if(movement.areCrossDown(this.myPosition, myPositionAux)){
                crossDown.add(myPositionAux);
            }else if(movement.areCrossRight(this.myPosition, myPositionAux)){
                crossRight.add(myPositionAux);
            }else if(movement.areCrossLeft(this.myPosition, myPositionAux)){
                crossLeft.add(myPositionAux);
            }
        }

        Collections.reverse(crossDown);
        Collections.reverse(crossLeft);

        List<MyPosition> posibleMyPositions = new ArrayList<>();

        //FALTA CHEQUEAR QUE FUNCIONE CON UNA PIEZA EN EL MEDIO
        for(MyPosition myPositionAux : crossUp){
            //si la posicion no está ocupada la agrega a posibles posiciones
            if(!ocupiedMyPositions.contains(myPositionAux)){
                posibleMyPositions.add(myPositionAux);
            }else{
                //de lo contrario se fija: si la posicion está ocupada por una pieza de distinto color puede moverse sino no
                if (!sameColorMyPositions.contains(myPositionAux)) {
                    posibleMyPositions.add(myPositionAux);
                    break;
                }else{
                    break;
                }
            }
        }
        for(MyPosition myPositionAux : crossDown){
            if(!ocupiedMyPositions.contains(myPositionAux)){
                posibleMyPositions.add(myPositionAux);
            }else{
                if (!sameColorMyPositions.contains(myPositionAux)) {
                    posibleMyPositions.add(myPositionAux);
                    break;
                }else{
                    break;
                }
            }
        }
        for(MyPosition myPositionAux : crossRight){
            if(!ocupiedMyPositions.contains(myPositionAux)){
                posibleMyPositions.add(myPositionAux);
            }else{
                if (!sameColorMyPositions.contains(myPositionAux)) {
                    posibleMyPositions.add(myPositionAux);
                    break;
                }else{
                    break;
                }
            }
        }
        for(MyPosition myPositionAux : crossLeft){
            if(!ocupiedMyPositions.contains(myPositionAux)){
                posibleMyPositions.add(myPositionAux);
            }else{
                if (!sameColorMyPositions.contains(myPositionAux)) {
                    posibleMyPositions.add(myPositionAux);
                    break;
                }else{
                    break;
                }
            }
        }

        //FUNCIONA
        for(MyPosition myPositionAux : diagonalUpRight){
            //si la posicion no está ocupada la agrega a posibles posiciones
            if(!ocupiedMyPositions.contains(myPositionAux)){
                posibleMyPositions.add(myPositionAux);
            }else{
                //de lo contrario se fija: si la posicion está ocupada por una pieza de distinto color puede moverse sino no
                if (!sameColorMyPositions.contains(myPositionAux)) {
                    posibleMyPositions.add(myPositionAux);
                    break;
                }else{
                    break;
                }
            }
        }
        for(MyPosition myPositionAux : diagonalUpLeft){
            if(!ocupiedMyPositions.contains(myPositionAux)){
                posibleMyPositions.add(myPositionAux);
            }else{
                if (!sameColorMyPositions.contains(myPositionAux)) {
                    posibleMyPositions.add(myPositionAux);
                    break;
                }else{
                    break;
                }
            }
        }
        for(MyPosition myPositionAux : diagonalDownRight){
            if(!ocupiedMyPositions.contains(myPositionAux)){
                posibleMyPositions.add(myPositionAux);
            }else{
                if (!sameColorMyPositions.contains(myPositionAux)) {
                    posibleMyPositions.add(myPositionAux);
                    break;
                }else{
                    break;
                }
            }
        }
        for(MyPosition myPositionAux : diagonalDownLeft){
            if(!ocupiedMyPositions.contains(myPositionAux)){
                posibleMyPositions.add(myPositionAux);
            }else{
                if (!sameColorMyPositions.contains(myPositionAux)) {
                    posibleMyPositions.add(myPositionAux);
                    break;
                }else{
                    break;
                }
            }
        }
//        System.out.println(ocupiedPositions.size());
//        System.out.println(posiblePositions.size());
//        for (int i = 0; i < crossLeft.size(); i++) {
//            System.out.println(crossLeft.get(i).getPositionX() + "," + crossLeft.get(i).getPositionY());
//        }
        return posibleMyPositions;
    }

    private List<Piece> getOtherColorPieces(List<Piece> sameColorPieces, Board board) {
        List<Piece> otherColorPieces = new ArrayList<>();
        List<Piece> allPieces = board.getPieces();
        for (Piece piece: allPieces) {
            if (!sameColorPieces.contains(piece)) {
                otherColorPieces.add(piece);
            }
        }
        return otherColorPieces;
    }

    public List<MyPosition> fillSameColorPositions(List<Piece> sameColorPieces){
        List<MyPosition> aux = new ArrayList<>();
        for(Piece piece: sameColorPieces){
            aux.add(piece.getPosition());
        }
        return aux;
    }


    @Override
    public boolean containsPiece(List<Piece> pieces, Piece piece) {
        boolean aux = false;
        for(int i=0; i < pieces.size(); i++){
            if(pieces.get(i).equals(piece)){
                aux = true;
                break;
            }
        }
        return aux;
    }

    @Override
    public boolean isAlive() {
        return alive;
    }

    @Override
    public MyPosition getPosition() {
        return myPosition;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public MyPosition getId() {
        return initialMyPosition;
    }

    @Override
    public String getName(){ return name; }

    @Override
    public void setPosition(MyPosition myPosition) {
        this.myPosition = myPosition;
    }

    @Override
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public void setMovement(Movement movement) {
        this.movement = (AllWayMovement) movement;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}
