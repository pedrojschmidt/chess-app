package edu.austral.dissis.chess.ajedrez;
import edu.austral.dissis.chess.ajedrez.pieces.King;
import edu.austral.dissis.chess.ajedrez.pieces.Rook;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private List<MyPosition> myPositions;
    private List<MyPosition> emptyMyPositions = new ArrayList<>();
    private List<MyPosition> occupiedMyPositions = new ArrayList<>();
    private List<Piece> pieces;
    private List<Player> players;
    private boolean isOver;
    private int xSize;
    private int ySize;
    private Color turn;

    public Board(List<MyPosition> myPositions, List<Piece> pieces) {
        this.myPositions = myPositions;
        this.pieces = pieces;
        isOver = false;
        fillEmptyAndOccupiedPositions(pieces, myPositions);
        xSize = checkXSize();
        ySize = checkYSize();
        turn = Color.WHITE;
        this.players = fillPlayers();
    }

    private List<Player> fillPlayers(){
        List<Player> players = new ArrayList<>();
        Player playerBlack = new Player("black", getPiecesByColor(Color.BLACK), false);
        Player playerWhite = new Player("white", getPiecesByColor(Color.WHITE), true);
        players.add(playerWhite);
        players.add(playerBlack);
        return players;
    }

    private int checkXSize(){
        int aux = 0;
        for (MyPosition position: myPositions) {
            if (position.getPositionX() > aux) {
                aux = position.getPositionX();
            }
        }
        return aux;
    }

    private int checkYSize(){
        int aux = 0;
        for (MyPosition position: myPositions) {
            if (position.getPositionY() > aux) {
                aux = position.getPositionY();
            }
        }
        return aux;
    }

    // Chequear que funcione
    private void fillEmptyAndOccupiedPositions(List<Piece> pieces, List<MyPosition> myPositions){
        emptyMyPositions.addAll(myPositions);
        for(Piece piece: pieces){
            manageEmptyAndOccupiedListsAdd(piece.getPosition());
        }
    }

    public boolean isCheckMate(){
        boolean aux = false;

        isOver = aux;
        return aux;
    }

    public boolean isCheck(){
        boolean aux = false;

        isOver = aux;
        return aux;
    }

    public boolean positionExists(MyPosition myPosition){
        return myPositions.contains(myPosition);
    }

    public boolean movePiece(Piece piece, MyPosition myPosition){
        MyPosition posAux = piece.getPosition();
        //chequeo que la posicion exista y no sea la misma, y que el color de la pieza sea el mismo que el turno
        if (myPositions.contains(myPosition) && !posAux.equals(myPosition) && piece.getColor() == turn) {
            boolean aux = piece.move(myPosition, this);
            if (aux) {
                manageEmptyAndOccupiedListsRemove(posAux);
                manageEmptyAndOccupiedListsAdd(myPosition);
                if (turn == Color.BLACK) {
                    turn = Color.WHITE;
                } else {
                    turn = Color.BLACK;
                }
            }
            return aux;
        } else {
            return false;
        }
    }

    public void removeOccupiedPosition(MyPosition myPosition){
        occupiedMyPositions.remove(myPosition);
    }

    public void addPiece(Piece piece){
        pieces.add(piece);
//        occupiedPositions.add(piece.getPosition());
        manageEmptyAndOccupiedListsAdd(piece.getPosition());
    }

    public void manageEmptyAndOccupiedListsAdd(MyPosition myPosition){
        occupiedMyPositions.add(getRealPosition(myPosition));
        emptyMyPositions.remove(getRealPosition(myPosition)); // ESTO ROMPE TODOO
    }

    public void manageEmptyAndOccupiedListsRemove(MyPosition myPosition){
        occupiedMyPositions.remove(getRealPosition(myPosition));
        emptyMyPositions.add(getRealPosition(myPosition));
    }

    public void deletePiece(Piece piece){
        pieces.remove(piece);
    }

    //Sirve aunque haya más de dos jugadores, ya que lo único que hace es
    //ponerle false al que tenía true, y true a uno de los que tenía false.
    public void changeTurn(Player currentTurn, Player nextTurn){
        currentTurn.setHisTurn(false);
        nextTurn.setHisTurn(true);
    }

    public void addPosition(MyPosition myPosition){
        myPositions.add(myPosition);
    }

    public void deletePosition(MyPosition myPosition){
        myPositions.remove(myPosition);
    }

    public List<MyPosition> getPositions() {
        return myPositions;
    }

    public void addPlayer(Player player){
        players.add(player);
    }

    public void deletePlayer(Player player){
        players.remove(player);
    }

    //chequear
    public boolean isPossibleCastling(King king, Rook rook){
        if (king.getColor().equals(rook.getColor())) {
            if (!king.isHasMoved() && !rook.isHasMoved()) {
                return checkEmptyBetweenKingAndRook(rook);
            }
        }
        return false;
    }

    //NO ANDA
    public boolean checkEmptyBetweenKingAndRook(Rook rook){
        int rookX = rook.getPosition().getPositionX();
        int rookY = rook.getPosition().getPositionY();
        if (rookX == 1) {
            MyPosition pos1 = new MyPosition(rookX+1, rookY);
            MyPosition pos2 = new MyPosition(rookX+2, rookY);
            MyPosition pos3 = new MyPosition(rookX+3, rookY);
            boolean empty1 = checkEmptyPosition(pos1);
            boolean empty2 = checkEmptyPosition(pos2);
            boolean empty3 = checkEmptyPosition(pos3);
            if (empty1 && empty2 && empty3) {
                return true;
            }
        } else if (rookX == 8) {
            MyPosition pos1 = new MyPosition(rookX-1, rookY);
            MyPosition pos2 = new MyPosition(rookX-2, rookY);
            boolean empty1 = checkEmptyPosition(pos1);
            boolean empty2 = checkEmptyPosition(pos2);
            if (empty1 && empty2) {
                return true;
            }
        }
        return false;
    }

    //chequear
    public boolean castling(King king, Rook rook){
        boolean aux = isPossibleCastling(king, rook);
        if (aux) {
            MyPosition kingPos = king.getPosition();
            int kingXPosition = kingPos.getPositionX();
            int kingYPosition = kingPos.getPositionY();
            MyPosition rookPos = rook.getPosition();
            int rookXPosition = rookPos.getPositionX();
            int rookYPosition = rookPos.getPositionY();

            int newKingXPosition;
            int newRookXPosition;

            if (kingXPosition > rookXPosition) {
                newKingXPosition = kingXPosition - 2;
                newRookXPosition = rookXPosition + 3;
            }else{
                newKingXPosition = kingXPosition + 2;
                newRookXPosition = rookXPosition - 2;
            }
            MyPosition newKingPos = new MyPosition(newKingXPosition, kingYPosition);
            MyPosition newRookPos = new MyPosition(newRookXPosition, rookYPosition);
//            king.setPosition(newKingPos);
//            rook.setPosition(newRookPos);
            movePiece(king, newKingPos);
            movePiece(rook, newRookPos);
        }
        return aux;
    }

    //sirve para obtener la posicion que está en la lista de positions
    public MyPosition getRealPosition(MyPosition pos){
        for (int i = 0; i < myPositions.size(); i++) {
            if (myPositions.get(i).equals(pos)) {
                return myPositions.get(i);
            }
        }
        return null;
    }

    //chequear
    public boolean checkEmptyPosition(MyPosition myPosition) {
        return emptyMyPositions.contains(getRealPosition(myPosition));
    }

    public Piece getPieceByPosition(MyPosition myPosition){
        for(Piece piece: pieces){
            if (piece.getPosition().getPositionX() == myPosition.getPositionX() && piece.getPosition().getPositionY() == myPosition.getPositionY()) {
                return piece;
            }
        }
        return null;
    }

    public List<Piece> getPiecesByColor(Color color){
        List<Piece> colorPieces = new ArrayList<>();
        for(Piece piece: pieces){
            if (color.equals(piece.getColor())){
                colorPieces.add(piece);
            }
        }
        return colorPieces;
    }

    public List<MyPosition> getEmptyPositions() {
        return emptyMyPositions;
    }

    public List<MyPosition> getOccupiedPositions() {
        return occupiedMyPositions;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getxSize() {
        return xSize;
    }

    public int getySize() {
        return ySize;
    }

    public Color getTurn() {
        return turn;
    }
}
