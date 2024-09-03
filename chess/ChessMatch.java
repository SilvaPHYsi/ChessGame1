package chess;

import boardGame.Board;
import boardGame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

public class ChessMatch {
	private Board board;
	
	public ChessMatch() {
		board = new Board(8, 8);
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
	
	private void initialSetup() {
		board.placePieces(new King(board, Color.BLACK), new Position(0, 4));
	}

}
