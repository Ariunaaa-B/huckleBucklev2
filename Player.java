package huckleBuckle;

import java.awt.*;

abstract class Player extends Point {
	// constraint: 0 <= x < myGame.gridSize()
	// constraint: 0 <= y < myGame.gridSize()

	private static final long serialVersionUID = -5253550170404888625L;
	private String myName;

	private Game myGame; // note: uninitialised for new Players

	//TODO: override the equals() method of Point, so that a Set<Seeker>
	//can have multiple Seekers at the same location of the grid.

	Player(String myName) {
		this.myName = myName;
		setLocation(0,0); // all players start at (0,0)
	}

	String getName() {
		return myName;
	}

	Game getGame() {
		return myGame;
	}

	void setGame(Game myGame) {
		this.myGame = myGame;
	}

	void setX(int px) {
		setLocation(px, getY());
	}

	void setY(int py) {
		setLocation(getX(), py);
	}

	@Override
	public String toString() {
		return getName() + " at (" + (int) getX() + ", " + (int) getY() + ")";

	}

}