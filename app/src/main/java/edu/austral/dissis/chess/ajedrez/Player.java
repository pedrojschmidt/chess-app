package edu.austral.dissis.chess.ajedrez;

import java.util.List;

public class Player {

    private String color;
    private List<Piece> pieces;
    private boolean isHisTurn;

    public Player(String color, List<Piece> pieces, boolean isHisTurn) {
        this.color = color;
        this.pieces = pieces;
        this.isHisTurn = isHisTurn;
    }

    public void setHisTurn(boolean hisTurn) {
        isHisTurn = hisTurn;
    }
}
