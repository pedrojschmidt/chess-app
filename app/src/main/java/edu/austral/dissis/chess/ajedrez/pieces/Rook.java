package edu.austral.dissis.chess.ajedrez.pieces;
import edu.austral.dissis.chess.ajedrez.*;
import edu.austral.dissis.chess.ajedrez.movements.CrossMovement;
import edu.austral.dissis.chess.ajedrez.Color;
import edu.austral.dissis.chess.gui.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Rook implements Piece {

    private MyPosition position;
    private final String name = "rook";
    private Color color;
    private boolean alive;
    //Sirve como piece id
    private final MyPosition initialPosition;
    private CrossMovement movement;

    private boolean hasMoved;

    public Rook(MyPosition initialPosition, Color color, boolean alive, CrossMovement movement) {
        this.position = initialPosition;
        this.color = color;
        this.alive = alive;
        this.movement = movement;
        this.initialPosition = initialPosition;
        hasMoved = false;
    }

    @Override
    public boolean move(MyPosition position, Board board) {
        boolean aux = checkAvailablePosition(position, board);
        //chequea que la posicion essté disponible
        if (aux) {
            hasMoved = true;
            List<Piece> sameColorPieces = board.getPiecesByColor(color);
            List<Piece> otherColorPieces = getOtherColorPieces(sameColorPieces, board);
            for (Piece piece: otherColorPieces) {
                MyPosition otherColorPosition = piece.getPosition();
                //si hay una pieza del otro color en esa posicion, la "mata"
                if (otherColorPosition != null) {
                    if (otherColorPosition.equals(position)) {
                        board.removeOccupiedPosition(otherColorPosition);
                        piece.setAlive(false);
                        piece.setPosition(null);
                    }
                }
            }
            setPosition(position);
        }
        return aux;
    }

    public void moveCastling(MyPosition myPosition) {
        hasMoved = true;
        setPosition(myPosition);
    }

    public boolean checkAvailablePosition (MyPosition position, Board board) {
        List<MyPosition> availablePositions = getAvailablePositions(board);
        if (availablePositions.contains(position)) {
            return true;
        }
        return false;
    }

    @Override
    public List<MyPosition> getAvailablePositions(Board board) {
        // Empiezo con todas las posiciones y a medida que voy chequeando, elimino las que no se puede
        List<MyPosition> posiblePositionsAux = board.getPositions();
        List<MyPosition> sameColorPositions = fillSameColorPositions(board.getPiecesByColor(color));
        List<MyPosition> ocupiedPositions = board.getOccupiedPositions();

        List<MyPosition> crossUp = new ArrayList<>();
        List<MyPosition> crossDown = new ArrayList<>();
        List<MyPosition> crossRight = new ArrayList<>();
        List<MyPosition> crossLeft = new ArrayList<>();

        //llena las listas de las 4 rectas. FUNCIONA
        for(MyPosition positionAux: posiblePositionsAux){
            if(movement.areCrossUp(this.position, positionAux)){
                crossUp.add(positionAux);
            }else if(movement.areCrossDown(this.position, positionAux)){
                crossDown.add(positionAux);
            }else if(movement.areCrossRight(this.position, positionAux)){
                crossRight.add(positionAux);
            }else if(movement.areCrossLeft(this.position, positionAux)){
                crossLeft.add(positionAux);
            }
        }

        Collections.reverse(crossDown);
        Collections.reverse(crossLeft);

//        System.out.println(crossLeft.size());
//        for (int i = 0; i < crossLeft.size(); i++) {
//            System.out.println(crossLeft.get(i).getPositionX() + "," + crossLeft.get(i).getPositionY());
//        }

        List<MyPosition> posiblePositions = new ArrayList<>();

        //FUNCIONA
        for(MyPosition positionAux: crossUp){
            //si la posicion no está ocupada la agrega a posibles posiciones
            if(!ocupiedPositions.contains(positionAux)){
                posiblePositions.add(positionAux);
            }else{
                //de lo contrario se fija: si la posicion está ocupada por una pieza de distinto color puede moverse sino no
                if (!sameColorPositions.contains(positionAux)) {
                    posiblePositions.add(positionAux);
                    break;
                }else{
                    break;
                }
            }
        }
        for(MyPosition positionAux: crossDown){
            if(!ocupiedPositions.contains(positionAux)){
                posiblePositions.add(positionAux);
            }else{
                if (!sameColorPositions.contains(positionAux)) {
                    posiblePositions.add(positionAux);
                    break;
                }else{
                    break;
                }
            }
        }
        for(MyPosition positionAux: crossRight){
            if(!ocupiedPositions.contains(positionAux)){
                posiblePositions.add(positionAux);
            }else{
                if (!sameColorPositions.contains(positionAux)) {
                    posiblePositions.add(positionAux);
                    break;
                }else{
                    break;
                }
            }
        }
        for(MyPosition positionAux: crossLeft){
            if(!ocupiedPositions.contains(positionAux)){
                posiblePositions.add(positionAux);
            }else{
                if (!sameColorPositions.contains(positionAux)) {
                    posiblePositions.add(positionAux);
                    break;
                }else{
                    break;
                }
            }
        }

//        System.out.println(ocupiedPositions.size());

        return posiblePositions;
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
        return position;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public MyPosition getId() {
        return initialPosition;
    }

    @Override
    public String getName(){ return name; }
    @Override
    public void setPosition(MyPosition position) {
        this.position = position;
    }

    @Override
    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    @Override
    public void setMovement(Movement movement) {
        this.movement = (CrossMovement) movement;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isHasMoved() {
        return hasMoved;
    }
}
