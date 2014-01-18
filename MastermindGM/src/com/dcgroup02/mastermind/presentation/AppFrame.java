package com.dcgroup02.mastermind.presentation;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.border.EmptyBorder;

import java.awt.Color;
import java.io.IOException;

import com.dcgroup02.mastermind.GameClient;
import com.dcgroup02.mastermind.constants.OpCode;

/**
 * Mastermind's main application JFrame. Initializes important game components
 * such as the connection panel and the game panel.
 * 
 * @author Ian Ozturk, Anton Shevchencko, Darrel-Day Guerrero
 * @version 1.0
 * @since jdk 7.0
 */
public class AppFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLayeredPane layeredPane;
	private GameClient gameClient;
	private GamePanel gamePanel;
	private GameFinished gameFinished;
	private Menu menu;

	/**
	 * Create the frame.
	 */
	public AppFrame(GameClient gameClient) {
		// Store instance of game client (business layer).
		this.gameClient = gameClient;

		// Create and register custom font from resources package.
		GraphicsEnvironment ge = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		try {
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, AppFrame.class
					.getResourceAsStream("../../resources/Helvetica.ttf")));
		} catch (Exception e) {
			e.getStackTrace();
		}

		// Fixes size and title.
		setResizable(false);
		setTitle("Mastermind");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, 300, 555);

		// Create content panel.
		layeredPane = new JLayeredPane();
		layeredPane.setBackground(Color.WHITE);
		layeredPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		layeredPane.setLayout(new BorderLayout(0, 0));
		layeredPane.setOpaque(true);
		setContentPane(layeredPane);

		// Display connection panel.
		displayConnectionPanel();

		// Center the frame in computer's screen.
		setLocationRelativeTo(null);

		// Before quitting app, always send quit packet and close socket.
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				exitApplication();
			}
		});
	}

	/**
	 * Displays to console that answer set has been changed by the client.
	 */
	public void displayAnswerChanged() {
		System.out.println("Answer set has been succesfully changed.");
	}

	/**
	 * Displays the menu panel.
	 */
	public void displayMenu() {
		// Deactivate the game panel
		gamePanel.setIsActive(false);

		menu = new Menu(this);
		menu.setBounds(0, 0, 300, 555);
		layeredPane.add(menu);
		layeredPane.moveToFront(menu);
	}

	/**
	 * Displays the game panel.
	 */
	public void displayGamePanel() {
		gamePanel = new GamePanel(this);
		gamePanel.setBounds(0, 0, 300, 555);
		layeredPane.add(gamePanel);
	}

	/**
	 * Displays the connection panel.
	 */
	public void displayConnectionPanel() {
		ConnectionPanel connectionPanel = new ConnectionPanel(this);
		connectionPanel.setBounds(0, 0, 300, 555);
		layeredPane.add(connectionPanel);
	}

	/**
	 * Displays the game finished panel with the appropriate message.
	 * 
	 * @param msg
	 *            - The message corresponding to game's final status
	 * @param answer
	 *            - The generated answer set
	 */
	public void displayAnswer(String msg, Color[] answer) {
		if (gamePanel != null) {
			gamePanel.setIsActive(false);
		}

		Dot[] dots = gamePanel.getLastGuessSet();

		// Assigns dots from last guess set
		Color[] guess = new Color[4];
		for (int i = 0; i < guess.length; i++)
			guess[i] = dots[i].getColor();

		gameFinished = new GameFinished(this, msg, answer, guess);
		gameFinished.setBounds(0, 0, 300, 555);
		layeredPane.add(gameFinished);
		layeredPane.moveToFront(gameFinished);
	}

	/**
	 * Displays clue
	 * 
	 * @param colors
	 *            - The Color
	 */
	public void displayClue(Color[] colors) {
		gamePanel.displayClue(colors);
	}

	/**
	 * Displays the game finished panel with the appropriate message.
	 * 
	 * @param msg
	 *            - The message corresponding to game's final status
	 * @param answer
	 *            - The generated answer set
	 * @param guess
	 *            - The last guess set
	 */
	public void displayGameFinished(String msg, Color[] answer, Color[] guess) {
		gameFinished = new GameFinished(this, msg, answer, guess);
		gameFinished.setBounds(0, 0, 300, 555);
		layeredPane.add(gameFinished);
	}

	/**
	 * Removes menu panel.
	 */
	public void removeMenu() {
		gamePanel.setIsActive(true);

		layeredPane.remove(menu);
		layeredPane.repaint();
	}

	// Communication methods.

	/**
	 * Connects to server with IP.
	 * 
	 * @param serverIp
	 */
	public void connectToServer(String serverIp) {
		try {
			layeredPane.removeAll();
			layeredPane.repaint();

			gameClient.connectToServer(serverIp);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,
					"Cannot connect to server. Please try again.",
					"Server Connection Error", JOptionPane.ERROR_MESSAGE);
			displayConnectionPanel();
		}
	}

	/**
	 * Starts the game's session from game client.
	 */
	public void startGameWithSession() {
		try {
			layeredPane.removeAll();
			layeredPane.repaint();

			gameClient.startGameWithSession();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,
					"Cannot start game with server. Please try again.",
					"Server Connection Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Sends the user-generated answer set from the game client.
	 * 
	 * @param dotValues
	 *            - The user-generated answer set color integer values
	 */
	public void sendAnswerSet(int[] dotValues) {
		try {
			gameClient.sendAndReceivePacket(OpCode.REQUEST_SET_ANSWER,
					dotValues[0], dotValues[1], dotValues[2], dotValues[3]);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,
					"Cannot send answer packet to server. Please try again.",
					"Server Connection Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Sends the guess set from the game client.
	 * 
	 * @param dotValues
	 *            - The guess set's color integer values
	 */
	public void sendGuessSet(int[] dotValues) {
		try {
			gameClient.sendAndReceivePacket(OpCode.GUESS, dotValues[0],
					dotValues[1], dotValues[2], dotValues[3]);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,
					"Cannot send guess packet to server. Please try again.",
					"Server Connection Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Sends forfeit packet from the game client.
	 */
	public void sendForfeitPacket() {
		try {
			removeMenu();

			gameClient.sendAndReceivePacket(OpCode.FORFEIT, 0, 0, 0, 0);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,
					"Cannot send forfeit packet to server. Please try again.",
					"Server Connection Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Exits application and closes connection from the game client.
	 */
	private void exitApplication() {
		try {
			gameClient.sendAndReceivePacket(OpCode.QUIT, 0, 0, 0, 0);

			gameClient.closeConnection();
		} catch (IOException e) {
			JOptionPane
					.showMessageDialog(
							this,
							"Cannot send quit packet to server or socket cannot close properly.",
							"Server Connection Error",
							JOptionPane.ERROR_MESSAGE);
		}
	}

}
