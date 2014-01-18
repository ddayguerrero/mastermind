package com.dcgroup02.mastermind.constants;

import java.awt.Color;

/**
 * The GameColor Enum represents all possible Color used as int values for
 * any packets sent during the communications between client-server.
 * 
 * @author Ian Ozturk, Anton Shevchencko, Darrel-Day Guerrero
 * @version 1.0
 * @since jdk 7.0
 */
public enum GameColor {

	DEFAULT(Color.decode("#e3e3e3")), BLACK(Color.BLACK), WHITE(Color.WHITE), PINK(
			Color.decode("#ff6b89")), RED(Color.decode("#f03b35")), ORANGE(
			Color.decode("#ff9500")), YELLOW(Color.decode("#ffcc00")), GREEN(
			Color.decode("#4ed965")), TEAL(Color.decode("#35acdb")), CYAN(Color
			.decode("#007bff")), PURPLE(Color.decode("#5757d4"));

	private Color color;

	/**
	 * Default constructor for Enum
	 * @param color - Color object
	 */
	private GameColor(Color color) {
		this.color = color;
	}

	/**
	 * Accesses the enum's Color value
	 * @return Color
	 */
	public Color getColor() {
		return color;
	}

}
