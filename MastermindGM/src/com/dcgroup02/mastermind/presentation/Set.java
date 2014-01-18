package com.dcgroup02.mastermind.presentation;

import javax.swing.JPanel;

import com.dcgroup02.mastermind.constants.GameColor;

import java.awt.Color;
import java.awt.GridLayout;

/**
 * Set for Mastermind game.
 * 
 * @author Ian Ozturk, Anton Shevchencko, Darrel-Day Guerrero
 * @version 1.0
 * @since jdk 7.0
 */
public class Set extends JPanel {

	private static final long serialVersionUID = -3282569370762714989L;
	private Dot[] dots;

	/**
	 * Create the panel.
	 */
	public Set() {

		setOpaque(false);
		setLayout(new GridLayout(1, 0, 0, 0));

		dots = new Dot[4];

		for (int i = 0; i < 4; i++) {
			Dot dot = new Dot(GameColor.DEFAULT.getColor());
			dots[i] = dot;
			add(dot);
		}

	}
	
	/**
	 * Sets color of the Set's Dots
	 * @param colors
	 */
	public void setDots(Color[] colors) {
		for (int i = 0; i < 4; i++)
			dots[i].setColor(colors[i]);
	}
}
