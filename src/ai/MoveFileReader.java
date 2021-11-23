package ai;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;
import java.util.Set;

import gameboard.CustomPoint;
import gameboard.GameBoard;
import gameboard.Tile;
import gameplay.Board;
import player.Player;

public class MoveFileReader {

	private final String filename = "src/ai/GomokuPatterns.txt";

	private List<String> rawPatterns;

	private final char PLAYER = 'P';
	private final char PLACE_AT = 'X';
	private final char NON_PLAYER = 'N';
	private final char ANY = '*';

	public MoveFileReader(Player player, char otherPlayerColor) {
		File file = new File(filename);
		rawPatterns = readFile(file);
	}

	public List<Pattern> analyzeBoardForPatterns(GameBoard gb, char playerColor, char otherPlayerColor) {
		List<Pattern> patternList = readPatterns(rawPatterns, playerColor);

		Set<Pattern> orientedList = generateOrientedPatterns(patternList);

		for (Pattern p : orientedList) {
			System.out.println(p);
		}

		List<Pattern> potentialPatterns = getPotentialPatterns(orientedList, gb, otherPlayerColor);
		System.out.println("\nInitial patterns detected for player " + playerColor + ": " + potentialPatterns.size());

		List<Pattern> completePatterns = eliminateIncompletePatterns(potentialPatterns, gb, otherPlayerColor);
		System.out.println("\nComplete patterns detected for player " + playerColor + ": " + potentialPatterns.size());

		System.out.println();
		return completePatterns;
	}

	private List<Pattern> eliminateIncompletePatterns(List<Pattern> potentialPatterns, GameBoard gb,
			char otherPlayerColor) {
		ListIterator<Pattern> iter = potentialPatterns.listIterator();
		while (iter.hasNext()) {
			Pattern pattern = iter.next();
			Board board = pattern.getBoard().copyOf();
			CustomPoint startPoint = pattern.getPoint();

			boolean completePattern = true;
			int x = 0;

			while (completePattern && x < board.getLength()) {
				int y = 0;
				while (completePattern && y < board.getHeight()) {
					int xc = startPoint.x + x, yc = startPoint.y + y;
					// Out of bounds errors
					if (gb.isOnBoard(new CustomPoint(xc, yc))) {
						Tile gridTile = board.getTileAt(new CustomPoint(x, y));
						Tile gbt = gb.getTileAt(new CustomPoint(xc, yc));

						if (!tilesMatch(gridTile, gbt, otherPlayerColor)) {
							completePattern = false;
						}
						y++;
					} else {
						completePattern = false;
					}
				}
				x++;
			}
			if (!completePattern) {
				iter.remove();
			}
		}
		return potentialPatterns;
	}

	private List<Pattern> getPotentialPatterns(Set<Pattern> orientedList, GameBoard gb, char otherPlayerColor) {
		List<Pattern> potentialPatterns = new ArrayList<Pattern>();

		// Go through all the grid patterns
		for (Pattern pattern : orientedList) {
			Pattern copy = pattern.copyOf();
			// Go through the GameBoard to see if the pattern exists
			for (int i = 0; i < gb.getBoardSize(); i++) {
				for (int j = 0; j < gb.getBoardSize(); j++) {
					int x = 0, y = 0;
					Tile gridTile = copy.getBoard().getTileAt(new CustomPoint(x, y));
					Tile gbt = gb.getTileAt(new CustomPoint(i, j));

					if (tilesMatch(gridTile, gbt, otherPlayerColor)) {
						copy.setPoint(new CustomPoint(i, j));
						potentialPatterns.add(copy.copyOf());
					}
				}
			}

		}
		return potentialPatterns;
	}

	private Set<Pattern> generateOrientedPatterns(List<Pattern> list) {
		Set<Pattern> orientedPatterns = new LinkedHashSet<Pattern>();

		// Go through all the grid patterns
		for (Pattern pattern : list) {
			orientedPatterns.add(pattern.copyOf());
			System.out.println("MATCH ADDED *************" + pattern.copyOf());

			Board board = pattern.getBoard();

			boolean rotate = false;
			// if square, rotate 4 times + reverse -> otherwise reverse
			if (board.getLength() == board.getHeight()) {
				rotate = true;
			}

			// Rotating it 4 times - reflecting when not symetrical??
			if (rotate) {
				for (int r = 1; r <= 3; r++) {
					Pattern rotatedPattern = pattern.copyOf();
					rotatedPattern.getBoard().rotateBoard(r);

					boolean match = true;
					for (Pattern p : orientedPatterns) {
//						System.out.println("Rotated " + p + " \nReversed " + reversedPattern);
						if (p.equals(rotatedPattern)) {
//							System.out.println("NO MATCH **********");
							match = false;
							break;
						}
					}

					if (match) {
						System.out.println("MATCH ADDED *************" + rotatedPattern);
						orientedPatterns.add(rotatedPattern);
					}

					Pattern reversedPattern = rotatedPattern.copyOf();
					reversedPattern.getBoard().reverseGrid(1);

					match = true;
					for (Pattern p : orientedPatterns) {
//						System.out.println("Rotated " + p + " \nReversed " + reversedPattern);
						if (p.equals(reversedPattern)) {
//							System.out.println("NO MATCH **********");
							match = false;
							break;
						}
					}
					if (match) {
						System.out.println("MATCH ADDED *************" + reversedPattern);
						orientedPatterns.add(reversedPattern);
					}
				}
			} else {
				Pattern copy = pattern.copyOf();
				copy.getBoard().reverseGrid(1);
				orientedPatterns.add(copy);
				System.out.println("MATCH ADDED *************" + copy);
			}
		}
		return orientedPatterns;
	}

	private boolean tilesMatch(Tile gridTile, Tile gbt, char nonplayer) {
		return gridTile.getDiscColor() == gbt.getDiscColor()
				|| (gridTile.getDiscColor() == this.NON_PLAYER && gbt.getDiscColor() != nonplayer)
				|| (gridTile.getDiscColor() == this.PLACE_AT && gbt.getDiscColor() == GameBoard.EMPTY);
	}

	private List<Pattern> readPatterns(List<String> rawPatterns, char playerColor) {
		List<Pattern> patternList = new ArrayList<Pattern>();

		boolean firstLine = true;
		Pattern pattern = null;
		int y = 0;

		for (String line : rawPatterns) {
			if (line.equals("-")) {
				patternList.add(pattern);
				firstLine = true;
			} else {
				try {
					double score = Double.parseDouble(line);
					pattern.setScore(score);
				} catch (NumberFormatException e) {
					if (firstLine) {
						pattern = new Pattern();
						y = 0;
					}
					firstLine = false;

					Tile[] row = new Tile[line.length()];
					for (int x = 0; x < line.length(); x++) {
						char c = line.charAt(x);
						switch (c) {
						case PLAYER: // Player
							c = playerColor;
							break;
						case GameBoard.EMPTY: // Empty
							c = GameBoard.EMPTY;
							break;
						case NON_PLAYER: // Non-player
							c = NON_PLAYER;
							break;
						case ANY: // Any"thing"
							c = GameBoard.EMPTY;
							break;
						case PLACE_AT: // Play at
							c = PLACE_AT;
							break;
						}
						row[x] = new Tile(new CustomPoint(x, y), c);
					}
					y++;
					pattern.getBoard().addRow(row);
				}
			}
		}

		return patternList;
	}

	private List<String> readFile(File file) {
		List<String> strings = new ArrayList<String>();

		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (scan.hasNextLine()) {
			strings.add(scan.nextLine());
		}

		scan.close();
		return strings;
	}

}
