package com.chatter.connection;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public final class IpAddressUtils {

	private static final int PING_TEST_NUMBER = 3;

	private IpAddressUtils() {
	}

	public static List<String> getActiveHostAddressesInLAN() {
		return getAllHostAddressesInLAN().parallelStream().filter(IpAddressUtils::pingHostAddress).toList();
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
