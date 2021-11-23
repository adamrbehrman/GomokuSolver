package gameplay;

import java.util.List;
import gameboard.Tile;
import player.Player;

public class Move implements Comparable<Move> {

	private Tile tile;
	private Player player;
	
	private List<Move> nextMoves;
	private double playScore;

	public Move(Tile tile, Player player) {
		this.tile = tile;
		this.player = player;
	}
	
	public Tile getTile() {
		return tile;
	}

	public void setTile(Tile tile) {
		this.tile = tile;
	}
	
	public char getPlayerColor() {
		return player.getColor();
	}

	public double getPlayScore() {
		return playScore;
	}

	public void setPlayScore(double playScore) {
		this.playScore = playScore;
	}

	public void addNextMove(Move move) {
		nextMoves.add(move);
	}

	public List<Move> getNextMoves() {
		return nextMoves;
	}

	public void setNextMoves(List<Move> nextMoves) {
		this.nextMoves = nextMoves;
	}

	@Override
	public String toString() {
		if (nextMoves != null && !nextMoves.isEmpty()) {
			String str = "";
			for (Move move : this.nextMoves) {
				str += move.getPlayScore() + "\n";
			}
			return tile + "\nScore: " + this.getPlayScore() + "\nNext moves scores: \n" + str + "\n";
		} else {
			return tile + "\nScore: " + this.getPlayScore() + "\n";
		}
	}

	@Override
	public int compareTo(Move move) {
		return (int) ((move.getPlayScore() * 100) - (this.getPlayScore() * 100));
	}

}
