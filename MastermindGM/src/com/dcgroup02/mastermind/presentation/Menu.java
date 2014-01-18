package com.dcgroup02.mastermind.presentation;

import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import com.dcgroup02.mastermind.constants.GameColor;

/**
 * Menu for Mastermind game.
 * 
 * @author Ian Ozturk, Anton Shevchencko, Darrel-Day Guerrero
 * @version 1.0
 * @since jdk 7.0
 */
public class Menu extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1650148046995442728L;
	private AppFrame frame;

	/**
	 * Create the panel.
	 */
	public Menu(AppFrame frame) {
		this.frame = frame;
		
		Color background = new Color(255, 255, 255, 220);
		
		//set the background color of the menu
		setBackground(background);
		setLayout(null);
		
		//Create About button, set its properties, and add it to the menu
		JButton btnAbout = new JButton("About");
		btnAbout.addActionListener(new ActionListener() {
			//Calls the displayAbout() method when the About button is clicked
			public void actionPerformed(ActionEvent e) {
				displayAbout();
			}
		});
		btnAbout.setBounds(66, 179, 167, 29);
		btnAbout.setFont(new Font("Helvetica", Font.PLAIN, 18));
		btnAbout.setHorizontalAlignment(SwingConstants.CENTER);
		btnAbout.setForeground(GameColor.CYAN.getColor());
		btnAbout.setOpaque(false);
		btnAbout.setContentAreaFilled(false);
		btnAbout.setBorderPainted(false);
		add(btnAbout);
		
		//Create the Give Up button, set its properties, and add it to the menu
		JButton btnGiveUp = new JButton("Give Up");
		btnGiveUp.addActionListener(new ActionListener() {
			//Calls the sendForfeitPacket() method when the Give Up button is clicked
			public void actionPerformed(ActionEvent e) {
				sendForfeitPacket();
			}
		});
		btnGiveUp.setBounds(66, 219, 167, 29);
		btnGiveUp.setFont(new Font("Helvetica", Font.PLAIN, 18));
		btnGiveUp.setHorizontalAlignment(SwingConstants.CENTER);
		btnGiveUp.setForeground(GameColor.CYAN.getColor());
		btnGiveUp.setOpaque(false);
		btnGiveUp.setContentAreaFilled(false);
		btnGiveUp.setBorderPainted(false);
		add(btnGiveUp);
		
		//Create the Quit Game button, set its properties, and add it to the menu
		JButton btnQuit = new JButton("Quit Game");
		btnQuit.addActionListener(new ActionListener() {
			//Calls the quitGame() method when the Quit Game button is clicked
			public void actionPerformed(ActionEvent e) {
				quitGame();
			}
		});
		btnQuit.setBounds(66, 259, 167, 29);
		btnQuit.setFont(new Font("Helvetica", Font.PLAIN, 18));
		btnQuit.setHorizontalAlignment(SwingConstants.CENTER);
		btnQuit.setForeground(GameColor.CYAN.getColor());
		btnQuit.setOpaque(false);
		btnQuit.setContentAreaFilled(false);
		btnQuit.setBorderPainted(false);
		add(btnQuit);
		
		//Create the Instructions button, set its properties, and add it to the menu
		JButton btnInstructions = new JButton("Instructions");
		btnInstructions.addActionListener(new ActionListener() {
			//Calls the displayInstructions() method when the Instructions button is clicked
			public void actionPerformed(ActionEvent e) {
				displayInstructions();
			}
		});
		btnInstructions.setBounds(66, 299, 167, 29);
		btnInstructions.setFont(new Font("Helvetica", Font.PLAIN, 19));
		btnInstructions.setHorizontalAlignment(SwingConstants.CENTER);
		btnInstructions.setForeground(GameColor.CYAN.getColor());
		btnInstructions.setOpaque(false);
		btnInstructions.setContentAreaFilled(false);
		btnInstructions.setBorderPainted(false);
		add(btnInstructions);
		
		//Create the Back to Game button, set its properties, and add it to the menu
		JButton btnBack = new JButton("Back to Game");
		btnBack.addActionListener(new ActionListener() {
			//Calls the removeMenu() method when the Back to Game button is clicked
			public void actionPerformed(ActionEvent e) {
				removeMenu();
			}
		});
		btnBack.setBounds(66, 339, 167, 29);
		btnBack.setFont(new Font("Helvetica", Font.PLAIN, 18));
		btnBack.setHorizontalAlignment(SwingConstants.CENTER);
		btnBack.setForeground(GameColor.CYAN.getColor());
		btnBack.setOpaque(false);
		btnBack.setContentAreaFilled(false);
		btnBack.setBorderPainted(false);
		add(btnBack);
	}
	
	/**
	 * Sends a forfeit packet
	 */
	private void sendForfeitPacket() {
		frame.sendForfeitPacket();
	}
	
	/**
	 * Closes the frame
	 */
	private void quitGame() {
		// Close the frame, which will trigger event in GameClient.
		System.exit(0);
	}
	
	/**
	 * Removes the menu from the frame
	 */
	private void removeMenu() {
		frame.removeMenu();
	}
	
	/**
	 * Displays the instructions frame
	 */
	@SuppressWarnings("unused")
	private void displayInstructions() {
		Instructions ins = new Instructions(this);
	}
	
	/**
	 * Displays the about frame
	 */
	@SuppressWarnings("unused")
	private void displayAbout() {
		About about = new About(this);
	}
}