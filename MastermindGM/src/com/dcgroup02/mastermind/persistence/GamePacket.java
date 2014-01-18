package com.dcgroup02.mastermind.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import com.dcgroup02.mastermind.constants.OpCode;

/**
 * Mastermind's Game Packet. Composed of a OpCode enum for the operation and 4
 * int values representing the Color values. Handles sending and receiving
 * between client and server.
 * 
 * @author Ian Ozturk, Anton Shevchencko, Darrel-Day Guerrero
 * @version 1.0
 * @since jdk 7.0
 */
public class GamePacket {

	private static final int BUFSIZE = 5;
	private Socket sock;
	private OutputStream out;
	private InputStream in;

	public GamePacket(Socket sock) {
		super();
		this.sock = sock;
	}

	/**
	 * 
	 * @throws IOException
	 *             - Catch exception closer to presentation layer
	 */
	public void sendGamePacket(OpCode code, int dot1, int dot2, int dot3,
			int dot4) throws IOException {

		byte[] packet = { code.toByte(), (byte) dot1, (byte) dot2, (byte) dot3,
				(byte) dot4 };

		out = sock.getOutputStream();

		out.write(packet);
	}

	/**
	 * Reads input stream from socket.
	 * 
	 * @return byte array representing a packet
	 * @throws IOException
	 */
	public byte[] receiveGamePacket() throws IOException {
		// Wraps the socket into Input stream - High Level IO
		in = sock.getInputStream();

		int totalBytesRcvd = 0;
		int bytesRcvd;
		byte[] byteBuffer = new byte[BUFSIZE];

		while (totalBytesRcvd < byteBuffer.length) {
			if ((bytesRcvd = in.read(byteBuffer, totalBytesRcvd,
					byteBuffer.length - totalBytesRcvd)) == -1)
				throw new SocketException("Connection closed prematurely");
			totalBytesRcvd += bytesRcvd;
		}

		return byteBuffer;
	}
	
	/**
	 * Closes socket connection.
	 * 
	 * @throws IOException
	 */
	public void close() throws IOException {
		sock.close();
	}

}
