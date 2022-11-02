package edu.austral.dissis.chess;

import edu.austral.dissis.chess.ajedrez.Color;
import edu.austral.dissis.chess.ajedrez.MyPosition;
import edu.austral.dissis.chess.ajedrez.movements.*;
import edu.austral.dissis.chess.ajedrez.pieces.*;
import edu.austral.dissis.chess.gui.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyGameEngine implements GameEngine {

    private Transform transform = new Transform();
    private PlayerColor currentPlayer = PlayerColor.WHITE;
    private List<ChessPiece> pieces = fillPieces();

    public List<ChessPiece> fillPieces(){
        List<ChessPiece> aux = new ArrayList<>();

        aux.add(transform.transformPiece(new Rook(new MyPosition(1,1), Color.WHITE, true, new CrossMovement(7))));
        aux.add(transform.transformPiece(new Knight(new MyPosition(2,1), Color.WHITE, true, new JumpMovement(1, 2))));
        aux.add(transform.transformPiece(new Bishop(new MyPosition(3,1), Color.WHITE, true, new DiagonalMovement(7))));
        aux.add(transform.transformPiece(new Queen(new MyPosition(4,1), Color.WHITE, true, new AllWayMovement(7))));
        aux.add(transform.transformPiece(new King(new MyPosition(5,1), Color.WHITE, true, new KingMovement(1))));
        aux.add(transform.transformPiece(new Bishop(new MyPosition(6,1), Color.WHITE, true, new DiagonalMovement(7))));
        aux.add(transform.transformPiece(new Knight(new MyPosition(7,1), Color.WHITE, true, new JumpMovement(1, 2))));
        aux.add(transform.transformPiece(new Rook(new MyPosition(8,1), Color.WHITE, true, new CrossMovement(7))));

        for (int i = 1; i < 9; i++) {
            aux.add(transform.transformPiece(new Pawn(new MyPosition(i,2), Color.WHITE, true, new FowardMovement(2), new FowardMovement(1), new PawnEatMovement(1))));
        }

        aux.add(transform.transformPiece(new Rook(new MyPosition(1,8), Color.BLACK, true, new CrossMovement(7))));
        aux.add(transform.transformPiece(new Knight(new MyPosition(2,8), Color.BLACK, true, new JumpMovement(1, 2))));
        aux.add(transform.transformPiece(new Bishop(new MyPosition(3,8), Color.BLACK, true, new DiagonalMovement(7))));
        aux.add(transform.transformPiece(new Queen(new MyPosition(4,8), Color.BLACK, true, new AllWayMovement(7))));
        aux.add(transform.transformPiece(new King(new MyPosition(5,8), Color.BLACK, true, new KingMovement(1))));
        aux.add(transform.transformPiece(new Bishop(new MyPosition(6,8), Color.BLACK, true, new DiagonalMovement(7))));
        aux.add(transform.transformPiece(new Knight(new MyPosition(7,8), Color.BLACK, true, new JumpMovement(1, 2))));
        aux.add(transform.transformPiece(new Rook(new MyPosition(8,8), Color.BLACK, true, new CrossMovement(7))));

        for (int i = 1; i < 9; i++) {
            aux.add(transform.transformPiece(new Pawn(new MyPosition(i,7), Color.BLACK, true, new FowardMovement(2), new FowardMovement(1), new PawnEatMovement(1))));
        }

        return aux;
    }

    @NotNull
    @Override
    public InitialState init() {
        return new InitialState();
    }

    @NotNull
    @Override
    public MoveResult applyMove(@NotNull Move move) {
        return null;
    }
}
