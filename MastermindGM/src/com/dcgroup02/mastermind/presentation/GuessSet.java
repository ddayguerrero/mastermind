package com.dcgroup02.mastermind.presentation;

import javax.swing.JPanel;

import com.dcgroup02.mastermind.constants.GameColor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Guess Set in GUI. Hold an array of 4 dots.
 * 
 * @author Ian Ozturk, Anton Shevchencko, Darrel-Day Guerrero
 * @version 1.0
 * @since jdk 7.0
 */
public class GuessSet extends JPanel {

	private static final long serialVersionUID = -3282569370762714989L;
	private GamePanel gamePanel;
	private Dot[] dots;

	/**
	 * Default constructor.
	 * @param gamePanel
	 */
	public GuessSet(GamePanel gamePanel) {
		this.gamePanel = gamePanel;

		setOpaque(false);
		setLayout(new GridLayout(1, 0, 0, 0));

		dots = new Dot[4];
		//Sets the default color of the dots
		for (int i = 0; i < 4; i++) {
			Dot dot = new Dot(GameColor.DEFAULT.getColor());
			dots[i] = dot;

			dot.addMouseListener(new MouseAdapter() {
				//Calls the changeDot(MouseEvent) method when a dot is clicked
				public void mousePressed(MouseEvent me) {
					changeDot(me);
				}
			});
			add(dot);
		}

	}
	
	/**
	 * Checks if dot is selected
	 * @param me
	 */
	private void changeDot(MouseEvent me) {
		if (gamePanel.getIsActive())
			changeDotColor((Dot) me.getSource());
	}

	/**
	 * Changes the dot color
	 * @param dot
	 */
	private void changeDotColor(Dot dot) {
		Color color = gamePanel.checkIfDotSelected();
		if (color != null) {
			dot.setColor(color);
			gamePanel.repaint();
		}
	}

	/**
	 * Accesses the Dots
	 * @return dots
	 */
	public Dot[] getDots() {
		return dots;
	}
}
