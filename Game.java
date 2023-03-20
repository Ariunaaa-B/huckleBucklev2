package huckleBuckle;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.Timer;

class Game {

	private int myGridSize;
	private List<GridCell> myGrid;
	private Hider myHider;
	private Set<Seeker> mySeekers;
	private Timer myTimer;

	Game(int gs) {

		myGridSize = gs;

		myGrid = new ArrayList<GridCell>(gs*gs);
		for (int i = 0; i < getGridSize(); i++ ) {
			for (int j = 0; j < getGridSize(); j++ ) {
				myGrid.add(new GridCell(i, j, Temperature.UNKNOWN));
			}
		}
	}

	Hider getHider() {
		return myHider;
	}

	void setHider(Hider h) {
		myHider = h;
	}

	Set<Seeker> getSeekers() {
		return mySeekers;
	}

	void setSeekers(Set<Seeker> s) {
		mySeekers = s;
	}

	int getGridSize() {
		return myGridSize;
	}

	Timer getTimer() {
		return myTimer;
	}

	void setTimer(Timer t) {
		myTimer = t;
	}

	List<GridCell> getGrid() {
		return myGrid;
	}
}