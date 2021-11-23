package player;

import java.util.List;

import ai.MoveFileReader;
import ai.Pattern;
import gameboard.CustomPoint;
import gameboard.GameBoard;
import gameboard.Tile;
import gameplay.Move;

public class AIPlayer extends Player {

	private MoveFileReader fr;
	private int depth;
	private char otherPlayerColor;

	private int test;

	public AIPlayer(char color, int depth) {
		super(color);
		this.depth = depth;

		if (getColor() == GameBoard.BLACK) {
			otherPlayerColor = GameBoard.WHITE;
		} else {
			otherPlayerColor = GameBoard.BLACK;
		}

		this.fr = new MoveFileReader(this, otherPlayerColor);
		test = 1;
	}

	@Override
	public boolean makeMove(GameBoard gb) {

		List<Pattern> playerPatterns = fr.analyzeBoardForPatterns(gb, getColor(), otherPlayerColor);
		List<Pattern> otherPatterns = fr.analyzeBoardForPatterns(gb, otherPlayerColor, getColor());

		for (Pattern pattern : otherPatterns) {
			pattern.setScore(-pattern.getScore());
		}

		System.out.println("Player patterns. \n");
		for (Pattern pattern : playerPatterns) {
			System.out.println(pattern);
		}

		System.out.println("Other patterns. \n");
		for (Pattern pattern : otherPatterns) {
			System.out.println(pattern);
		}

		test++;

		return gb.makeMove(new Move(new Tile(new CustomPoint(test, 14)), this));
	}

	/*
	 * Plays move over times amount of plays Adjusts color for opponent's moves
	 * 
	 */
	private Move runSimulations(GameBoard gb, Move move, int times, int index) {
		/*
		 * 
		 * move = returnMoveScore(gb, move); System.out.println(move);
		 * 
		 * // Catch after scoring if (index > times) { return move; }
		 * 
		 * char playerColor; if(move.getPlayerColor() == GameBoard.WHITE) playerColor =
		 * GameBoard.BLACK; else playerColor = GameBoard.WHITE;
		 * 
		 * // System.out.println(copyGB); GameBoard copy = GameBoard.copyOf(gb);
		 * copy.makeMove(move); // System.out.println(copy);
		 * move.setNextMoves(copy.getPossibleMoves(this)); //
		 * System.out.println(copy.getPossibleMoves(color).size()); for (Move nextMove :
		 * move.getNextMoves()) { nextMove = runSimulations(copy, nextMove, times, index
		 * + 1); }
		 * 
		 * // After running all simulations, return move return move;
		 * 
		 */

		return null;
	}
}
