package boardGame;

public class Board {
	private int row;
	private int column;
	private Piece[][] pieces;
	
	
	public Board(int row, int column) {
		if(row <=0 || column <=0) {
			throw new BoardException("O tabuleiro precisa de ter mais de uma linha ou coluna");
		}
		this.row = row;
		this.column = column;
		pieces = new Piece[row][column];
	}
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
	public Piece pieces(int row, int column) {
		if(!positionExists(row, column)) {
			throw new BoardException("A posição não existe no tabuleiro ");
		}
		return pieces[row][column];
	}
	
	public Piece pieces(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("A posição não está no tabuleiro ");
		}
		return pieces[position.getRow()][position.getColumn()];
	}
	
	public void placePieces(Piece piece, Position position) {
		if(thereIsAPiece(position)) {
			throw new BoardException("Há peças nessa posição do tabuleiro, impossível escolher ");
		}
	    pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position;
		
	}
	
	public Piece removePieces(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("A posição não está no tabuleiro");
		}
		if(pieces(position) == null) {
			return null;
		}
		Piece aux = pieces(position);
		aux.position = null;
		pieces[position.getRow()][position.getColumn()] = null;
		return aux;
	}
	
	private boolean positionExists(int row, int column){
		return row >= 0 && row < this.row && column >= 0 && column < this.column;
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(), position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("A posição não está no tabuleiro ");
		}
		return pieces(position) != null;
	}

	
	

}
