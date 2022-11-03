package edu.austral.dissis.chess;

import edu.austral.dissis.chess.ajedrez.Board;
import edu.austral.dissis.chess.ajedrez.Color;
import edu.austral.dissis.chess.ajedrez.MyPosition;
import edu.austral.dissis.chess.ajedrez.Piece;
import edu.austral.dissis.chess.gui.*;

import java.util.ArrayList;
import java.util.List;

public class Transform {

    public Position transformPosition(MyPosition myPosition){
        return new Position(myPosition.getPositionX(), myPosition.getPositionY());
    }
    public MyPosition transformMyPosition(Position position){
        return new MyPosition(position.getRow(), position.getColumn());
    }

    public PlayerColor transformColor(Color color){
        if (color == Color.WHITE) {
            return PlayerColor.WHITE;
        } else {
            return PlayerColor.BLACK;
        }
    }

    public ChessPiece transformPiece(Piece piece){
        String aux =  "" + piece.getId().getPositionY() + piece.getId().getPositionX();
        return new ChessPiece(aux, transformColor(piece.getColor()), transformPosition(piece.getPosition()), piece.getName());
    }

    public List<ChessPiece> transformPieces(List<Piece> pieces){
        List<ChessPiece> chessPieces = new ArrayList<>();
        for (Piece piece: pieces) {
            chessPieces.add(transformPiece(piece));
        }
        return chessPieces;
    }

    public BoardSize transformBoardSize(Board board){
        return new BoardSize(board.getxSize(), board.getySize());
    }

    public InitialState transformInitialBoard(Board board){
        return new InitialState(transformBoardSize(board), transformPieces(board.getPieces()), transformColor(board.getTurn()));
    }
}
