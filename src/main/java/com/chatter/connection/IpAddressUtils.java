package com.chatter.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class IpAddressUtils {

	private static final int PING_TEST_NUMBER = 3;

	private IpAddressUtils() {
	}

	public static List<String> getActiveHostAddressesInLAN() {
		List<String> ipList = Collections.synchronizedList(new ArrayList<>());
		ExecutorService executorService = Executors.newFixedThreadPool(255);

		for (String hostAddress : getAllHostAddressesInLAN()) {
			executorService.submit(() -> {
				if (pingHostAddress(hostAddress)) {
					ipList.add(hostAddress);
				}
			});
		}

		executorService.shutdown();
		while (!executorService.isTerminated()) {
			// Do nothing, wait
		}

		return ipList;
	}

	private static List<String> getAllHostAddressesInLAN() {
		List<String> hostAddressList = new ArrayList<>();
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			String ip = localHost.getHostAddress();
			int lastDotIndex = ip.lastIndexOf("."); // ip => 192.168.1.132
			String networkAddress = ip.substring(0, lastDotIndex + 1); // networkAddress => 192.168.1.
			for (int i = 1; i <= 255; i++) {
				String hostAddress = networkAddress + i;
				hostAddressList.add(hostAddress);
			}
		} catch (IOException e) {
		}

		return hostAddressList;
	}

	public static boolean pingHostAddress(String hostAddress) {
		try {
			InetAddress host = InetAddress.getByName(hostAddress);
			for (int i = 0; i < PING_TEST_NUMBER; i++) {
				if (host.isReachable(100)) {
					return true;
				}
			}
		} catch (IOException e) {
		}
		return false;
	}

}
