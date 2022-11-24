package edu.austral.dissis.chess.ajedrez;

import edu.austral.dissis.chess.Transform;
import edu.austral.dissis.chess.ajedrez.movements.*;
import edu.austral.dissis.chess.ajedrez.pieces.*;
import edu.austral.dissis.chess.gui.ChessPiece;
import edu.austral.dissis.chess.gui.Position;

import java.util.ArrayList;
import java.util.List;

public class Game {

    private Transform transform = new Transform();
    List<MyPosition> positions = null;
    List<Piece> pieces = null;
    List<ChessPiece> chessPieces = null;
    Board board = null;

    public void setClassicGame(){
        positions = fillPositions(8, 8);
        pieces = fillClassicPieces();
        chessPieces = transform.transformPieces(pieces);
        board = new Board(positions, pieces);
    }

    public void setGame2(){
        positions = fillPositions(10, 8);
        pieces = fillPieces2();
        chessPieces = transform.transformPieces(pieces);
        board = new Board(positions, pieces);
    }

    public void setGame3(){
        positions = fillPositions(10, 10);
        pieces = fillPieces3();
        chessPieces = transform.transformPieces(pieces);
        board = new Board(positions, pieces);
    }

    private List<MyPosition> fillPositions(int xSize, int ySize){
        List<MyPosition> positions = new ArrayList<>();
        for (int i = 1; i < ySize+1; i++) {
            for (int j = 1; j < xSize+1; j++) {
                positions.add(new MyPosition(i, j));
            }
        }
        return positions;
    }

    public List<Piece> fillClassicPieces(){
        List<Piece> aux = new ArrayList<>();

        aux.add(new Rook(positions.get(0), Color.WHITE, true, new CrossMovement(7)));
        aux.add(new Knight(positions.get(8), Color.WHITE, true, new JumpMovement(1, 2)));
        aux.add(new Bishop(positions.get(16), Color.WHITE, true, new DiagonalMovement(7)));
        aux.add(new Queen(positions.get(24), Color.WHITE, true, new AllWayMovement(7)));
        aux.add(new King(positions.get(32), Color.WHITE, true, new KingMovement(1)));
        aux.add(new Bishop(positions.get(40), Color.WHITE, true, new DiagonalMovement(7)));
        aux.add(new Knight(positions.get(48), Color.WHITE, true, new JumpMovement(1, 2)));
        aux.add(new Rook(positions.get(56), Color.WHITE, true, new CrossMovement(7)));

        for (int i = 1; i < 9; i++) {
            aux.add(new Pawn(new MyPosition(i,2), Color.WHITE, true, new FowardMovement(2), new FowardMovement(1), new PawnEatMovement(1)));
        }

        aux.add(new Rook(positions.get(7), Color.BLACK, true, new CrossMovement(7)));
        aux.add(new Knight(positions.get(15), Color.BLACK, true, new JumpMovement(1, 2)));
        aux.add(new Bishop(positions.get(23), Color.BLACK, true, new DiagonalMovement(7)));
        aux.add(new Queen(positions.get(31), Color.BLACK, true, new AllWayMovement(7)));
        aux.add(new King(positions.get(39), Color.BLACK, true, new KingMovement(1)));
        aux.add(new Bishop(positions.get(47), Color.BLACK, true, new DiagonalMovement(7)));
        aux.add(new Knight(positions.get(55), Color.BLACK, true, new JumpMovement(1, 2)));
        aux.add(new Rook(positions.get(63), Color.BLACK, true, new CrossMovement(7)));

        for (int i = 1; i < 9; i++) {
            aux.add(new Pawn(new MyPosition(i,7), Color.BLACK, true, new FowardMovement(2), new FowardMovement(1), new PawnEatMovement(1)));
        }

        return aux;
    }

    public List<Piece> fillPieces2(){
        List<Piece> aux = new ArrayList<>();

        aux.add(new Rook(new MyPosition(1,1), Color.WHITE, true, new CrossMovement(7)));
        aux.add(new Knight(new MyPosition(2,1), Color.WHITE, true, new JumpMovement(1, 2)));
        aux.add(new Archbishop(new MyPosition(3,1), Color.WHITE, true, new DiagonalMovement(7), new JumpMovement(1, 2)));
        aux.add(new Bishop(new MyPosition(4,1), Color.WHITE, true, new DiagonalMovement(7)));
        aux.add(new Queen(new MyPosition(5,1), Color.WHITE, true, new AllWayMovement(7)));
        aux.add(new King(new MyPosition(6,1), Color.WHITE, true, new KingMovement(1)));
        aux.add(new Bishop(new MyPosition(7,1), Color.WHITE, true, new DiagonalMovement(7)));
        aux.add(new Archbishop(new MyPosition(8,1), Color.WHITE, true, new DiagonalMovement(7), new JumpMovement(1, 2)));
        aux.add(new Knight(new MyPosition(9,1), Color.WHITE, true, new JumpMovement(1, 2)));
        aux.add(new Rook(new MyPosition(10,1), Color.WHITE, true, new CrossMovement(7)));

        for (int i = 1; i < 11; i++) {
            aux.add(new Pawn(new MyPosition(i,2), Color.WHITE, true, new FowardMovement(2), new FowardMovement(1), new PawnEatMovement(1)));
        }

        aux.add(new Rook(new MyPosition(1,8), Color.BLACK, true, new CrossMovement(7)));
        aux.add(new Knight(new MyPosition(2,8), Color.BLACK, true, new JumpMovement(1, 2)));
        aux.add(new Archbishop(new MyPosition(3,8), Color.BLACK, true, new DiagonalMovement(7), new JumpMovement(1, 2)));
        aux.add(new Bishop(new MyPosition(4,8), Color.BLACK, true, new DiagonalMovement(7)));
        aux.add(new Queen(new MyPosition(5,8), Color.BLACK, true, new AllWayMovement(7)));
        aux.add(new King(new MyPosition(6,8), Color.BLACK, true, new KingMovement(1)));
        aux.add(new Bishop(new MyPosition(7,8), Color.BLACK, true, new DiagonalMovement(7)));
        aux.add(new Archbishop(new MyPosition(8,8), Color.BLACK, true, new DiagonalMovement(7), new JumpMovement(1, 2)));
        aux.add(new Knight(new MyPosition(9,8), Color.BLACK, true, new JumpMovement(1, 2)));
        aux.add(new Rook(new MyPosition(10,8), Color.BLACK, true, new CrossMovement(7)));

        for (int i = 1; i < 11; i++) {
            aux.add(new Pawn(new MyPosition(i,7), Color.BLACK, true, new FowardMovement(2), new FowardMovement(1), new PawnEatMovement(1)));
        }

        return aux;
    }

    public List<Piece> fillPieces3(){
        List<Piece> aux = new ArrayList<>();

        aux.add(new Rook(positions.get(0), Color.WHITE, true, new CrossMovement(7)));
        aux.add(new Knight(positions.get(8), Color.WHITE, true, new JumpMovement(1, 2)));
        aux.add(new Bishop(positions.get(16), Color.WHITE, true, new DiagonalMovement(7)));
        aux.add(new Queen(positions.get(24), Color.WHITE, true, new AllWayMovement(7)));
        aux.add(new King(positions.get(32), Color.WHITE, true, new KingMovement(1)));
        aux.add(new Bishop(positions.get(40), Color.WHITE, true, new DiagonalMovement(7)));
        aux.add(new Knight(positions.get(48), Color.WHITE, true, new JumpMovement(1, 2)));
        aux.add(new Rook(positions.get(56), Color.WHITE, true, new CrossMovement(7)));

        for (int i = 1; i < 9; i++) {
            aux.add(new Pawn(new MyPosition(i,2), Color.WHITE, true, new FowardMovement(2), new FowardMovement(1), new PawnEatMovement(1)));
        }

        aux.add(new Rook(positions.get(7), Color.BLACK, true, new CrossMovement(7)));
        aux.add(new Knight(positions.get(15), Color.BLACK, true, new JumpMovement(1, 2)));
        aux.add(new Bishop(positions.get(23), Color.BLACK, true, new DiagonalMovement(7)));
        aux.add(new Queen(positions.get(31), Color.BLACK, true, new AllWayMovement(7)));
        aux.add(new King(positions.get(39), Color.BLACK, true, new KingMovement(1)));
        aux.add(new Bishop(positions.get(47), Color.BLACK, true, new DiagonalMovement(7)));
        aux.add(new Knight(positions.get(55), Color.BLACK, true, new JumpMovement(1, 2)));
        aux.add(new Rook(positions.get(63), Color.BLACK, true, new CrossMovement(7)));

        for (int i = 1; i < 9; i++) {
            aux.add(new Pawn(new MyPosition(i,7), Color.BLACK, true, new FowardMovement(2), new FowardMovement(1), new PawnEatMovement(1)));
        }

        return aux;
    }

    public List<MyPosition> getPositions() {
        return positions;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public List<ChessPiece> getChessPieces() {
        return chessPieces;
    }

    public Board getBoard() {
        return board;
    }
}
