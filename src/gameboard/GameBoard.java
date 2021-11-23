package gameboard;

import gameplay.Board;
import gameplay.Move;

public class GameBoard {

	private Board board;

	public static final char EMPTY = ' ';
	public static final char WHITE = 'W';
	public static final char BLACK = 'B';

	private final int boardSize = 15;

	public static final CustomPoint NO_MOVES = new CustomPoint(-1, -1);

	/**
	 * Default board setup
	 * 
	 */
	public GameBoard() {
		board = new Board(boardSize);
	}

	public boolean makeMove(Move move) {
		if (board.getEmptyTiles().isEmpty()) {
			System.out.println("Game tied by " + move.getPlayerColor() + ".\n");
			return true;
		}

		if (move.getTile().getCoords().equals(NO_MOVES)) {
			System.out.println("No moves for player " + move.getPlayerColor() + ".\n");
			return true;
		}

		board.setTileColor(move);

		if (board.gameWon(move)) {
			System.out.println("Game won by player " + move.getPlayerColor() + ".\n");
			return true;
		}

		System.out.println("Made move.\n");
		return false;
	}

	public boolean isOnBoard(CustomPoint point) {
		return board.isOnBoard(point);
	}

	public Tile getTileAt(CustomPoint point) {
		return board.getTileAt(point);
	}

	public int getBoardSize() {
		return boardSize;
	}

	public String toString() {
		return board.toString();
	}

	public boolean isEmpty(Move move) {
		return board.isEmpty(move);
	}
}
