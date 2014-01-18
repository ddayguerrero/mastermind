package com.dcgroup02.mastermind;

import java.net.*;
import java.awt.EventQueue;
import java.io.*;

import com.dcgroup02.mastermind.business.GameSession;
import com.dcgroup02.mastermind.constants.OpCode;
import com.dcgroup02.mastermind.persistence.GamePacket;

/**
 * Game Server for Mastermind game. Creates Server Socket.
 * 
 * @author Ian Ozturk, Anton Shevchencko, Darrel-Day Guerrero
 * @version 2.0
 * @since jdk 7.0
 */
public class GameServer {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GameServer gs = new GameServer();
					gs.startServer();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Default constructor.
	 */
	public GameServer() {
		super();
	}

	/**
	 * Starts the Game Session and Socket by creating accepting a server socket.
	 */
	@SuppressWarnings({ "resource" })
	public void startServer() {
		int counter = 1;
		int servPort = 50000;

		try {
			System.out.println("SERVER STARTED");

			ServerSocket servSock = new ServerSocket(servPort);

			// Display server's IP address and port number in console.
			System.out.println("Server's IP: "
					+ InetAddress.getLocalHost().getHostAddress()
					+ "\nListening on port: " + servSock.getLocalPort());

			// Runs forever, accepting and servicing connections.
			for (;;) {
				Socket clntSock = servSock.accept();

				GamePacket gamePacket = new GamePacket(clntSock);

				byte[] packet = gamePacket.receiveGamePacket();

				if (packet[0] == OpCode.REQUEST_CONNECT.toByte()) {
					System.out.println("\n********************");
					System.out
							.println("Establishing game session with incoming client machine.\nClient machine's IP address: "
									+ clntSock.getInetAddress()
											.getHostAddress()
									+ "\nClient machine's port number: "
									+ clntSock.getPort());

					// Send back an acknowledgment to the client's connect
					// request.
					gamePacket.sendGamePacket(OpCode.ACKNOWLEDGE_CONNECT, 0, 0,
							0, 0);

					// Create new game session in its own thread and start
					// executing it.
					GameSession gs = new GameSession(gamePacket, counter++);
					Thread thread = new Thread(gs);
					thread.start();
				}
			}
		} catch (IOException e) {
			System.out
					.println("Error handling clients on server socket in GameServer: ");
			e.printStackTrace();
		}
	}

}
