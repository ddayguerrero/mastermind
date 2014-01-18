package com.dcgroup02.mastermind.business;

import java.io.IOException;
import java.util.Arrays;

import com.dcgroup02.mastermind.constants.OpCode;
import com.dcgroup02.mastermind.persistence.GamePacket;

/**
 * Game Session for Mastermind game. Creates client socket and listens for
 * incoming game packets. Handles game logic.
 * 
 * @author Ian Ozturk, Anton Shevchencko, Darrel-Day Guerrero
 * @version 1.0
 * @since jdk 7.0
 */
public class GameSession implements Runnable {

	private int[] answer;
	private GamePacket gamePacket;
	private int counter;

	/**
	 * Default constructor which accepts the client socket.
	 * 
	 * @param clntSock
	 *            - The client socket
	 * @throws IOException
	 */
	public GameSession(GamePacket gamePacket, int counter) {
		super();
		this.gamePacket = gamePacket;
		this.counter = counter;
	}

	/**
	 * Listens for incoming game packets and handles GUESS, FORFEIT and QUIT
	 * packets and also keeps track of submission attempts.
	 * 
	 * @throws IOException
	 *             - cannot communicate with client's socket.
	 */
	public void run() {
		boolean isQuit = false;

		while (!isQuit) {
			try {
				if (gamePacket.receiveGamePacket()[0] == OpCode.REQUEST_GAME_START
						.toByte()) {
					System.out
							.println("\n--> Starting new game with client " + counter + ".");

					// Send back an acknowledgment of the client's request to
					// start
					// game.
					gamePacket.sendGamePacket(OpCode.ACKNOWLEDGE_GAME_START, 0,
							0, 0, 0);

					boolean isDone = false;

					int submissionCount = 1;

					// Generate ordered answer set.
					generateAnswerSet();

					while (!isDone) {
						// Receive GamePacket and store it.
						byte[] packet = gamePacket.receiveGamePacket();

						// Handle GUESS operation code.
						if (packet[0] == OpCode.GUESS.toByte()) {
							System.out.print("\nCLIENT " + counter + " - Received GUESS set:");

							// Create a guess set from the packet.
							int[] guess = new int[4];

							for (int i = 1; i < packet.length; i++) {
								guess[i - 1] = (int) packet[i];
								System.out.print(" " + guess[i - 1]);
							}

							// Generate hint set using client's guess set.
							int[] clue = clueGenerator(guess);

							if (Arrays.equals(clue, new int[] { 2, 2, 2, 2 })) {
								System.out.println("\n\nClient " + counter + " won game!");
								gamePacket.sendGamePacket(OpCode.WIN,
										answer[0], answer[1], answer[2],
										answer[3]);
								isDone = true;
							} else if (submissionCount == 10) {
								System.out
										.println("\n\nClient " + counter + " lost game because reached 10th wrong submission.");
								gamePacket.sendGamePacket(OpCode.LOSE,
										answer[0], answer[1], answer[2],
										answer[3]);
								isDone = true;
							} else {
								// Display clue packet sent to client.
								System.out.print("\nSent CLUE set to CLIENT " + counter + ": " + clue[0]
										+ " " + clue[1] + " " + clue[2] + " "
										+ clue[3] + "\n");
								gamePacket.sendGamePacket(OpCode.CLUE, clue[0],
										clue[1], clue[2], clue[3]);
								// Add one submission attempt.
								submissionCount++;
							}
						}
						// Handle FORFEIT operation code.
						else if (packet[0] == OpCode.FORFEIT.toByte()) {
							System.out
									.println("\n\nClient " + counter + " lost game because he/she gave up.");
							gamePacket.sendGamePacket(OpCode.LOSE, answer[0],
									answer[1], answer[2], answer[3]);
							isDone = true;
						}
						// Handle QUIT operation code.
						else if (packet[0] == OpCode.QUIT.toByte()) {
							isQuit = true;
							isDone = true;
						} else if (packet[0] == OpCode.REQUEST_SET_ANSWER
								.toByte()) {
							int[] clientAnswer = new int[answer.length];
							for (int i = 0; i < clientAnswer.length; i++)
								clientAnswer[i] = packet[i + 1];

							setAnswerSet(clientAnswer);

							// Send back an acknowledgment of the client's
							// request
							// to define the answer set.
							gamePacket.sendGamePacket(
									OpCode.ACKNOWLEDGE_SET_ANSWER, 0, 0, 0, 0);
						}
					}
				} else {
					isQuit = true;
				}
			} catch (IOException e) {
				System.out
						.println("Error connecting to client " + counter + " in GameSession: ");
				e.printStackTrace();
			}
		}

		System.out.println("\nClient " + counter + " quit the game.");
		System.out.println("********************");

		// Close socket
		try {
			gamePacket.close();
		} catch (IOException e) {
			System.out
					.println("Error closing connection with client " + counter + " in GameSession: ");
			e.printStackTrace();
		}
	}

	/**
	 * Generates the game session's answer set.
	 */
	private void generateAnswerSet() {
		answer = new int[4];

		System.out.print("\nAnswer set for CLIENT " + counter + ":");
		for (int i = 0; i < answer.length; i++) {
			answer[i] = (int) (Math.random() * 8 + 3);
			System.out.print(" " + answer[i]);
		}

		// Separate answer from other output in console for readability
		// purposes.
		System.out.println();
	}

	private void setAnswerSet(int[] answer) {
		this.answer = answer;

		System.out.print("\nNew answer set (user-defined) for CLIENT " + counter + ":");
		for (int i = 0; i < answer.length; i++) {
			System.out.print(" " + answer[i]);
		}

		System.out.println();
	}

	/**
	 * Analyzes the user's guess set and generates the in-of-place and
	 * out-of-place clues.
	 * 
	 * @param guess
	 *            - the client's guess set
	 */
	private int[] clueGenerator(int[] guess) {
		// Placeholder integer array, -1 corresponds to empty.
		int[] hint = { 0, 0, 0, 0 };

		// Searches in place clues from answer set.
		for (int i = 0; i < answer.length; i++) {
			if (answer[i] == guess[i])
				hint[i] = 2; // 2 corresponds to in place.
		}

		// Searches out of place clues from answer set.
		for (int i = 0; i < answer.length; i++) {
			if (hint[i] != 2) { // Skips in place clues that are already found.
				for (int j = 0; j < guess.length; j++) {
					if (hint[j] == 0) {
						if (answer[i] == guess[j]) {
							hint[j] = 1; // 1 corresponds to out of place.
							break;
						}
					}
				}
			}
		}

		// Put in place clues first, then out of place clues.
		int pos = 0;
		for (int i = 0; i < hint.length; i++) {
			if (hint[i] == 2) {
				int temp = hint[pos];
				hint[pos] = hint[i];
				hint[i] = temp;
				pos++;
			}
		}

		for (int i = pos; i < hint.length; i++) {
			if (hint[i] == 1) {
				int temp = hint[pos];
				hint[pos] = hint[i];
				hint[i] = temp;
				pos++;
			}
		}

		return hint;
	}

}