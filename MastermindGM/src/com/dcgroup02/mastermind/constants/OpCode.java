package com.dcgroup02.mastermind.constants;

/**
 * The OpCode Enum represents all possible operation codes used as headers for
 * any packets sent during the communications between client-server.
 * 
 * @author Ian Ozturk, Anton Shevchencko, Darrel-Day Guerrero
 * @version 1.0
 * @since jdk 7.0
 */
public enum OpCode {

	REQUEST_CONNECT((byte) 0x0), ACKNOWLEDGE_CONNECT((byte) 0x1), REQUEST_GAME_START(
			(byte) 0x2), ACKNOWLEDGE_GAME_START((byte) 0x3), GUESS((byte) 0x4), CLUE(
			(byte) 0x5), WIN((byte) 0x6), LOSE((byte) 0x7), FORFEIT((byte) 0x8), QUIT(
			(byte) 0x9), REQUEST_SET_ANSWER((byte) 0xA), ACKNOWLEDGE_SET_ANSWER(
			(byte) 0xB);

	private byte code;

	/**
	 * Private constructor for initializing byte code for each operation code.
	 * 
	 * @param code
	 *            - byte representation of operation code
	 */
	private OpCode(byte code) {
		this.code = code;
	}

	/**
	 * Returns an operation's codes' enum's byte value.
	 * 
	 * @return code - byte representation of operation code
	 */
	public byte toByte() {
		return code;
	}

}
