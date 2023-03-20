package huckleBuckle;

public class RowSeeker extends Seeker {

	private static final long serialVersionUID = -6084803437355194L;

	public RowSeeker(String myName) {
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
		if ((nextY % 2) == 0) { // I move right on even-numbered rows
			nextX++;
			if (nextX >= getGame().getGridSize()) {
				nextX--;
				nextY++; // I move to the next row
			}
		} else { // I move left on odd-numbered rows
			nextX--;
			if (nextX < 0) { // Am I past the end of a row?
				nextX++; // Yes, so I shouldn't move left
				nextY++; // I move to the next row
			}
		}
		if (nextY >= getGame().getGridSize()) {
			nextY--; // I don't move off the last row
			return false; // I haven't moved
		} else {
			return true;
		}
	}

}
