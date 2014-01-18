package com.dcgroup02.mastermind.presentation;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import com.dcgroup02.mastermind.constants.GameColor;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;

/**
 * Game Finished JPanel for Mastermind.
 * 
 * @author Ian Ozturk, Anton Shevchencko, Darrel-Day Guerrero
 * @version 1.0
 * @since jdk 7.0
 */
public class GameFinished extends JPanel {

	private static final long serialVersionUID = 6775840522415014496L;
	private AppFrame frame;
	
	/**
	 * Create the panel.
	 */
	public GameFinished(AppFrame frame, String msg, Color[] answer, Color[] guess) {
		this.frame = frame;
		
		Color background = new Color(255, 255, 255, 220);

		setBackground(background);
		setLayout(null);

		// Create status message JLabel
		JLabel lblMsg = new JLabel("You " + msg + "!");
		lblMsg.setHorizontalAlignment(SwingConstants.CENTER);
		lblMsg.setBounds(0, 143, 300, 16);
		lblMsg.setFont(new Font("Helvetica", Font.BOLD, 18));
		add(lblMsg);

		// Create answer JLabel
		JLabel lblAnswer = new JLabel("The answer was:");
		lblAnswer.setHorizontalAlignment(SwingConstants.CENTER);
		lblAnswer.setBounds(0, 171, 300, 16);
		lblAnswer.setFont(new Font("Helvetica", Font.PLAIN, 18));
		add(lblAnswer);

		// Create the answer Set
		if (answer != null) {
			Set set = new Set();
			set.setBounds(40, 203, 220, 50);
			set.setDots(answer);
			add(set);
		}

		// Create the replay JButton
		JButton btnReplay = new JButton("Start New Game");
		btnReplay.setBounds(0, 275, 300, 29);
		btnReplay.setFont(new Font("Helvetica", Font.PLAIN, 18));
		btnReplay.setForeground(GameColor.CYAN.getColor());
		btnReplay.setOpaque(false);
		btnReplay.setContentAreaFilled(false);
		btnReplay.setBorderPainted(false);
		btnReplay.setHorizontalAlignment(SwingConstants.CENTER);
		btnReplay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				startNewGame();
			}
		});
		add(btnReplay);

		// Create the quit JButton
		JButton btnQuit = new JButton("Quit Game");
		btnQuit.setBounds(0, 310, 300, 29);
		btnQuit.setFont(new Font("Helvetica", Font.PLAIN, 18));
		btnQuit.setForeground(GameColor.CYAN.getColor());
		btnQuit.setOpaque(false);
		btnQuit.setContentAreaFilled(false);
		btnQuit.setBorderPainted(false);
		btnQuit.setHorizontalAlignment(SwingConstants.CENTER);
		btnQuit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				quitGame();
			}
		});
		add(btnQuit);

		// Sets appropriate labels, sets and button if lose
		if (msg == "lost") {
			lblMsg.setBounds(0, 120, 300, 16);

			lblAnswer.setText("The correct answer was:");
			lblAnswer.setBounds(0, 171, 300, 16);

			JLabel lblGuess = new JLabel("Your answer was:");
			lblGuess.setHorizontalAlignment(SwingConstants.CENTER);
			lblGuess.setBounds(0, 270, 300, 16);
			lblGuess.setFont(new Font("Helvetica", Font.PLAIN, 18));
			add(lblGuess);

			if (guess != null) {
				Set set2 = new Set();
				set2.setBounds(87, 302, 135, 30);
				set2.setDots(guess);
				add(set2);
			}

			btnReplay.setBounds(0, 354, 300, 29);
			btnQuit.setBounds(0, 389, 300, 29);
		} else if (msg == "start") {
			lblMsg.setText("Welcome to Mastermind!");
			lblMsg.setBounds(0, 213, 300, 16);
			btnReplay.setBounds(0, 248, 300, 29);
			btnQuit.setBounds(0, 285, 300, 29);
			remove(lblAnswer);
		}
	}

	/**
	 * Restarts the game
	 */
	private void startNewGame() {
		frame.startGameWithSession();
	}

	/**
	 * Quits the game and sends the packet
	 * 
	 */
	private void quitGame() {
		System.exit(0);
	}
}
