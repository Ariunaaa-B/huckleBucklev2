package huckleBuckle;

public class ColSeeker extends Seeker {

	private static final long serialVersionUID = -8201110213318737563L;

	public ColSeeker(String myName) {
		super(myName);
	}

	/**
	 * Updates (nextX, nextY): a new immediate destination
	 *
	 * I move right on even-numbered rows, then left on odd-numbered rows
	 *
	 * @return false if I  have already looked everywhere
	 *
	 */
	@Override
	boolean tryNewPosition() {
		nextX = (int) getX(); // px will be my next x-pos
		nextY = (int) getY(); // py will be my next y-pos
		if ((nextX % 2) == 0) { // I move down (y++) on even-numbered columns
			nextY++;
			if (nextY >= getGame().getGridSize()) {
				nextY--;
				nextX++; // I move to the next column (x++)
			}
		} else { // I move up (y--) on odd-numbered columns
			nextY--;
			if (nextY < 0) { // Am I past the end of a column?
				nextY++; // Yes, so I shouldn't move down
				nextX++; // I move to the next column
			}
		}
		if (nextX >= getGame().getGridSize()) {
			nextX--; // I don't move off the last column
			return false; // I have looked everywhere!
		} else {
			return true;
		}
	}
}
