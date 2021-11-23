package gameplay;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import gameboard.CustomPoint;
import gameboard.GameBoard;
import gameboard.Tile;
import player.Player;

public class Board {

	private ArrayList<Tile[]> board;

	public Board() {
		board = new ArrayList<Tile[]>();
	}

	public Board(int size) {
		board = new ArrayList<Tile[]>();
		for (int i = 0; i < size; i++) {
			Tile[] tiles = new Tile[size];
			for (int j = 0; j < size; j++) {
				tiles[j] = new Tile(new CustomPoint(i, j));
			}
			board.add(tiles);
		}
	}

	public Board(int x, int y) {
		board = new ArrayList<Tile[]>();
		for (int i = 0; i < x; i++) {
			Tile[] tiles = new Tile[y];
			for (int j = 0; j < y; j++) {
				tiles[j] = new Tile(new CustomPoint(i, j));
			}
			board.add(tiles);
		}
	}

	public Board copyOf() {
		Board copy = new Board(getLength(), getHeight());

		for (int i = 0; i < copy.getLength(); i++) {
			for (int j = 0; j < copy.getHeight(); j++) {
//				System.out.println(i + " " + j);
				copy.setTile(i, j, board.get(i)[j].copyOf());
			}
		}
		return copy;
	}

	public String getBoardAsString() {
		String str = "";
		for (int i = 0; i < board.size(); i++) {
			for (int j = 0; j < board.get(i).length; j++) {
				str += board.get(i)[j].getDiscColor();
			}
			str += "\n";
		}
		return str;
	}

	public String toString() {
		String str = "   | ";
		String spc;
		for (int i = 0; i < board.size(); i++) {
			if (i > 8)
				spc = " ";
			else
				spc = "  ";
			str += " " + i + spc;
		}
		str += "\n---+";
		for (int i = 0; i < board.size(); i++)
			str += "----";
		str += "\n";

		for (int i = 0; i < board.size(); i++) {
			Tile[] tiles = board.get(i);

			if (i < 10)
				str += " ";
			str += i + " | ";
			for (Tile j : tiles) {
				str += "[" + j.getDiscColor() + "] ";
			}
			str += "\n\n";

		}
		return str;
	}

	public boolean isEdge(Tile t) {
		int i = t.getCoords().x, j = t.getCoords().y;
		return i == board.size() || j == board.get(0).length || i == 0 || j == 0;
	}

	public boolean isCorner(Tile t) {
		int i = t.getCoords().x, j = t.getCoords().y;
		return (j == 0 || j == board.get(0).length) && (i == 0 || i == board.size());
	}

	public boolean isOnBoard(CustomPoint point) {
		return (point.x < board.size() && point.x >= 0) && (point.y < board.get(0).length && point.x >= 0);
	}

	public boolean isEmpty(Move move) {
		return board.get(move.getTile().getCoords().x)[move.getTile().getCoords().y].getDiscColor() == GameBoard.EMPTY;
	}

	public boolean equals(Board board2) {
		if (this.getHeight() != board2.getHeight() || this.getLength() != board2.getLength()) {
			return false;
		}

		for (int i = 0; i < this.getLength(); i++) {
			for (int j = 0; j < this.getHeight(); j++) {
				CustomPoint point = new CustomPoint(i, j);
				Tile t1 = this.getTileAt(point.copyOf());
				Tile t2 = board2.getTileAt(point.copyOf());

				if (t1.getDiscColor() != t2.getDiscColor()) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean gameWon(Move move) {
		int rows = board.size();
		int cols = board.get(0).length;

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols - 4; j++) {
				if (board.get(i)[j].getDiscColor() == move.getPlayerColor()
						&& board.get(i)[j].getDiscColor() == board.get(i)[j + 1].getDiscColor()
						&& board.get(i)[j + 1].getDiscColor() == board.get(i)[j + 2].getDiscColor()
						&& board.get(i)[j + 2].getDiscColor() == board.get(i)[j + 3].getDiscColor()
						&& board.get(i)[j + 3].getDiscColor() == board.get(i)[j + 4].getDiscColor()) {
					return true;
				}
			}
		}

		for (int i = 0; i < cols - 4; i++) {
			for (int j = 0; j < rows; j++) {
				if (board.get(i)[j].getDiscColor() == move.getPlayerColor()
						&& board.get(i)[j].getDiscColor() == board.get(i + 1)[j].getDiscColor()
						&& board.get(i + 1)[j].getDiscColor() == board.get(i + 2)[j].getDiscColor()
						&& board.get(i + 2)[j].getDiscColor() == board.get(i + 3)[j].getDiscColor()
						&& board.get(i + 3)[j].getDiscColor() == board.get(i + 4)[j].getDiscColor()) {
					return true;
				}
			}
		}

		for (int i = 0; i < rows - 4; i++) {
			for (int j = 0; j < cols - 4; j++) {
				if (board.get(i)[j].getDiscColor() == move.getPlayerColor()
						&& board.get(i)[j].getDiscColor() == board.get(i + 1)[j + 1].getDiscColor()
						&& board.get(i + 1)[j + 1].getDiscColor() == board.get(i + 2)[j + 2].getDiscColor()
						&& board.get(i + 2)[j + 2].getDiscColor() == board.get(i + 3)[j + 3].getDiscColor()
						&& board.get(i + 3)[j + 3].getDiscColor() == board.get(i + 4)[j + 4].getDiscColor()) {
					return true;
				}
			}
		}

		for (int i = 0; i < rows - 4; i++) {
			for (int j = cols - 1; j >= 4; j--) {
				if (board.get(i)[j].getDiscColor() == move.getPlayerColor()
						&& board.get(i)[j].getDiscColor() == board.get(i + 1)[j - 1].getDiscColor()
						&& board.get(i + 1)[j - 1].getDiscColor() == board.get(i + 2)[j - 2].getDiscColor()
						&& board.get(i + 2)[j - 2].getDiscColor() == board.get(i + 3)[j - 3].getDiscColor()
						&& board.get(i + 3)[j - 3].getDiscColor() == board.get(i + 4)[j - 4].getDiscColor()) {
					return true;
				}
			}
		}
		return false;
	}

	public int getLength() {
		return board.size();
	}

	public int getHeight() {
		return board.get(0).length;
	}

	public List<Tile> getPlayersTiles(Player player) {
		List<Tile> playerTiles = new ArrayList<Tile>();

		for (Tile[] tiles : board) {
			for (Tile tile : tiles) {
				if (tile.getDiscColor() == player.getColor()) {
					playerTiles.add(tile);
				}
			}
		}
		return playerTiles;
	}

	public List<Tile> getEmptyTiles() {
		List<Tile> nullTiles = new ArrayList<Tile>();
		for (Tile[] tiles : board) {
			for (Tile tile : tiles) {
				if (tile.getDiscColor() == GameBoard.EMPTY) {
					nullTiles.add(tile);
				}
			}
		}
		return nullTiles;
	}

	public List<Move> getPossibleMoves(Player player) {
		List<Move> moves = new ArrayList<Move>();
		Move move;
		// If there are tiles that can be played into, add them to moves and return
		// moves
		for (Tile tile : this.getEmptyTiles()) {
			move = new Move(new Tile(tile.getCoords()), player);
			moves.add(move);
		}
		return moves;
	}

	public void setTileColor(Move move) {
		board.get(move.getTile().getCoords().x)[move.getTile().getCoords().y].setDiscColor(move.getPlayerColor());
	}

	public Tile getTileAt(CustomPoint point) {
		return board.get(point.x)[point.y];
	}

	public void addRow(Tile[] row) {
		board.add(row);
	}

	public void rotateBoard(int times) {
		for (int t = 0; t < times; t++) {
			rotate(this);
		}
		resetCoords();
	}

	public void reverseGrid(int times) {
		if (getLength() < getHeight()) {
//			System.out.println("Reversed horizontally");
			reverseColumns(times);
		} else {
//			System.out.println("Reversed vertically");
			reverseRows(times);
		}
		resetCoords();
	}

	private void resetCoords() {
		for (int i = 0; i < getLength(); i++) {
			for (int j = 0; j < getHeight(); j++) {
				board.get(i)[j].setCoords(new CustomPoint(i, j));
			}
		}
	}

	private void reverseColumns(int times) {
		for (int i = 0; i < times; i++) {
			for (Tile[] t : board) {
				Collections.reverse(Arrays.asList(t));
			}
		}
	}

	private void reverseRows(int times) {
		for (int i = 0; i < times; i++) {
			Collections.reverse(board);
		}
	}

	private void setTile(int i, int j, Tile tile) {
		board.get(i)[j] = tile.copyOf();
	}

	private void rotate(Board board) {
		Board temp = board.copyOf();
		for (int i = 0; i < board.getLength(); i++) {
			for (int j = 0; j < board.getHeight(); j++) {
				board.setTile(i, j, temp.getTileAt(new CustomPoint(board.getLength() - j - 1, i)));
			}
		}
	}
}
