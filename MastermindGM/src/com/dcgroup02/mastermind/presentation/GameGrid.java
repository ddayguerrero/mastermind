package com.dcgroup02.mastermind.presentation;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import com.dcgroup02.mastermind.constants.GameColor;

/**
 * Game Grid for Mastermind game. 
 * 
 * @author Ian Ozturk, Anton Shevchencko, Darrel-Day Guerrero
 * @version 1.0
 * @since jdk 7.0
 */
public class GameGrid extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2747108444194560992L;
	private Set[][] sets;
	private GamePanel gamePanel;

	/**
	 * Create the panel.
	 */
	public GameGrid(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		setBorder(new LineBorder(GameColor.DEFAULT.getColor(), 20));
		
		setLayout(new GridLayout(10, 2, 2, 5));
		setBackground(GameColor.DEFAULT.getColor());

		sets = new Set[10][2];
		
		for (int i = 0; i < sets.length; i++) {
			// Creates the left side game board : guesses
			Set set1 = new Set();
			sets[i][0] = set1;
			add(sets[i][0]);
			
			// Creates the right side game board : hints
			Set set2 = new Set();
			sets[i][1] = set2;
			add(sets[i][1]);
		}

	}
	
	/**
	 * Changes the colors of a set
	 * @param row Game Grid row position
	 * @param col Game Grid column position
	 * @param colors Color
	 */
	public void changeSetColors(int row, int col, Color[] colors)
	{
		for (int i = 0; i < 4; i++)
		{
			sets[row][col].setDots(colors);
		}
		
		gamePanel.repaint();
	}

}