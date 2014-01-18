package com.dcgroup02.mastermind.presentation;

import javax.swing.JPanel;

import com.dcgroup02.mastermind.constants.GameColor;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Dot Shape Object used in GUI
 * 
 * @author Ian Ozturk, Anton Shevchencko, Darrel-Day Guerrero
 * @version 1.0
 * @since jdk 7.0
 */
public class DotChooser extends JPanel {

	private Color dotSelected;
	private static final long serialVersionUID = -2148777945855840566L;
	private GamePanel gamePanel;

	/**
	 * Create the panel.
	 */
	public DotChooser(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		setOpaque(false);
		setLayout(new GridLayout(2, 4, 5, 10));
		
		GameColor[] allColors = GameColor.values();

		for (int i = 3; i < allColors.length; i++) {
			Dot dot = new Dot(allColors[i].getColor());
			dot.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent me) {
					dotSelected(me);
				}
			});
			add(dot);
		}
	}

	/**
	 * Sets and selects the selected dot's color 
	 * @param me Dot
	 */
	private void dotSelected(MouseEvent me) {
		if (gamePanel.getIsActive()) {
			dotSelected = ((Dot) me.getSource()).getColor();
		}
	}

	/**
	 * Accesses the dot selected
	 * @return the Color
	 */
	public Color getDotSelected() {
		return dotSelected;
	}
}
