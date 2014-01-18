package com.dcgroup02.mastermind.presentation;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

/**
 * Dot Shape Object used in GUI
 * 
 * @author Ian Ozturk, Anton Shevchencko, Darrel-Day Guerrero
 * @version 1.0
 * @since jdk 7.0
 */
public class Dot extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6320759453755962555L;
	private Color color;

	/**
	 * Default constructor
	 * @param color
	 */
	public Dot(Color color) {
		this.color = color;

		setOpaque(false);
		setLayout(null);
	}

	/**
	 * Renders the Dot Ellipse shape with the associated color
	 * 
	 * @param g
	 *            - The Graphics Object
	 */
	@SuppressWarnings("unused")
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		int size = getHeight();
		if (getWidth() <= getHeight())
			size = getWidth();

		Ellipse2D.Double dot = new Ellipse2D.Double(0, 0, size - 5, size - 5);

		// Smoothen out the shape
		g2.setComposite(AlphaComposite.Src);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(color);
		g2.fillOval(0, 0, size, size);

		g2.setComposite(AlphaComposite.SrcAtop);
		g2.dispose();
	}

	/**
	 * Mutates the Color
	 * 
	 * @param color
	 *            Color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Accesses the Color
	 * 
	 * @return Color
	 */
	public Color getColor() {
		return color;
	}
}
