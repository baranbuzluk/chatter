package com.chatter;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainClass {

	private static FileLock tryLock;

	private static RandomAccessFile randomAccessFile;

	public static void main(String[] args) {
		checkIfProgramIsAlreadyRunning();
		MainFX.main(args);
	}

	private static void checkIfProgramIsAlreadyRunning() {
		File tempFile = new File(System.getProperty("java.io.tmpdir"), "chatter.tmp");
		try {
			tempFile.createNewFile();
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(new JFrame(), "Failed to create temporary file", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}

		try {
			randomAccessFile = new RandomAccessFile(tempFile, "rw");
			FileChannel fileChannel = randomAccessFile.getChannel();
			tryLock = fileChannel.tryLock();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (tryLock == null) {
			JOptionPane.showMessageDialog(new JFrame(), "The program is already running", "Error",
					JOptionPane.ERROR_MESSAGE);
			System.exit(0);
		}
	}
}
