package edu.austral.dissis.chess;

import edu.austral.dissis.chess.ajedrez.*;
import edu.austral.dissis.chess.ajedrez.movements.*;
import edu.austral.dissis.chess.ajedrez.pieces.*;
import edu.austral.dissis.chess.gui.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MyGameEngine implements GameEngine {

    private Transform transform = new Transform();
    List<MyPosition> positions = fillPositions();
    List<Piece> pieces = fillPieces();
    List<ChessPiece> chessPieces = transform.transformPieces(pieces);
    Board board = new Board(positions, pieces);
    private PlayerColor currentPlayer = transform.transformColor(board.getTurn());

    @NotNull
    @Override
    public InitialState init() {
        return transform.transformInitialBoard(board);
    }

    @NotNull
    @Override
    public MoveResult applyMove(@NotNull Move move) {
        Position posFrom = move.getFrom();
        Position posTo = move.getTo();
        MyPosition myPosFrom = transform.transformMyPosition(posFrom);
        MyPosition myPosTo = transform.transformMyPosition(posTo);
//        ChessPiece fromPiece = pieces.find { it.position == move.from } //devuelve la pieza que cumple la condicion
//        ChessPiece toPiece = pieces.find { it.position == move.to } //devuelve la pieza que cumple la condicion
        ChessPiece fromChessPiece = findChessPiece(posFrom);
        ChessPiece toChessPiece = findChessPiece(posTo);
        Piece fromPiece = findPiece(myPosFrom);
        Piece toPiece = findPiece(myPosTo);

        if (fromChessPiece == null) { //chequea que haya una pieza en el lugar que se esta queriendo mover
            return new InvalidMove("No piece in (${move.from.row}, ${move.from.column})");
        } else if (fromChessPiece.getColor() != currentPlayer) { //chequea que la pieza que se quiere mover sea del color del jugador que le toca
            return new InvalidMove("Piece does not belong to current player");
        } else if (toChessPiece != null && toChessPiece.getColor() == currentPlayer) { //chequea que la posicion final no contenga una pieza del mismo color
            return new InvalidMove("There is a piece in (${move.to.row}, ${move.to.column})");
        } else {
            boolean hasMoved = board.movePiece(fromPiece, myPosTo);
            if (hasMoved) {
                chessPieces = transform.transformPieces(board.getAlivePieces());
                currentPlayer = transform.transformColor(board.getTurn());
                if (board.isCheckMate()) {
                    PlayerColor winner = (currentPlayer == PlayerColor.BLACK) ? PlayerColor.WHITE : PlayerColor.BLACK;
                    return new GameOver(winner);
                }
            } else {
                return new InvalidMove("Invalid movement");
            }
        }
        return new NewGameState(chessPieces, currentPlayer);
//        return null;
    }

    private List<MyPosition> fillPositions(){
        List<MyPosition> positions = new ArrayList<>();
        for (int i = 1; i < 9; i++) {
            for (int j = 1; j < 9; j++) {
                positions.add(new MyPosition(i, j));
            }
        }
        return positions;
    }

    public List<Piece> fillPieces(){
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

    private Piece findPiece(MyPosition position){
        for (Piece piece: pieces) {
            if (piece.getPosition() != null) {
                if (piece.getPosition().equals(position)) {
                    return piece;
                }
            }
        }
        return null;
    }

    private ChessPiece findChessPiece(Position position){
        for (ChessPiece chessPiece: chessPieces) {
            if (chessPiece.getPosition().equals(position)) {
                return chessPiece;
            }
        }
        return null;
    }
}
