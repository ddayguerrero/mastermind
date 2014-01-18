package com.dcgroup02.mastermind;

import java.awt.Color;
import java.awt.EventQueue;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.dcgroup02.mastermind.constants.GameColor;
import com.dcgroup02.mastermind.constants.OpCode;
import com.dcgroup02.mastermind.persistence.GamePacket;
import com.dcgroup02.mastermind.presentation.AppFrame;

/**
 * Game Client for Mastermind game. Handles the initialization of the
 * application's JFrame and game.
 * 
 * @author Ian Ozturk, Anton Shevchencko, Darrel-Day Guerrero
 * @version 1.0
 * @since jdk 7.0
 */
public class GameClient {

	private GamePacket gamePacket;
	private AppFrame frame;
	private static GameColor[] allColors = GameColor.values();

	/**
	 * Launches the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@SuppressWarnings("unused")
			public void run() {
				try {
					GameClient gc = new GameClient();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public GameClient() {
		super();

		// Instantiate frame and display it.
		this.frame = new AppFrame(this);
		this.frame.setVisible(true);
	}

	public void connectToServer(String serverIp) throws IOException {
		int port = 50000;

		Socket sock = new Socket();

		// Set a timeout of 3 seconds if it takes too long to connect to server.
		sock.connect(new InetSocketAddress(serverIp, port), 3000);

		if (sock != null) {
			gamePacket = new GamePacket(sock);

			sendAndReceivePacket(OpCode.REQUEST_CONNECT, 0, 0, 0, 0);
		}
	}

	/**
	 * Starts the connection by creating a client socket with the passed IP.
	 * 
	 * @param server
	 *            The server's IP address
	 * @throws IOException
	 */
	public void startGameWithSession() throws IOException {
		sendAndReceivePacket(OpCode.REQUEST_GAME_START, 0, 0, 0, 0);
	}

	/**
	 * Sends a game packet. If packet is a guess, sets the dots colors. If
	 * packet is a quit, analyzes packet.
	 * 
	 * @param opCode
	 *            Operation Code
	 * @param d1
	 *            First int value corresponding to a Dot
	 * @param d2
	 *            Second int value corresponding to a Dot
	 * @param d3
	 *            Third int value corresponding to a Dot
	 * @param d4
	 *            Fourth int value corresponding to a Dot
	 * @throws IOException
	 */
	public void sendAndReceivePacket(OpCode opCode, int d1, int d2, int d3,
			int d4) throws IOException {
		if (gamePacket != null) {
			gamePacket.sendGamePacket(opCode, d1, d2, d3, d4);

			// Analyze the received packet.
			if (!opCode.equals(OpCode.QUIT)) {
				analysePacket(gamePacket.receiveGamePacket());
			}
		}
	}

	/**
	 * Analyzes received packet and displays correct GUI panel depending on
	 * operation code.
	 * 
	 * @param packet
	 *            The game packet
	 */
	private void analysePacket(byte[] packet) {
		byte header = packet[0];

		if (header == OpCode.CLUE.toByte()) {
			// Display clue in current row in GameGrid.
			displayClue(packet);
		} else if (header == OpCode.ACKNOWLEDGE_CONNECT.toByte()) {
			// Display the menu.
			frame.displayGameFinished("start", null, null);
		} else if (header == OpCode.ACKNOWLEDGE_GAME_START.toByte()) {
			// Display GamePanel and start game.
			frame.displayGamePanel();
		} else if (header == OpCode.ACKNOWLEDGE_SET_ANSWER.toByte()) {
			// Display pop-up to user to confirm the answer set change.
			frame.displayAnswerChanged();
		} else {
			// Display GameFinished with answer set, either LOSE or WIN packet.
			displayAnswer(packet);
		}
	}

	/**
	 * Displays the clue from the game panel
	 * 
	 * @param packet
	 *            The packet
	 */
	private void displayClue(byte[] packet) {
		Color[] colors = new Color[4];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = (allColors[packet[i + 1]]).getColor();
		}

		frame.displayClue(colors);
	}

	/**
	 * Displays the answers from the game panel
	 * 
	 * @param packet
	 * @throws IOException
	 */
	private void displayAnswer(byte[] packet) {
		String msg = "won";
		if (packet[0] == OpCode.LOSE.toByte())
			msg = "lost";

		Color[] colors = new Color[4];
		for (int i = 0; i < colors.length; i++) {
			colors[i] = (allColors[packet[i + 1]]).getColor();
		}

		frame.displayAnswer(msg, colors);
	}

	/**
	 * Closes the connection from the game packet.
	 * @throws IOException
	 */
	public void closeConnection() throws IOException {
		if (gamePacket != null)
			gamePacket.close();
	}
}