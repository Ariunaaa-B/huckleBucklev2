package huckleBuckle;

import java.awt.Point;

public class GridCell extends Point {

	private static final long serialVersionUID = -4483267296463845798L;

	private Temperature myTemperature;

	public GridCell(int x, int y, Temperature t) {
		setLocation(x,y);
		myTemperature = t;
	}

	public Temperature getTemperature() {
		return myTemperature;
	}

}
