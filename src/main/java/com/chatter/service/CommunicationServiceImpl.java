package com.chatter.service;

import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
class CommunicationServiceImpl implements CommunicationService {

	private PostService postService;

	@Autowired
	public CommunicationServiceImpl(PostService postService) {
		this.postService = postService;
	}

	@Override
	public List<String> getOnlineHostAddresses() {
		List<String> onlineHostAddressList = Collections.synchronizedList(new ArrayList<>());
		List<String> activeHostAddresses = getActiveHostAddressList();
		ExecutorService executorService = Executors.newFixedThreadPool(activeHostAddresses.size());
		for (String activeHostAddress : activeHostAddresses) {
			executorService.submit(() -> {
				if (postService.sendPost(new TestPost(), activeHostAddress)) {
					onlineHostAddressList.add(activeHostAddress);
				}
			});
		}
		executorService.shutdown();
		while (!executorService.isTerminated())
			;
		return new ArrayList<>(onlineHostAddressList);
	}

	private List<String> getActiveHostAddressList() {
		List<String> activeHostAddresses = Collections.synchronizedList(new ArrayList<>());
		ExecutorService executorService = Executors.newCachedThreadPool();
		for (String hostAddress : getAllHostAddressesInLAN()) {
			executorService.submit(() -> {
				try {
					if (InetAddress.getByName(hostAddress).isReachable(50)) {
						activeHostAddresses.add(hostAddress);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}

			});
		}
		executorService.shutdown();
		while (!executorService.isTerminated())
			;
		return new ArrayList<>(activeHostAddresses);
	}

	private List<String> getAllHostAddressesInLAN() {
		List<String> hostAddressList = new ArrayList<>();
		try {
			InetAddress localHost = InetAddress.getLocalHost();
			String localHostAddress = localHost.getHostAddress();
			int lastDotIndex = localHostAddress.lastIndexOf("."); // ip => 192.168.1.132
			String networkAddress = localHostAddress.substring(0, lastDotIndex + 1); // networkAddress => 192.168.1.
			for (int i = 1; i <= 255; i++) {
				String hostAddress = networkAddress + i;
				if (!localHostAddress.equalsIgnoreCase(hostAddress)) {
					hostAddressList.add(hostAddress);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return hostAddressList;
	}

}

class TestPost implements Post {

	private static final long serialVersionUID = -6964975754241644848L;

}