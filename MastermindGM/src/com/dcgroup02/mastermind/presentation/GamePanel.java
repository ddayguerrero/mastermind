package com.dcgroup02.mastermind.presentation;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Timer;

import com.dcgroup02.mastermind.constants.GameColor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import javax.swing.JLabel;

/**
 * Game Panel for Mastermind game. Handles the initialization of all game
 * component's: JMenu, DotChooser, JButtons for send and menu, JLabels for
 * tries, etc.
 * 
 * @author Ian Ozturk, Anton Shevchencko, Darrel-Day Guerrero
 * @version 1.0
 * @since jdk 7.0
 */
public class GamePanel extends JPanel {

	private static final long serialVersionUID = -6144141703883694473L;
	private DotChooser dotChooser;
	private GuessSet guessSet;
	private int triesLeft;
	private JLabel lblTriesLeft;
	private GameGrid gameGrid;
	private AppFrame frame;
	private JButton btnSend, btnMenu;
	private boolean isActive, isDoubleClick;
	private Timer timer;

	/**
	 * Create the panel.
	 */
	public GamePanel(AppFrame appFrame) {
		this.frame = appFrame;

		isDoubleClick = false;
		isActive = true;
		triesLeft = 10;

		setOpaque(false);
		setLayout(new BorderLayout(0, 0));

		// Creates send JButton
		btnSend = new JButton("Send");
		btnSend.setBounds(80, 387, 138, 43);
		btnSend.setFont(new Font("Helvetica", Font.PLAIN, 22));
		btnSend.setForeground(GameColor.RED.getColor());
		btnSend.setOpaque(true);
		btnSend.setContentAreaFilled(false);
		btnSend.setBorderPainted(false);
		btnSend.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (isActive) {
					if (triesLeft == 10) {
						if (me.getClickCount() == 2) {
							sendAnswerSet();
							isDoubleClick = true;							
						} else {
							Integer timerinterval = new Integer(200);
							timer = new Timer(timerinterval.intValue(),
									new ActionListener() {
										public void actionPerformed(
												ActionEvent evt) {
											if (isDoubleClick) {
												isDoubleClick = false;												
											} else {												
												sendGuessSet();
											}
										}
									});
							timer.setRepeats(false);
							timer.start();
						}
					} else {
						sendGuessSet();
					}
				}
			}
		});
		add(btnSend);

		// Creates an empty guess set
		guessSet = new GuessSet(this);
		guessSet.setBounds(20, 316, 264, 60);
		add(guessSet);

		// Creates the dot color chooser
		dotChooser = new DotChooser(this);
		dotChooser.setBounds(44, 441, 226, 78);
		add(dotChooser);

		// Adds game grid
		gameGrid = new GameGrid(this);
		gameGrid.setBounds(0, 50, 300, 250);
		add(gameGrid);

		// Creates the menu JButton
		btnMenu = new JButton("Menu");
		btnMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (isActive)
					displayMenu();
			}
		});
		btnMenu.setBounds(-15, 12, 95, 29);
		btnMenu.setFont(new Font("Helvetica", Font.PLAIN, 18));
		btnMenu.setForeground(GameColor.CYAN.getColor());
		btnMenu.setOpaque(true);
		btnMenu.setContentAreaFilled(false);
		btnMenu.setBorderPainted(false);
		add(btnMenu);

		// Creates tries left JLabel
		JLabel lblTries = new JLabel("Tries Left:");
		lblTries.setBounds(185, 17, 85, 16);
		lblTries.setFont(new Font("Helvetica", Font.PLAIN, 18));
		add(lblTries);

		// Creates the tries value JLabel
		lblTriesLeft = new JLabel("" + triesLeft);
		lblTriesLeft.setBounds(267, 17, 20, 16);
		lblTriesLeft.setFont(new Font("Helvetica", Font.BOLD, 18));
		lblTriesLeft.setHorizontalAlignment(SwingConstants.TRAILING);
		add(lblTriesLeft);

		// Add a fake label to display the previous label.
		JLabel lblTest = new JLabel();
		add(lblTest);

	}

	private void sendAnswerSet() {
		Dot[] dots = guessSet.getDots();
		boolean isInvalid = false;

		// Check if dots in guess set are default color (not changed).
		for (int i = 0; i < dots.length; i++) {
			if (dots[i].getColor().equals(GameColor.DEFAULT.getColor()))
				isInvalid = true;
		}

		// Send guess set to session.
		if (!isInvalid) {
			int[] dotValues = new int[dots.length];
			// Convert dot colors to integer values.
			for (int i = 0; i < dots.length; i++) {
				dotValues[i] = getIntValueOfColor(dots[i].getColor());
			}

			frame.sendAnswerSet(dotValues);
		}
	}

	/**
	 * Sends guess set
	 */
	private void sendGuessSet() {
		Dot[] dots = guessSet.getDots();
		boolean isInvalid = false;

		// Check if dots in guess set are default color (not changed).
		for (int i = 0; i < dots.length; i++) {
			if (dots[i].getColor().equals(GameColor.DEFAULT.getColor()))
				isInvalid = true;
		}

		// Send guess set to session.
		if (!isInvalid) {
			int[] dotValues = new int[dots.length];
			// Convert dot colors to integer values.
			for (int i = 0; i < dots.length; i++) {
				dotValues[i] = getIntValueOfColor(dots[i].getColor());
			}

			frame.sendGuessSet(dotValues);

			Color[] colors = new Color[4];
			for (int i = 0; i < colors.length; i++)
				colors[i] = dots[i].getColor();

			gameGrid.changeSetColors(triesLeft - 1, 0, colors);

			if (triesLeft != 0)
				triesLeft--; // Lose one try

			// Sets a warning by changing foreground color
			if (triesLeft <= 3)
				lblTriesLeft.setForeground(GameColor.RED.getColor());

			lblTriesLeft.setText("" + triesLeft);
		}
	}

	private int getIntValueOfColor(Color color) {
		final GameColor[] allColors = GameColor.values();

		for (int i = 0; i < allColors.length; i++)
			if (color.equals(allColors[i].getColor()))
				return i;

		return -1;
	}

	private void displayMenu() {
		frame.displayMenu();
	}

	/**
	 * Returns dot Color if dot from the selector has been selected
	 * 
	 * @return Color
	 */
	public Color checkIfDotSelected() {
		return dotChooser.getDotSelected();
	}

	public void displayClue(Color[] colors) {
		gameGrid.changeSetColors(triesLeft - 1, 1, colors);
	}

	public Dot[] getLastGuessSet() {
		return guessSet.getDots();
	}

	/**
	 * Accesses isActive boolean
	 * 
	 * @return
	 */
	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}
}
