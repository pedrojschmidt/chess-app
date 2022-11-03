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
    private PlayerColor currentPlayer = PlayerColor.WHITE;
    private List<MyPosition> positions = fillPositions();
    private List<Piece> pieces = fillPieces();
    private List<ChessPiece> chessPieces = transform.transformPieces(pieces);

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

    @NotNull
    @Override
    public InitialState init() {
        return transform.transformInitialBoard(new Board(positions, pieces));
    }

    @NotNull
    @Override
    public MoveResult applyMove(@NotNull Move move) {
//        Position posFrom = move.getFrom();
//        Position posTo = move.getTo();
////        ChessPiece fromPiece = pieces.find { it.position == move.from } //devuelve la pieza que cumple la condicion
////        ChessPiece toPiece = pieces.find { it.position == move.to } //devuelve la pieza que cumple la condicion
//        ChessPiece fromPiece = findPiece(posFrom);
//        ChessPiece toPiece = findPiece(posTo);
//
//        if (fromPiece == null) {
//            return new InvalidMove("No piece in (${move.from.row}, ${move.from.column})");
//        } else if (fromPiece.getColor() != currentPlayer) {
//            return new InvalidMove("Piece does not belong to current player");
//        } else if (toPiece != null && toPiece.getColor() == currentPlayer) {
//            return new InvalidMove("There is a piece in (${move.to.row}, ${move.to.column})");
//        } else {
//            chessPieces = chessPieces
//                                    .filter { it != fromPiece && it != toPiece }
//                                                                                .plus(fromPiece.copy(position = move.to))
//
//            currentPlayer = (currentPlayer == PlayerColor.WHITE) ? PlayerColor.BLACK : PlayerColor.WHITE;
//
//            if (chessPieces.size() == 1) {
//                return transform.transformGameOver(pieces.get(0).getColor());
//            }
//        }
//
//        pieces = pieces.map {
//            if ((it.color == WHITE && it.position.row == 6) || it.color == BLACK && it.position.row == 1)
//                it.copy(pieceId = "queen")
//            else
//                it
//        }
//
//        return NewGameState(pieces, currentPlayer);
        return null;
    }

    private ChessPiece findPiece(Position position){
        for (ChessPiece chessPiece: chessPieces) {
            if (chessPiece.getPosition().equals(position)) {
                return chessPiece;
            }
        }
        return null;
    }
}

class MovePrinter implements PieceMovedListener {
    @Override
    public void onMovePiece(@NotNull Position from, @NotNull Position to) {
        System.out.print("Move: from ");
        System.out.print(from);
        System.out.print(" to ");
        System.out.println(to);
    }
}

class MyChessGameApplication extends AbstractChessGameApplication {
    @NotNull
    @Override
    protected GameEngine getGameEngine() {
        return new MyGameEngine();
    }
    @NotNull
    @Override
    protected ImageResolver getImageResolver() {
        return new CachedImageResolver(new DefaultImageResolver());
    }
}
