package huckleBuckle;

/**
 * A non-interactive console and graphic application which simulates a children's game.
 * <p>
 * Usage: hbbv2.0 [gridsize]
 * <p>
 * gridsize, the size of the playing field, is an integer between 1 and 40 inclusive.
 * <p>
 * When versioning this code, programmers should adjust the values of
 * HuckleBuckle.VERSION and serialVersionUID.
 * <p>
 * TODO: put this project into a version-control system, so that all classes
 * (including this one) are version-stamped, so that versions don't get
 * confused, so that versions can be conveniently stored and retrieved and
 * archived, and so that the versioning constants are updated reliably.
 *
 * @author
 *
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.Timer;

public class HuckleBuckle extends JPanel implements ActionListener {

	public static final String VERSION = "2.01";
	private static final long serialVersionUID = 3896500931075858726L;
	/**
	 * Note: serialization is *not* examinable.
	 *
	 * If you want to learn about serialization, I encourage you to read Chapter
	 * 11 of Joshua Bloch's Effective Java, 2nd Edition. This book is available
	 * online through our University's library.
	 */

	// Seekers move once every DELAY milliseconds.
	final static int DELAY = 300;

	// Each instance of HuckleBuckle simulates one game of Huckle Buckle
	// Beanstalk; and we'll only instantiate one HuckleBuckle.
	static Game myGame;

	 /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
		JFrame frame = new JFrame("HuckleBuckle viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setPreferredSize(new Dimension(500, 500));

        //Create the menu bar.  Make it have a green background.
        //TODO: add menu items to control the game
        JMenuBar greenMenuBar = new JMenuBar();
        greenMenuBar.setOpaque(true);
        greenMenuBar.setBackground(new Color(154, 165, 127));
        greenMenuBar.setPreferredSize(new Dimension(200, 20));

        frame.setJMenuBar(greenMenuBar);
        frame.getContentPane().add(new HuckleBuckle(), BorderLayout.CENTER);

        //Display the window.
        frame.pack(); // it'll be just big enough for the preferred sizes of its components
        frame.setVisible(true);
    }

    /**
	 * Instantiates some (simulated) children, and transfers control to the
	 * hider.
	 */
	public HuckleBuckle() {

		myGame.setHider(new Hider("Harry"));

		Set<Seeker> allSeekers = new HashSet<Seeker>();
		allSeekers.add(new ColSeeker("Sue"));
		//TODO: repair the defect which prevents the following statement from
		//adding a second Seeker to the set of allSeekers.  See the TODO in the Player class.
		allSeekers.add(new RowSeeker("Sally"));
		myGame.setSeekers(allSeekers);

		myGame.setTimer(new Timer(HuckleBuckle.DELAY, this));

		// Ask the hider to complete the setup for the game.
		myGame.getHider().pleasePlayWith(allSeekers, myGame);

	}

	public static void main(String[] args) {

		int gridSize = 5; // gridSize == 5, if there are no command-line args

		if (args.length > 0) {
			try {
				gridSize = Integer.parseInt(args[0]);
			} catch (NumberFormatException e) {
				System.err
						.println("Error: first arg (gridSize) must be an integer.  ");
				System.exit(1);
			}
			if (gridSize < 1 || gridSize > 40) {
				System.err
						.println("Error: first arg (gridSize) must be in the range 1..40.  ");
				System.exit(1);
			}
		}
		if (args.length > 1) {
			System.err.println("Warning: too many args.  ");
		}

		System.out.println("Playing HuckleBuckle (v" + HuckleBuckle.VERSION
				+ ") on a " + gridSize + " by " + gridSize + " grid...");
		System.out.println();

		myGame = new Game(gridSize);

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });

		// Note: the Swing framework continues to run after main() exits!
		// This is desirable behaviour, because it allows our simulation
		// to respond to Timer events until its window is dismissed.
	}

	/*
	 * The game's timer events are handled here
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		for (Seeker s : myGame.getSeekers()) {
			s.move(); // change the model, to reflect the passage of time
			repaint(); // update the view, for consistency with the model
			// This is your first Swing app, so I'm keeping it simple, however
			// in intermediate GUI programming you'd learn how to avoid
			// global-repaints (such as the one above) by factoring
			// the repaint(), so that only the affected portions of the
			// view are updated. If you want to learn more, see
			// http://docs.oracle.com/javase/tutorial/uiswing/painting/refining.html
		}
	}

	/*
	 * Paints the game-grid and the seekers.
	 *
	 * Note that this method may be invoked in two different ways: by the
	 * repaint() call in our actionPerformed() method immediately above, and by
	 * any resizing of the display window. The former invocation is called an
	 * "app-generated painting", and the latter is a "system- generated
	 * painting".
	 *
	 * The detailed semantics of painting and repainting in Swing are not
	 * examinable. However if you want to learn more, I'd suggest you read Amy
	 * Fowler's article "Painting in AWT and Swing: Good Painting Code is the
	 * Key to App Performance", 2003. Available:
	 * http://www.oracle.com/technetwork/java/painting-140037.html.
	 */
	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);

		// A HuckleBuckle instance is-a JPanel, so it has a display area
		// in a GUI window which may be resized. How big is it now?
		int width = getSize().width;
		int height = getSize().height;

		// Grid cells are square, even when the display area is rectangular
		int cellSize = Math.min(width, height) / myGame.getGridSize();

		// Display grid in checkerboard gray, initially.
		// FIXME: gridCells should be painted in color, after their temperatures
		// are revealed
		Color lighterGray = new Color(150, 150, 150);
		for (GridCell gc : myGame.getGrid()) {
			if (((gc.x % 2) ^ (gc.y % 2)) == 0) {
				g.setColor(Color.LIGHT_GRAY);
			} else {
				g.setColor(lighterGray);
			}
			// Note: I scale the cell indices (gc.x, gc.y) by cellSize,
			// so that cells are of an appropriate size for our current view-window.
			g.fillRect(gc.x * cellSize, gc.y * cellSize, cellSize, cellSize);
		}

		// display Seeker names in black texxt
		g.setColor(Color.BLACK);

		int numSeekers = myGame.getSeekers().size();

		// Offset seeker names within each grid cell, to avoid overlap
		int xOffset = 0;
		int yOffset = 0;
		int deltaOffset = cellSize / numSeekers;

		for (Seeker s : myGame.getSeekers()) {
			// The semantics of font-rendering in AWT is not examinable!
			// Here I'm calculating margins for a string-display within a cell.
			int xMargin = (deltaOffset - g.getFontMetrics().stringWidth(
					s.getName())) / 2;
			int yMargin = (deltaOffset - g.getFontMetrics().getHeight()) / 2
					+ g.getFontMetrics().getAscent();
			// Note: I scale the cell indices (s.x, s.y) by cellSize,
			// so that the seeker-strings are displayed inside their cell.
			g.drawString(s.getName(), s.x * cellSize + xOffset + xMargin, s.y
					* cellSize + yOffset + yMargin);
			// Seeker names are offset diagonally.
			xOffset += deltaOffset;
			yOffset += deltaOffset;
		}
	}

}