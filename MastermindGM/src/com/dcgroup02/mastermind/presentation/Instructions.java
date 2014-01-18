package com.dcgroup02.mastermind.presentation;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

/**
 * Creates a frame that holds the instructions for Mastermind game.
 * 
 * @author Ian Ozturk, Anton Shevchencko, Darrel-Day Guerrero
 * @version 1.0
 * @since jdk 7.0
 */
public class Instructions extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2280284577767500112L;
	private final JPanel contentPanel = new JPanel();

	/**
	 * Default constructor.
	 * Creates the Dialog.
	 * @param menu
	 */
	public Instructions(Menu menu) {
		setTitle("Instructions");
		setModal(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 280, 425);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		
		contentPanel.setLayout(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblCreatedBy = new JLabel("How to Play:");
		lblCreatedBy.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreatedBy.setFont(new Font("Helvetica", Font.BOLD, 18));
		lblCreatedBy.setBounds(6, 25, 280, 16);
		contentPanel.add(lblCreatedBy);
		
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(15, 59, 250, 330);
		textPane.setText("The computer has selected a code consisting of four colors. You have 10 chances. If you didn't correctly guess the color code, the computer gives you a set of clues after each guess.\n\nWhite = a color is in the right position. Black = a color is correct but is in the wrong position.\n\nIf you haven't guessed the color code after 10 tries, you lose the game.\n\nTesting feature: double click the Send button in order to set a custom answer.");
		textPane.setFont(new Font("Helvetica", Font.PLAIN, 14));
		
		contentPanel.add(textPane);
		setLocationRelativeTo(menu);
		setVisible(true);
	}

}