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
 * Mastermind's About JDialog. Provides information about the application.
 * 
 * @author Ian Ozturk, Anton Shevchencko, Darrel-Day Guerrero
 * @version 1.0
 * @since jdk 7.0
 */
public class About extends JDialog {

	private static final long serialVersionUID = -7746586143773497826L;
	private final JPanel contentPanel = new JPanel();
	
	/**
	 * Create the dialog.
	 */
	public About(Menu menu) {
		// Set panel's properties and add it to the panel.
		setTitle("About Mastermind");
		setModal(true);
		setResizable(false);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 250, 176);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(Color.WHITE);
		
		contentPanel.setLayout(null);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		// Create the Created by label, set its properties, and add it to the menu.
		JLabel lblCreatedBy = new JLabel("Created by:");
		lblCreatedBy.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreatedBy.setFont(new Font("Helvetica", Font.BOLD, 18));
		lblCreatedBy.setBounds(6, 25, 238, 16);
		contentPanel.add(lblCreatedBy);
		
		// Create the JTextPane and add it to the menu.
		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		textPane.setBounds(51, 59, 154, 72);
		textPane.setText("Darrel-Day Guerrero\nAnton Shevchenko\nIan Oztruk");
		textPane.setFont(new Font("Helvetica", Font.PLAIN, 14));
		
		contentPanel.add(textPane);
		setLocationRelativeTo(menu);
		setVisible(true);
	}
}
