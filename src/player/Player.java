package player;

import java.util.Scanner;

import gameboard.CustomPoint;
import gameboard.GameBoard;
import gameboard.Tile;
import gameplay.Move;

public class Player {

	protected char color;
	private Scanner scan;

	public Player(char c) {
		color = c;
	}

	public char getColor() {
		return color;
	}

	/*
	 * Makes a move based on move input from getMove()
	 */
	public boolean makeMove(GameBoard gb) {
		Move move;
		do {
			move = getMove();

			// Valid moves only
			if (move.getTile().getCoords().equals(GameBoard.NO_MOVES)
					|| gb.isOnBoard(move.getTile().getCoords()) && gb.isEmpty(move)) {
				// Returns true if game is won or if the game is tied?
				// Otherwise, returns false
				return gb.makeMove(move);
			} else {
				System.out.println("Make sure your move is in bounds and empty!");
			}
		} while (true);
	}

	/*
	 * Gathers command line user input and returns a Move
	 */
	private Move getMove() {
		scan = new Scanner(System.in);
		Move move = null;

		do {
			System.out.println("What is player " + this.getColor() + "'s next move? (Insert in format 'xpos, ypos')");
			if (scan.hasNextLine()) {
				String[] str = scan.nextLine().split(",");
				CustomPoint point = null;
				if (str.length == 2) {
					try {
						point = new CustomPoint(Integer.parseInt(str[0]), Integer.parseInt(str[1]));
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(point != null) {
						move = new Move(new Tile(point, color), this);
					}
				}
			}
		} while (move == null);
		return move;
	}
}
