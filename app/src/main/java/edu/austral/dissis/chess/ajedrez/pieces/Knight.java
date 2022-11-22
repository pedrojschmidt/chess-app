package edu.austral.dissis.chess.ajedrez.pieces;
import edu.austral.dissis.chess.ajedrez.*;
import edu.austral.dissis.chess.ajedrez.movements.JumpMovement;

import java.util.ArrayList;
import java.util.List;

public class Knight implements Piece {

    private MyPosition position;
    private final String name = "knight";
    private Color color;
    private boolean alive;
    //Sirve como piece id
    private final MyPosition initialPosition;

    private JumpMovement movement;

    public Knight(MyPosition initialPosition, Color color, boolean alive, JumpMovement movement) {
        this.position = initialPosition;
        this.color = color;
        this.alive = alive;
        this.movement = movement;
        this.initialPosition = initialPosition;
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
        position = availableMyPosition;
        board.manageEmptyAndOccupiedListsRemove(originalPosition);
        board.manageEmptyAndOccupiedListsAdd(availableMyPosition);
        //capaz tengo que hacer algo más acá (depende de como haga el isCheck())
        if (board.isCheck()) {
            position = originalPosition;
            board.manageEmptyAndOccupiedListsRemove(availableMyPosition);
            board.manageEmptyAndOccupiedListsAdd(originalPosition);
            return false;
        } else {
            position = originalPosition;
            board.manageEmptyAndOccupiedListsRemove(availableMyPosition);
            board.manageEmptyAndOccupiedListsAdd(originalPosition);
            return true;
        }
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

        //llena las listas de las 4 rectas. FUNCIONA
        for(MyPosition positionAux: posiblePositionsAux){
            if(movement.areJump(this.position, positionAux)){
                crossUp.add(positionAux);
            }
        }

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
        this.movement = (JumpMovement) movement;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}
