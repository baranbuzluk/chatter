package com.chatter.client.session;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chatter.core.entity.Account;

public final class ChatterSession {

	private static final Logger LOG = LoggerFactory.getLogger(ChatterSession.class);

	private static final ChatterSession INSTANCE = new ChatterSession();

	private Account activeAccount;

	private ChatterSession() {
		LOG.info("ChatterSession has been created.");
	}

	public static ChatterSession getInstance() {
		return INSTANCE;
	}

	public void closeSession() {
		if (activeAccount != null) {
			String log = MessageFormat.format("{0}  session has been closed.", activeAccount.getUsername());
			LOG.info(log);
			activeAccount = null;
		}
	}

	public Account getActiveAccount() {
		return activeAccount;
	}

	public void openSession(Account activeAccount) {
		closeSession();
		if (activeAccount != null) {
			this.activeAccount = activeAccount;
			String log = MessageFormat.format("{0} session has been opened.", activeAccount.getUsername());
			LOG.info(log);
		}
	}

	public boolean isOpen() {
		return activeAccount != null;
	}
}
