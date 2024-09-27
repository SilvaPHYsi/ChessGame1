package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardGame.Board;
import boardGame.Piece;
import boardGame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	private int turn;
	private Color currentPlayer;
	private Board board;
	private boolean check;
	private List<ChessPiece> piecesOnTheBoard = new ArrayList <>();
	private List<ChessPiece> capturedPieces = new ArrayList <>();
	
	
	
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}

	public ChessMatch() {
		board = new Board(8, 8);
		turn = 1;
		currentPlayer = Color.WHITE;
		initialSetup();
	}
	
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRow()][board.getColumn()];
		for(int i=0; i<board.getRow(); i++) {
			for(int j=0; j<board.getColumn();j++) {
				mat[i][j] = (ChessPiece) board.pieces(i, j);
			}
		}
		return mat;
	}
	
	public boolean[][] possibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.pieces(position).possibleMoves();
	}
	
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece capturedPiece = makeMove(source, target);
		if(testCheck(currentPlayer)) {
			undoMove(source,target, capturedPiece);
			throw new ChessException("Você não pode colocar você mesmo em check ");
		}
		check = (testCheck(opponent(currentPlayer))) ? true : false;
		nextTurn();
		return (ChessPiece)capturedPiece;
	}
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		Piece p = board.removePieces(target);
		board.placePieces(p, source);
		
		if(capturedPiece != null) {
			board.placePieces(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add((ChessPiece) capturedPiece);
		}
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePieces(source);
		Piece capturedPiece = board.removePieces(target);
		board.placePieces(p, target);
		
		if(capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add((ChessPiece) capturedPiece);
		}
		return capturedPiece;
	}
	private void validateSourcePosition(Position position) {
		if(!board.thereIsAPiece(position)) {
			throw new  ChessException("Não há peça na posição indicada ");
		}
		if(currentPlayer != ((ChessPiece)board.pieces(position)).getColor()) {
			throw new ChessException("Essa peça não é sua, ela não pode ser movida");
		}
		if(!board.pieces(position).isThereAnyPossibleMove()) {
			throw new ChessException("Não há nenhum movimento possível");
		}
		
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!board.pieces(source).possibleMove(target)) {
			throw new ChessException("A peça não pode ser movida para a posição escolhida");
		}
	}
	
	private void nextTurn() {
		turn ++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private Color opponent(Color color) {
		return (color == color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p: list) {
			if(p instanceof King) {
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("Não existe esse rei com essa cor ");
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece)x).getColor() == opponent(color)).collect(Collectors.toList());
		for(Piece p: opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) {
				return true;
			}
		}
		return false;
	}
	
	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePieces(piece, new ChessPosition(column, row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	

	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
}


