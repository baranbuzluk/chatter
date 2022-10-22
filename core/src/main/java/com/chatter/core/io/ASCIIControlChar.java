package com.chatter.core.io;

public final class ASCIIControlChar {

	/**
	 * Start of Header.
	 */
	public static final byte STX = 0x1;

	/**
	 * Start of Text.
	 */
	public static final byte SOT = 0x2;

	/**
	 * End of Text.
	 */
	public static final byte ETX = 0x3;

	/**
	 * End of Transmission
	 */
	public static final byte EOT = 0x4;

	private ASCIIControlChar() {
	}

}
