package gameboard;

public class Tile {

	private CustomPoint coords;
	private char discColor;

	public Tile() {
	}

	public Tile(CustomPoint p) {
		this.coords = p;
		this.discColor = GameBoard.EMPTY;
	}

	public Tile(CustomPoint p, char color) {
		this.coords = p;
		this.discColor = color;
	}

	public char getDiscColor() {
		return discColor;
	}

	public void setCoords(CustomPoint point) {
		this.coords = point;
	}

	public CustomPoint getCoords() {
		return coords;
	}

	public void setDiscColor(char color) {
		discColor = color;
	}

	public boolean equals(Tile tile2) {
		return this.getCoords().equals(tile2.getCoords()) && this.getDiscColor() == tile2.getDiscColor();
	}

	@Override
	public String toString() {
		return "Tile at " + coords + " for Player " + discColor;

	}

	public Tile copyOf() {
		return new Tile(this.getCoords(), this.getDiscColor());
	}
}
