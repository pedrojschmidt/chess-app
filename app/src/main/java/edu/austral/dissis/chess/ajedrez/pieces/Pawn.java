package edu.austral.dissis.chess.ajedrez.pieces;
import edu.austral.dissis.chess.ajedrez.*;
import edu.austral.dissis.chess.ajedrez.movements.AllWayMovement;
import edu.austral.dissis.chess.ajedrez.movements.FowardMovement;
import edu.austral.dissis.chess.ajedrez.movements.PawnEatMovement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pawn implements Piece {

    private MyPosition myPosition;
    private String name = "pawn";
    private Color color;
    private boolean alive;
    private boolean alreadyMoved;
    //Sirve como piece id
    private final MyPosition initialMyPosition;

    private FowardMovement firstMovement;
    private FowardMovement anyMovement;
    private PawnEatMovement killMovement;

    private boolean isQueen;
    private AllWayMovement queenMovement;

    private boolean hasMoved;

    public Pawn(MyPosition initialMyPosition, Color color, boolean alive, boolean alreadyMoved, FowardMovement firstMovement, FowardMovement anyMovement, PawnEatMovement killMovement) {
        this.myPosition = initialMyPosition;
        this.color = color;
        this.alive = alive;
        this.alreadyMoved = alreadyMoved;
        this.initialMyPosition = initialMyPosition;

        this.firstMovement = firstMovement;
        this.anyMovement = anyMovement;
        this.killMovement = killMovement;

        isQueen = false;
        queenMovement = new AllWayMovement(7);

        hasMoved = alreadyMoved;
    }

    public Pawn(MyPosition myPosition, Color color, FowardMovement firstMovement, FowardMovement anyMovement, PawnEatMovement killMovement, boolean alreadyMoved) {
        this(myPosition, color, true, alreadyMoved, firstMovement, anyMovement, killMovement);
    }

    public Pawn(MyPosition myPosition, Color color, boolean alive, FowardMovement firstMovement, FowardMovement anyMovement, PawnEatMovement killMovement) {
        this(myPosition, color, alive, false, firstMovement, anyMovement, killMovement);
    }

    public Pawn(MyPosition myPosition, Color color, FowardMovement firstMovement, FowardMovement anyMovement, PawnEatMovement killMovement) {
        this(myPosition, color, true, false, firstMovement, anyMovement, killMovement);
    }

    @Override
    public boolean move(MyPosition myPosition, Board board) {
        if (isQueen) {
            boolean aux = checkAvailablePositionAsQueen(myPosition, board);
            //chequea que la posicion essté disponible
            if (aux) {
                moveAux(myPosition, board);
            }
            return aux;
        } else {
            boolean aux = checkAvailablePosition(myPosition, board);
            if (aux) {
                hasMoved = true;
                moveAux(myPosition, board);
                if (myPosition.getPositionY() == 8) {
                    setQueen(true);
                    setName("queen");
                }
            }
            return aux;
        }
    }

    @Override
    public boolean moveInCheck(MyPosition myPosition, Board board) {
        boolean aux = checkAvailablePositionInCheck(myPosition, board);
        if (aux) {
            hasMoved = true;
            moveAux(myPosition, board);
            if (myPosition.getPositionY() == 8) {
                setQueen(true);
                setName("queen");
            }
        }
        return aux;
    }

    private void moveAux(MyPosition myPosition, Board board) {
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

    public boolean checkAvailablePositionInCheck (MyPosition myPosition, Board board) {
        List<MyPosition> availableMyPositions = getAvailablePositions(board);
        List<MyPosition> availableMyPositionsInCheck = getAvailablePositionsInCheck(board, availableMyPositions);
        if (availableMyPositionsInCheck.contains(myPosition)) {
            return true;
        }
        return false;
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
        if (availableMyPositions.contains(myPosition)) {
            return true;
        }
        return false;
    }

    @Override
    public List<MyPosition> getAvailablePositions(Board board) {
        List<MyPosition> posiblePositionsAuxes = board.getPositions();
        List<MyPosition> sameColorMyPositions = fillSameColorPositions(board.getPiecesByColor(color));
        Color otherColor = Color.BLACK;
        if (color.equals(otherColor)) {
            otherColor = Color.WHITE;
        }
        List<MyPosition> otherColorMyPositions = fillSameColorPositions(board.getPiecesByColor(otherColor));
        List<MyPosition> ocupiedMyPositions = board.getOccupiedPositions();

        List<MyPosition> foward = new ArrayList<>();
        List<MyPosition> fowardDiagonalOne = new ArrayList<>();

        for(MyPosition myPositionAux : posiblePositionsAuxes) {
            if (color == Color.WHITE) {
                if (!hasMoved) {
                    if (firstMovement.areFowardWhite(this.myPosition, myPositionAux)) {
                        if (!ocupiedMyPositions.contains(myPositionAux)) {
                            if (!otherColorMyPositions.contains(myPositionAux)) {
                                foward.add(myPositionAux);
                            }
                        } else {
                            break;
                        }
                    }
                } else if (anyMovement.areFowardWhite(this.myPosition, myPositionAux)) {
                    if (!otherColorMyPositions.contains(myPositionAux)) {
                        foward.add(myPositionAux);
                    }
                }
            } else {
                if (!hasMoved) {
                    if (firstMovement.areFowardBlack(this.myPosition, myPositionAux)) {
                        if (!ocupiedMyPositions.contains(myPositionAux)) {
                            if (!otherColorMyPositions.contains(myPositionAux)) {
                                foward.add(myPositionAux);
                            }
                        } else {
                            break;
                        }
                    }
                } else if (anyMovement.areFowardBlack(this.myPosition, myPositionAux)) {
                    if (!otherColorMyPositions.contains(myPositionAux)) {
                        foward.add(myPositionAux);
                    }
                }
            }
        }
        for(MyPosition myPositionAux : posiblePositionsAuxes) {
            if (color == Color.WHITE) {
                if(killMovement.areFowardDiagonalOneWhite(this.myPosition, myPositionAux)){
                    //chequea que haya una de otro color
                    if (otherColorMyPositions.contains(myPositionAux)) {
                        fowardDiagonalOne.add(myPositionAux);
                    }
                }
            } else {
                if(killMovement.areFowardDiagonalOneBlack(this.myPosition, myPositionAux)){
                    //chequea que haya una de otro color
                    if (otherColorMyPositions.contains(myPositionAux)) {
                        fowardDiagonalOne.add(myPositionAux);
                    }
                }
            }
        }

//        System.out.println(foward.size());
//        for (int i = 0; i < foward.size(); i++) {
//            System.out.println(foward.get(i).getPositionX() + "," + foward.get(i).getPositionY());
//        }

        List<MyPosition> posibleMyPositions = new ArrayList<>();

        posibleMyPositions.addAll(foward);
        posibleMyPositions.addAll(fowardDiagonalOne);

//        System.out.println(ocupiedPositions.size());
//        System.out.println(fowardDiagonalOne.size());
//        for (int i = 0; i < fowardDiagonalOne.size(); i++) {
//            System.out.println(fowardDiagonalOne.get(i).getPositionX() + "," + fowardDiagonalOne.get(i).getPositionY());
//        }

        return posibleMyPositions;
    }

    public boolean checkAvailablePositionAsQueen (MyPosition myPosition, Board board) {
        List<MyPosition> availableMyPositions = getAvailablePositionsAsQueen(board);
        if (availableMyPositions.contains(myPosition)) {
            return true;
        }
        return false;
    }

    public List<MyPosition> getAvailablePositionsAsQueen(Board board) {
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
            if(queenMovement.areDiagonalUpRight(this.myPosition, myPositionAux)){
                diagonalUpRight.add(myPositionAux);
            }else if(queenMovement.areDiagonalUpLeft(this.myPosition, myPositionAux)){
                diagonalUpLeft.add(myPositionAux);
            }else if(queenMovement.areDiagonalDownRight(this.myPosition, myPositionAux)){
                diagonalDownRight.add(myPositionAux);
            }else if(queenMovement.areDiagonalDownLeft(this.myPosition, myPositionAux)){
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
            if(queenMovement.areCrossUp(this.myPosition, myPositionAux)){
                crossUp.add(myPositionAux);
            }else if(queenMovement.areCrossDown(this.myPosition, myPositionAux)){
                crossDown.add(myPositionAux);
            }else if(queenMovement.areCrossRight(this.myPosition, myPositionAux)){
                crossRight.add(myPositionAux);
            }else if(queenMovement.areCrossLeft(this.myPosition, myPositionAux)){
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
        this.anyMovement = (FowardMovement) movement;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }

    public void setQueen(boolean queen) {
        isQueen = queen;
    }

    public void setName(String name) {
        this.name = name;
    }
}
