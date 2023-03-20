package huckleBuckle;

import java.lang.Math;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static huckleBuckle.Temperature.*;

/**
 * @author
 *
 */
class Hider extends Player {

	private static final long serialVersionUID = -4381028515915809405L;

	Set<Seeker> readySeekers; // The seekers who are ready to play.

	Hider(String myName) {
		super(myName);
	}

	/**
	 * This method is invoked by main(), and causes the Hider to play
	 * a game of HuckleBuckle with some Seekers
	 *
	 * @param mySeekers
	 * @param myGame
	 *
	 */
	void pleasePlayWith(Collection<Seeker> mySeekers, Game myGame) {

		setGame(myGame);  // I remember what game I'm playing!

		readySeekers = new HashSet<Seeker>(); // no seeker is ready to play

		// I invite each seeker to play with me
		for (Seeker s : mySeekers) {
			System.out.println("  " + getName() + " says \"Hi "
						+ s.getName() + ", I'm " + getName()
						+ ", let's play Huckle Buckle!\"");
			s.pleasePlayWith(this, getGame());
			// Each seeker will respond, using my pleaseHideSomething() method.
			// Note that I must reveal my identity (this) to my Seekers so that they
			// can invoke one of my methods for a callback.
		}
	}

	/**
	 * This method is invoked by a Seeker, in response to a pleasePlayWith() invitation.
	 *
	 * The Hider updates the list of readySeekers.  If all seekers have responded,
	 * then the Hider hides an object and starts the game timer.  The game timer
	 * will invoke the Seeker's move() methods at regular intervals, simulating the
	 * passage of time.
	 *
	 */
	void pleaseHideSomething(Seeker s) {

		readySeekers.add(s);

		// have all seekers responded yet?
		if (getGame().getSeekers().size() == readySeekers.size()) {
			// Yes!  Hide an object, then start the timer so that the seekers will move()
			setX((int) (Math.random() * getGame().getGridSize()));
			setY((int) (Math.random() * getGame().getGridSize()));
			System.out
					.println("  "
							+ getName()
							+ " says to everyone, \"I'm ready now.  I bet you can't find it!\"");
			getGame().getTimer().start();
		}
	}

	/**
	 *
	 * This method is invoked by a Seeker who has moved, and who wants to know
	 * if she is near the hidden object.
	 *
	 * @param s
	 *            a Seeker
	 * @return Temperature.FOUNDIT, if Seeker s has found the hidden object.
	 *         Returns a "cooler" Temperature if the Seeker is farther away.
	 *
	 *         <p>
	 *         Note that the Hider determines how non-zero distances are mapped
	 *         onto Temperatures. If this mapping were instead defined by some
	 *         "rules of the game", then the mapping of distances onto
	 *         temperatures should be declared in the Temperature enum by adding
	 *         parameters and/or methods to this enum, see
	 *         https://docs.oracle.com/javase/tutorial/java/javaOO/enum.html
	 */
	Temperature pleaseRevealTemperatureOf(Seeker s) {

		// compute a scaled distance. The range of r is 0.0 to 1.414
		double r = distance(s) / (double) getGame().getGridSize();

		System.out.print("  " + getName() + " says to " + s.getName() + ", \"");

		if (r == 0.0) {
			System.out.println("Huckle buckle beanstalk!\"");
			return FOUNDIT;
		} else if (r <= 0.25) {
			System.out.println("You're boiling!\"");
			return BOILING;
		} else if (r <= 0.35) {
			System.out.println("You're hot.\"");
			return HOT;
		} else if (r <= 0.5) {
			System.out.println("You're warm.\"");
			return WARM;
		} else if (r <= 0.7) {
			System.out.println("You're cool.\"");
			return COOL;
		} else if (r <= 1.0) {
			System.out.println("You're cold.\"");
			return COLD;
		} else {
			System.out.println("freezing!\"");
			return FREEZING;
		}
	}

}