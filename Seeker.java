package huckleBuckle;

import static huckleBuckle.Seeker.SeekerState.*;

abstract class Seeker extends Player {

	private static final long serialVersionUID = -7807300368225621143L;

	enum SeekerState {
		WAITING, STARTING, MOVING, SEEKING, WINNING, QUITTING
	}

	private SeekerState myState;
	private Hider myHider;
	private int distanceMoved; // updated after every change of position
	int nextX; // my immediate destination is (nextX, nextY)
	int nextY;

	Seeker(String myName) {
		super(myName);
		myState = WAITING;
	}

	/**
	 * A Hider uses this method to invite a Seeker to a game
	 *
	 * @param inviter
	 *            The Hider who is offering to play with this Seeker.
	 * @param game
	 *            The Game they'll play.
	 */
	void pleasePlayWith(Hider inviter, Game game) {

		// Have I already met this hider?
		if (inviter != myHider) {
			System.out.println(getName() + " says, \"Hi, " + inviter.getName()
					+ ", I'm " + getName()
					+ ".  Glad to meet you!  Are you ready?\"");
		} else {
			System.out.println(getName() + " says, \"OK!  Are you ready?\"");
		}

		myHider = inviter; // I remember who invited me to play
		setGame(game); // I remember what game we're playing

		// I ask the hider to get ready to play our game
		myHider.pleaseHideSomething(this);

		distanceMoved = 0; // reset distance counter

		myState = STARTING; // I'm ready to move()!
	}

	/**
	 * Invoking this method causes this Seeker to make one move in her current
	 * Game.
	 *
	 * @return true if the Seeker can make any more moves in this game.
	 *
	 */
	public boolean move() {

		switch (myState) {
		case WAITING: // I could play another game if a Hider would invite me...
			// but I won't do anything without an invitation!
			break;
		case STARTING:
			nextX = 0; // I always look at (0,0) first
			nextY = 0;
			System.out.println(getName() + " says, \"I took a step.  I'm now at "
					+ (int) getX() + ", " + (int) getY() + ".\"");
			myState = MOVING;
			break;
		case MOVING:
			if ((getX() == nextX) && (getY() == nextY)) {
				// I have reached my destination
				myState = SEEKING;
			} else {
				// I move one step toward (nextX, nextY)
				translate((int) Math.signum(nextX - getX()),
						(int) Math.signum(nextY - getY()));
				distanceMoved++;
				System.out.println(getName() + " says, \"I took a step.  I'm now at "
						+ (int) getX() + ", " + (int) getY() + ".\"");
			    myState = MOVING; // I'm still moving
			}
			break;
		case SEEKING:
			if (myHider.pleaseRevealTemperatureOf(this) == Temperature.FOUNDIT) {
				myState = WINNING; // Hooray!
			} else if (tryNewPosition()) {
				myState = MOVING; // I keep looking
			} else {
				myState = QUITTING; // I give up!
			}
			break;
		case QUITTING: // I don't move, but I do say something before WAITING.
			System.out.println(getName() + " says, \"I give up!  I took "
					+ distanceMoved + " steps before quitting.\"");
			myState = WAITING;
			break;
		case WINNING: // I don't move, but I do say something before WAITING.
			System.out.println(getName() + " says, \"That was fun! I walked "
					+ distanceMoved + " step" + (distanceMoved != 1 ? "s" : "")
					+ " before I found it.\"");

			myState = WAITING;
			break;
		}
		return (myState != WAITING);
	}

	/**
	 * I try to find a new destination (nextX, nextY).
	 *
	 * @return false if I have already looked everywhere.
	 *
	 */
	abstract boolean tryNewPosition();

}