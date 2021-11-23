package solver;

import java.util.Scanner;

import gameboard.GameBoard;
import player.AIPlayer;
import player.Player;

public class Solver {

	private Scanner scan;
	private Player player1;
	private Player player2;
	private GameBoard gb;

	public Solver() {
		gb = new GameBoard();
		scan = new Scanner(System.in);
		System.out.println("Who's going first, you (1) or your opponent (2)?");
		if (scan.hasNextLine()) {
			String str = scan.nextLine();
			if (str.equals("1") || str.equalsIgnoreCase("me")) {
				player1 = new Player(GameBoard.WHITE);
				player2 = new AIPlayer(GameBoard.BLACK, 1);
			} else {
				player1 = new AIPlayer(GameBoard.WHITE, 1);
				player2 = new Player(GameBoard.BLACK);
			}
		}
	}

	public void solve(int count) {
		boolean stop1 = false, stop2 = false;
		do {
			stop1 = false;
			stop2 = false;
			System.out.println(gb);
			stop1 = player1.makeMove(gb);
			// System.out.println("Score: \nPlayer " + gb.WHITE + ": " +
			// gb.getTilesOfColor(gb.WHITE) + "\nPlayer "
			// + gb.BLACK + gb.getTilesOfColor(gb.BLACK));

			if (!stop1) {
				System.out.println(gb);
				stop2 = player2.makeMove(gb);
			}

			// System.out.println("Score: \nPlayer " + gb.WHITE + ": " +
			// gb.getTilesOfColor(gb.WHITE) + "\nPlayer "
			// + gb.BLACK + gb.getTilesOfColor(gb.BLACK));
		} while (!stop1 && !stop2);

		System.out.println(gb);

		// System.out.println("Scores \n\tPlayer " + GameBoard.WHITE + ": " +
		// gb.getTilesOfColor(GameBoard.WHITE) + "\n\tPlayer " + gb.BLACK + ": " +
		// gb.getTilesOfColor(GameBoard.BLACK));
	}
}
