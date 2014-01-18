package com.dcgroup02.mastermind.presentation;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;
import javax.swing.JButton;

import com.dcgroup02.mastermind.constants.GameColor;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Mastermind's Connection JPanel. Initializes important game components
 * such as the connection panel and the game panel.
 * 
 * @author Ian Ozturk, Anton Shevchencko, Darrel-Day Guerrero
 * @version 1.0
 * @since jdk 7.0
 */
public class ConnectionPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5703527382597414240L;
	private JButton btnConnect;
	private JTextField txtIP;
	private AppFrame frame;

	/**
	 * Create the panel and initializes the GUI components.
	 */
	public ConnectionPanel(AppFrame frame) {
		this.frame = frame;
		
		setOpaque(false);
		setLayout(null);

		// Connect to Server JLabel
		JLabel lblWelcomeToMastermind = new JLabel("Connect to Server");
		lblWelcomeToMastermind.setBounds(0, 213, 300, 19);
		lblWelcomeToMastermind.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToMastermind.setFont(new Font("Helvetica", Font.PLAIN, 22));
		add(lblWelcomeToMastermind);

		// Enter server's IP JLabel
		JLabel lblEnterServersIp = new JLabel("Enter server's IP address:");
		lblEnterServersIp.setBounds(66, 248, 164, 16);
		lblEnterServersIp.setFont(new Font("Helvetica", Font.PLAIN, 14));
		add(lblEnterServersIp);
		
		//Create the IP text field
		// JTextField for the IP address
		txtIP = new JTextField();
		txtIP.setBounds(66, 271, 164, 35);
		txtIP.setFont(new Font("Helvetica", Font.PLAIN, 14));
		// Enables / Disables connect button if empty or not
		txtIP.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent k) {
				if (!txtIP.getText().trim().isEmpty())
					btnConnect.setEnabled(true);
				else
					btnConnect.setEnabled(false);
			}
		});
		// Trims the JTextField
		txtIP.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectToServer(txtIP.getText().trim());
			}
		});
		add(txtIP);
		txtIP.setColumns(10);
		
		// Connect JButton
		btnConnect = new JButton("Connect");
		btnConnect.setBounds(80, 313, 135, 29);
		btnConnect.setFont(new Font("Helvetica", Font.PLAIN, 18));
		btnConnect.setForeground(GameColor.CYAN.getColor());
		btnConnect.setOpaque(true);
		btnConnect.setBorderPainted(false);
		btnConnect.setBackground(Color.WHITE);
		btnConnect.setEnabled(false);
		btnConnect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				connectToServer(txtIP.getText().trim());
			}
		});

		add(btnConnect);

	}

	/**
	 * Connects to server from the App Frame.
	 * @param serverIp - The server's IP address
	 */
	private void connectToServer(String serverIp) {
		frame.connectToServer(serverIp);
	}
}
