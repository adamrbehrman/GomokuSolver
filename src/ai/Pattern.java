package ai;

import gameboard.CustomPoint;
import gameplay.Board;

public class Pattern {

	private Board board;
	private double score;
	private CustomPoint point;

	public Pattern(Board pattern, double score, CustomPoint point) {
		this.board = pattern.copyOf();
		this.score = score;
		this.point = point;
	}

	public Pattern() {
		this.board = new Board();
		this.point = new CustomPoint();
	}

	public double getScore() {
		return score;
	}

	public Board getBoard() {
		return board;
	}

	public String toString() {
		return "Pattern: \n" + board.getBoardAsString() + "Location: " + point + " | Score: " + score + "\n";
	}

	public void setScore(double score) {
		this.score = score;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public CustomPoint getPoint() {
		return point;
	}

	public void setPoint(CustomPoint point) {
		this.point = new CustomPoint(point.x, point.y);
	}

	public Pattern copyOf() {
		return new Pattern(board.copyOf(), score, point.copyOf());
	}

	public boolean equals(Pattern pattern2) {
		if (pattern2.getScore() != this.getScore() || !pattern2.getPoint().equals(this.getPoint())) {
			return false;
		}
		return pattern2.getBoard().equals(this.getBoard());
	}
}
