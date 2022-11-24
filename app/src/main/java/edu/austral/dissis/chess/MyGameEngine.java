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
    private Game game = new Game();
    List<MyPosition> positions = null;
    List<Piece> pieces = null;
    List<ChessPiece> chessPieces = null;
    Board board = null;
    private PlayerColor currentPlayer = null;

    @NotNull
    @Override
    public InitialState init() {
//        game.setClassicGame();
        game.setGame2();
//        game.setGame3();
        positions = game.getPositions();
        pieces = game.getPieces();
        chessPieces = game.getChessPieces();
        board = game.getBoard();
        currentPlayer = transform.transformColor(board.getTurn());
        return new InitialState(transform.transformBoardSize(board), transform.transformPieces(board.getPieces()), transform.transformColor(board.getTurn()));
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
                if (board.isOver()) {
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
