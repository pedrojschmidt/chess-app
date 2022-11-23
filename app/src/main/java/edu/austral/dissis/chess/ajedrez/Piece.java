package edu.austral.dissis.chess.ajedrez;

import java.util.List;

public interface Piece {

    public boolean move(MyPosition myPosition, Board board);
    public boolean isAlive();
    public List<MyPosition> fillSameColorPositions(List<Piece> sameColorPieces);
    public boolean containsPiece(List<Piece> pieces, Piece piece);
    public boolean moveInCheck(MyPosition myPosition, Board board);

    public List<MyPosition> getAvailablePositions(Board board);
    public List<MyPosition> getAvailablePositionsConsideringCheck(Board board, List<MyPosition> availableMyPositions);
    public List<MyPosition> getAvailablePositionsInCheck(Board board, List<MyPosition> availableMyPositions);
    public MyPosition getPosition();
    public Color getColor();
    MyPosition getId();
    String getName();

    public void setPosition(MyPosition myPosition);
    public void setAlive(boolean alive);
    public void setMovement(Movement movement);
    public void setColor(Color color);

}
